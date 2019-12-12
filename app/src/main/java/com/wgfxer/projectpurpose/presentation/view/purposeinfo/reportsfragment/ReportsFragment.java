package com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.models.domain.PurposeTheme;
import com.wgfxer.projectpurpose.models.domain.Report;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ReportsFragment extends Fragment {
    private static final String KEY_PICKED_DATE = "KEY_PICKED_DATE";
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";

    private CalendarView calendarView;

    private long selectedDate;

    private TextView reportTitleTextView;
    private TextView reportDescriptionTextView;
    private TextView reportDidGoodTextView;
    private TextView reportCouldBetterTextView;

    private Button createReportButton;
    private ImageButton editReportImageButton;

    private CardView cardViewReport;

    private MainViewModel viewModel;
    private Purpose purpose;
    private Report report;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        selectedDate = getIndependentTime(calendarView.getDate());
        calendarView.setMaxDate(System.currentTimeMillis());
        observeViewModel();
        setListeners();
        if (savedInstanceState != null) {
            selectedDate = savedInstanceState.getLong(KEY_PICKED_DATE);
            calendarView.setDate(selectedDate);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PICKED_DATE, selectedDate);
    }

    public static ReportsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, id);
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void findViews(@NonNull View view) {
        calendarView = view.findViewById(R.id.calendar_view_reports);
        reportTitleTextView = view.findViewById(R.id.text_view_report_title);
        reportDescriptionTextView = view.findViewById(R.id.text_view_report_body);
        reportDidGoodTextView = view.findViewById(R.id.what_did_good_text_view);
        reportCouldBetterTextView = view.findViewById(R.id.what_could_better_text_view);
        createReportButton = view.findViewById(R.id.button_write_report);
        editReportImageButton = view.findViewById(R.id.edit_image_button);
        cardViewReport = view.findViewById(R.id.card_view_report);
    }

    private void observeViewModel() {
        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        viewModel.getPurposeById(getArguments().getInt(KEY_PURPOSE_ID)).observe(this, new Observer<Purpose>() {
            @Override
            public void onChanged(Purpose purpose) {
                ReportsFragment.this.purpose = purpose;
                showCurrentReport();
                setButtonColor(purpose.getTheme());
            }
        });
    }

    private void setButtonColor(PurposeTheme theme){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GradientDrawable gradientDrawable = (GradientDrawable)getContext().getDrawable(theme.getGradientId());
            Drawable cornerBackround = getContext().getDrawable(R.drawable.solid_button);
            int[][] states = new int[][] {
                    new int[] { android.R.attr.state_pressed},
                    new int[] {-android.R.attr.state_pressed}
            };

            int[] colors = new int[] {
                    ColorUtils.blendARGB(gradientDrawable.getColors()[1], Color.BLACK,0.2f),
                    gradientDrawable.getColors()[1]
            };

            ColorStateList myList = new ColorStateList(states, colors);
            cornerBackround.setTintList(myList);
            createReportButton.setBackground(cornerBackround);
        }
    }

    private void setListeners() {
        editReportImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReportDialog editReportDialog = EditReportDialog.getEditInstance(report);
                editReportDialog.setOnEditReportListener(new EditReportDialog.OnEditReportListener() {
                    @Override
                    public void onEditReport(Report report) {
                        ReportsFragment.this.report = report;
                        showReport(report);
                        saveReport(report);
                    }
                });
                editReportDialog.show(getChildFragmentManager(), null);
            }
        });

        createReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReportDialog addReportDialog = EditReportDialog.getAddInstance(selectedDate);
                addReportDialog.setOnEditReportListener(new EditReportDialog.OnEditReportListener() {
                    @Override
                    public void onEditReport(Report report) {
                        ReportsFragment.this.report = report;
                        showReport(report);
                        addReport(report);
                    }
                });
                addReportDialog.show(getChildFragmentManager(), null);
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate = getIndependentTime(calendar.getTimeInMillis());
                showCurrentReport();
            }
        });
    }

    private long getIndependentTime(long time) {
        Calendar calendarResult = Calendar.getInstance();
        calendarResult.setTimeInMillis(0);
        Calendar calendarFromView = Calendar.getInstance();
        calendarFromView.setTimeInMillis(time);
        calendarResult.set(Calendar.YEAR, calendarFromView.get(Calendar.YEAR));
        calendarResult.set(Calendar.MONTH, calendarFromView.get(Calendar.MONTH));
        calendarResult.set(Calendar.DAY_OF_MONTH, calendarFromView.get(Calendar.DAY_OF_MONTH));
        return calendarResult.getTimeInMillis();
    }

    private void addReport(Report report) {
        purpose.getReportsList().add(report);
        viewModel.updatePurpose(purpose);
    }

    private void saveReport(Report report) {
        for (int i = 0; i < purpose.getReportsList().size(); i++) {
            if (purpose.getReportsList().get(i).getDateReport() == report.getDateReport()) {
                purpose.getReportsList().set(i, report);
            }
            viewModel.updatePurpose(purpose);
        }
    }

    private void showCurrentReport() {
        report = getReportBySelectedDate();
        if (report != null) {
            showReport(report);
        } else {
            createReportButton.setVisibility(View.VISIBLE);
            cardViewReport.setVisibility(View.INVISIBLE);
        }
    }

    private Report getReportBySelectedDate() {
        Report report = null;
        for (Report rep : purpose.getReportsList()) {
            if (rep.getDateReport() == selectedDate) {
                report = rep;
            }
        }
        return report;
    }

    private void showReport(Report report) {
        createReportButton.setVisibility(View.INVISIBLE);
        cardViewReport.setVisibility(View.VISIBLE);
        reportTitleTextView.setText(report.getTitleReport());
        reportDescriptionTextView.setText(report.getDescriptionReport());
        reportDidGoodTextView.setText(report.getWhatDidGood());
        reportCouldBetterTextView.setText(report.getWhatCouldBetter());
    }


}
