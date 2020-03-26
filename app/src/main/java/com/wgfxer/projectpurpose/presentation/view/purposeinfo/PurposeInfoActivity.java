package com.wgfxer.projectpurpose.presentation.view.purposeinfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.presentation.view.addpurpose.AddPurposeActivity;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.notesfragment.NotesListFragment;
import com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment.ReportsFragment;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;


/**
 * Активность для отображения детализации по цели(заметки,отчеты)
 */
public class PurposeInfoActivity extends AppCompatActivity implements EditThemeDialogFragment.OnThemeChangeListener {
    private static final String KEY_PURPOSE_ID = "PURPOSE_ID";
    private AppBarLayout appBar;
    private CollapsingToolbarLayout collapsingToolbar;
    private TextView daysLeftTextView;
    private Menu menu;
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;
    private Purpose purpose;
    private ViewPager purposeDetailPager;
    private TextView purposeTitleTextView;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ImageView toolbarGradient;
    private ImageView toolbarImage;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_info);

        findViews();
        setUpViewPager();
        setUpToolbar();
        observeViewModel();

    }

    /**
     * создает и возвращает интент для перехода к активности с передачей id цели
     */
    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, PurposeInfoActivity.class);
        intent.putExtra(KEY_PURPOSE_ID, id);
        return intent;
    }

    /**
     * заполнение меню
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.purpose_info_menu, menu);
        return true;
    }

    /**
     * обработка нажатий на меню(назад, изменить,поделиться)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.edit_purpose:
                Intent intent = AddPurposeActivity.editPurpose(this, purpose.getId());
                startActivity(intent);
                return true;

            case R.id.share_purpose:
                sharePurpose(purpose);
                return true;

            case R.id.edit_purpose_theme:
                EditThemeDialogFragment editThemeDialog = EditThemeDialogFragment.newInstance(purpose.getTheme());
                editThemeDialog.show(getSupportFragmentManager(), null);
                return true;

            case R.id.add_purpose_in_calendar:
                addPurposeInCalendar();
                return true;

            case R.id.check_purpose_done:
                checkPurposeDone();
                return true;

            case R.id.delete_purpose:
                showDialogDeletePurpose();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * отмечает цель выполненной
     */
    private void checkPurposeDone() {
        purpose.setDone(!purpose.isDone());
        setDonePurposeItemTitle();
        viewModel.updatePurpose(purpose);
    }

    /**
     * изменяет title у элемента меню в зависимости от того выполнена она или нет
     */
    private void setDonePurposeItemTitle() {
        if (purpose.isDone()) {
            menu.findItem(R.id.check_purpose_done).setTitle(R.string.uncheck_done_text);
        } else {
            menu.findItem(R.id.check_purpose_done).setTitle(R.string.check_done_text);
        }
    }

    /**
     * колбэк при изменении темы
     *
     * @param theme новая тема
     */
    @Override
    public void onThemeChange(PurposeTheme theme) {
        purpose.setTheme(theme);
        viewModel.updatePurpose(purpose);
    }

    private void findViews() {
        purposeTitleTextView = findViewById(R.id.purpose_title_text_view);
        toolbarImage = findViewById(R.id.toolbar_image);
        toolbarGradient = findViewById(R.id.toolbar_gradient);
        daysLeftTextView =  findViewById(R.id.days_left);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbar =  findViewById(R.id.collapsing_toolbar);
        appBar =  findViewById(R.id.appbar_layout);
        purposeDetailPager =  findViewById(R.id.view_pager);
        tabLayout =  findViewById(R.id.tab_layout);
    }

    private void setUpToolbar() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    setToolbarIconsColor(android.R.color.white);
                } else if (verticalOffset == 0) {
                    setToolbarIconsColor(android.R.color.black);
                }
            }
        };
    }

    private void setUpViewPager() {
        purposeDetailPager.setAdapter(new PurposeDetailPagerAdapter(getSupportFragmentManager(),
                getIntent().getIntExtra(KEY_PURPOSE_ID, -1)));
        tabLayout.setupWithViewPager(purposeDetailPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorChipStroke), getResources().getColor(R.color.colorPrimary));
        tabLayout.setBackgroundResource(android.R.color.transparent);
        for (int i = 0; i <= tabLayout.getTabCount() - 1; i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(25, 0, 0, 0);
            if (i == this.tabLayout.getTabCount() - 1) {
                p.setMargins(25, 0, 25, 0);
            }
            tab.requestLayout();
        }
    }

    /**
     * Устанавливает цвет иконок в тулбаре
     *  param colorResId id ресурса цвета в который красится тулбар
     */
    private void setToolbarIconsColor(int colorResId){
        int color =  getResources().getColor(colorResId);
        toolbar.getNavigationIcon().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        MenuItem editItem = toolbar.getMenu().findItem(R.id.edit_purpose);
        if(editItem != null){
            Drawable editIcon = editItem.getIcon();
            editIcon.setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
        }
        MenuItem shareItem = toolbar.getMenu().findItem(R.id.share_purpose);
        if(shareItem != null){
            Drawable shareIcon = shareItem.getIcon();
            shareIcon.setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
        }
        toolbar.getOverflowIcon().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }


    /**
     * подписка на вьюмодель,при изменении цели обновление ui
     */
    private void observeViewModel() {
        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this)).get(MainViewModel.class);
        viewModel.getPurposeById(getIntent().getIntExtra(KEY_PURPOSE_ID, -1)).observe(this, new Observer<Purpose>() {
            public void onChanged(Purpose purpose) {
                PurposeInfoActivity.this.purpose = purpose;
                if (purpose != null) {
                    updateUI();
                }
            }
        });
    }


    /**
     * устанавливает информацию о цели во все вью
     */
    private void updateUI() {
        purposeTitleTextView.setText(purpose.getTitle());
        toolbarGradient.setImageResource(purpose.getTheme().getGradientId());
        toolbarGradient.setAlpha(purpose.getTheme().getGradientAlpha());
        if (purpose.getTheme().getImagePath() != null) {
            toolbarImage.setImageBitmap(BitmapFactory.decodeFile(purpose.getTheme().getImagePath()));
        } else {
            toolbarGradient.setAlpha(1.0f);
        }
        if (purpose.getDate().after(new Date())) {
            int daysToGoal = Utils.getDaysFromDate(purpose.getDate());
            daysLeftTextView.setText(getResources().getQuantityString(R.plurals.days_count, daysToGoal, new Object[]{Integer.valueOf(daysToGoal)}));
        } else {
            daysLeftTextView.setText(getString(R.string.time_end_text));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int themeColor = ((GradientDrawable) getDrawable(purpose.getTheme().getGradientId())).getColors()[1];
            daysLeftTextView.setTextColor(themeColor);
            tabLayout.setTabTextColors(getResources().getColor(R.color.colorChipStroke, getTheme()), themeColor);
        }
        Drawable textViewBackground = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textViewBackground = getDrawable(R.drawable.white_background_text_view);
        }
        if (purpose.getTheme().isWhiteFont()) {
            purposeTitleTextView.setTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && textViewBackground != null) {
                textViewBackground.setTint(Color.WHITE);
            }
            setToolbarIconsColor(android.R.color.white);
            this.appBar.removeOnOffsetChangedListener(this.onOffsetChangedListener);
        } else {
            purposeTitleTextView.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && textViewBackground != null) {
                textViewBackground.setTint(Color.BLACK);
            }
            setToolbarIconsColor(android.R.color.black);
            this.appBar.addOnOffsetChangedListener(this.onOffsetChangedListener);
        }
        if (textViewBackground != null) {
            daysLeftTextView.setBackground(textViewBackground);
        }
    }

    /**
     * для корректного отображения элемента меню(отметить выполненной или не выполненной)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setDonePurposeItemTitle();
        return true;
    }

    /**
     * запускает интент для добавления цели в календарь
     */
    private void addPurposeInCalendar() {
        Intent addPurposeInCalendarIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, purpose.getTitle())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, purpose.getDate().getTime() + 18 * 60 * 60 * 1000);
        startActivity(addPurposeInCalendarIntent);
    }

    /**
     * Показывает диалог для удаления цели
     */
    private void showDialogDeletePurpose() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton(R.string.dialog_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deletePurpose(purpose);
                        dialogInterface.dismiss();
                        PurposeInfoActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setTitle(R.string.delete_purpose_confirm)
                .create();
        dialog.show();
    }

    /**
     * запускает интент поделиться целью, в зависимости от того выполнена или нет - разный текст
     *
     * @param purpose
     */
    private void sharePurpose(Purpose purpose) {
        String shareText = null;
        if (!purpose.isDone()) {
            shareText = getString(R.string.share_purpose_message, purpose.getTitle(), Utils.getStringFromDate(purpose.getDate()));
        } else {
            shareText = getString(R.string.share_done_purpose_message, purpose.getTitle(), Utils.getStringFromDate(purpose.getDate()));
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent = Intent.createChooser(intent, getString(R.string.share_purpose));
        startActivity(intent);
    }

}
