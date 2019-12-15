package com.wgfxer.projectpurpose.presentation.view.purposeinfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


/**
 * Активность для отображения детализации по цели(заметки,отчеты)
 */
public class PurposeInfoActivity extends AppCompatActivity implements EditThemeDialogFragment.OnThemeChangeListener {
    private static final String KEY_PURPOSE_ID = "PURPOSE_ID";
    private static final String KEY_SELECTED_MODE = "SELECTED_MODE";
    private static final int SELECTED_MODE_NOTES = 1;
    private static final int SELECTED_MODE_REPORTS = 2;

    private Purpose purpose;

    private TextView purposeTitleTextView;
    private ImageView toolbarImage;
    private ImageView toolbarGradient;
    private TextView daysLeftTextView;
    private FragmentManager fragmentManager;

    private MainViewModel viewModel;

    private NotesListFragment notesListFragment;
    private ReportsFragment reportsFragment;
    private Button showReportsButton;
    private Button showNotesButton;
    private int currentMode = SELECTED_MODE_NOTES;

    private Menu menu;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_info);

        setUpViews();

        observeViewModel();

        if (savedInstanceState != null) {
            switchMode(savedInstanceState.getInt(KEY_SELECTED_MODE));
        }
    }

    /**
     *  создает и возвращает интент для перехода к активности с передачей id цели
     */
    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, PurposeInfoActivity.class);
        intent.putExtra(KEY_PURPOSE_ID, id);
        return intent;
    }

    /**
     *  заполнение меню
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.purpose_info_menu, menu);
        return true;
    }

    /**
     * обработка нажатий на меню(назад, изменить,поделиться)
     * */
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
     * @param theme новая тема
     */
    @Override
    public void onThemeChange(PurposeTheme theme) {
        purpose.setTheme(theme);
        viewModel.updatePurpose(purpose);
    }

    /**
     * находит все вью и устанавливает лисенеры
     */
    private void setUpViews() {
        purposeTitleTextView = findViewById(R.id.purpose_title_text_view);
        toolbarImage = findViewById(R.id.toolbar_image);
        toolbarGradient = findViewById(R.id.toolbar_gradient);
        daysLeftTextView = findViewById(R.id.days_left);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        notesListFragment = NotesListFragment.newInstance(getIntent().getIntExtra(KEY_PURPOSE_ID, -1));
        reportsFragment = ReportsFragment.newInstance(getIntent().getIntExtra(KEY_PURPOSE_ID, -1));
        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, notesListFragment).commit();
        }

        showNotesButton = findViewById(R.id.show_notes_button);
        showReportsButton = findViewById(R.id.show_reports_button);
        showNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(SELECTED_MODE_NOTES);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, notesListFragment).commit();
            }
        });
        showReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(SELECTED_MODE_REPORTS);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, reportsFragment).commit();
            }
        });
    }

    /**
     * меняет режим просмотра(заметки или отчеты)
     * @param mode константа для отчетов или для заметок
     */
    private void switchMode(int mode) {
        if (mode == SELECTED_MODE_NOTES) {
            showNotesButton.setTextColor(Color.BLACK);
            showReportsButton.setTextColor(Color.LTGRAY);
            currentMode = SELECTED_MODE_NOTES;
        } else if (mode == SELECTED_MODE_REPORTS) {
            showNotesButton.setTextColor(Color.LTGRAY);
            showReportsButton.setTextColor(Color.BLACK);
            currentMode = SELECTED_MODE_REPORTS;
        }
    }

    /**
     * подписка на вьюмодель,при изменении цели обновление ui
     */
    private void observeViewModel() {
        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);

        LiveData<Purpose> purposeLiveData = viewModel.getPurposeById(getIntent().getIntExtra(KEY_PURPOSE_ID, -1));
        purposeLiveData.observe(this, new Observer<Purpose>() {
            @Override
            public void onChanged(@Nullable Purpose purpose) {
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
            toolbarGradient.setAlpha(1f);
        }
        if (purpose.getDate().after(new Date())) {
            int daysToGoal = Utils.getDaysFromDate(purpose.getDate());
            daysLeftTextView.setText(getResources().getQuantityString(R.plurals.days_count, daysToGoal, daysToGoal));
        } else {
            daysLeftTextView.setText(getString(R.string.time_end_text));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GradientDrawable gradientDrawable = (GradientDrawable) getDrawable(purpose.getTheme().getGradientId());
            daysLeftTextView.setTextColor(gradientDrawable.getColors()[1]);
        }
        Drawable textViewBackground = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            textViewBackground = getDrawable(R.drawable.white_background_text_view);
        }
        if (purpose.getTheme().isWhiteFont()) {
            purposeTitleTextView.setTextColor(Color.WHITE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && textViewBackground != null) {
                textViewBackground.setTint(Color.WHITE);
            }
        } else {
            purposeTitleTextView.setTextColor(Color.BLACK);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && textViewBackground != null) {
                textViewBackground.setTint(Color.BLACK);
            }
            //поставить иконки тулбара в черный
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
        return super.onPrepareOptionsMenu(menu);
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
                .setTitle(R.string.delete_purpuse_confirm)
                .create();
        dialog.show();
    }

    /**
     * запускает интент поделиться целью, в зависимости от того выполнена или нет - разный текст
     * @param purpose
     */
    private void sharePurpose(Purpose purpose) {
        String shareText = null;
        if(!purpose.isDone()){
            shareText = getString(R.string.share_purpose_message, purpose.getTitle(), Utils.getStringFromDate(purpose.getDate()));
        }else{
            shareText = getString(R.string.share_done_purpose_message, purpose.getTitle(), Utils.getStringFromDate(purpose.getDate()));
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent = Intent.createChooser(intent, getString(R.string.share_purpose));
        startActivity(intent);
    }

    /**
     * сохранение выбранного мода
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_MODE, currentMode);
    }
}
