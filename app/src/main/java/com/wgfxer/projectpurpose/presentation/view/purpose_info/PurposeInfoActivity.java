package com.wgfxer.projectpurpose.presentation.view.purpose_info;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.data.Purpose;
import com.wgfxer.projectpurpose.domain.PurposeTheme;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.presentation.view.add_purpose.AddPurposeActivity;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class PurposeInfoActivity extends AppCompatActivity implements EditThemeDialogFragment.OnThemeChangeListener {
    private static final String KEY_PURPOSE_ID = "PURPOSE_ID";

    private Purpose purpose;

    private TextView purposeTitleTextView;
    private ImageView toolbarImage;
    private ImageView toolbarGradient;
    private TextView daysLeftTextView;
    private ViewPager notesViewPager;
    private NotesAdapter notesAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose_info);

        setUpViews();

        observeViewModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        purpose.setNotesList(notesAdapter.getNotesList());
        viewModel.updatePurpose(purpose);
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, PurposeInfoActivity.class);
        intent.putExtra(KEY_PURPOSE_ID, id);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.purpose_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_purpose:
                Intent intent = AddPurposeActivity.editPurpose(this, purpose.getId());
                startActivity(intent);
                return true;
            case R.id.delete_purpose:
                showDialogDeletePurpose();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.share_purpose:
                sharePurpose(purpose);
                return true;
            case R.id.edit_purpose_theme:
                EditThemeDialogFragment editThemeDialog = EditThemeDialogFragment.newInstance(purpose.getTheme());
                editThemeDialog.show(getSupportFragmentManager(), null);
                return true;
            case R.id.add_purpose_in_calendar: {
                addPurposeInCalendar();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onThemeChange(PurposeTheme theme) {
        purpose.setTheme(theme);
        viewModel.updatePurpose(purpose);
    }

    private void setUpViews() {
        purposeTitleTextView = findViewById(R.id.purpose_title);
        toolbarImage = findViewById(R.id.toolbar_image);
        toolbarGradient = findViewById(R.id.toolbar_gradient);
        daysLeftTextView = findViewById(R.id.days_left);
        notesViewPager = findViewById(R.id.notes_view_pager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        float dip = 10f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        notesViewPager.setPageMargin((int) px);
        notesAdapter = new NotesAdapter(getSupportFragmentManager());
        notesViewPager.setAdapter(notesAdapter);
    }

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
        notesAdapter.setNotesList(purpose.getNotesList());
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
            //установить верхние иконки в черный цвет
        }
        if (textViewBackground != null) {
            daysLeftTextView.setBackground(textViewBackground);
        }
    }

    private void addPurposeInCalendar() {
        Intent addPurposeInCalendarIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, purpose.getTitle())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, purpose.getDate().getTime() + 18 * 60 * 60 * 1000);
        startActivity(addPurposeInCalendarIntent);
    }

    private void showDialogDeletePurpose() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deletePurpose(purpose);
                        dialogInterface.dismiss();
                        PurposeInfoActivity.this.finish();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setTitle("Вы действительно хотите удалить цель?")
                .create();
        dialog.show();
    }

    private void sharePurpose(Purpose purpose) {
        String share_text = getString(R.string.share_purpose_message, purpose.getTitle(), Utils.getStringFromDate(purpose.getDate()));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, share_text);
        intent = Intent.createChooser(intent, getString(R.string.share_purpose));
        startActivity(intent);
    }
}
