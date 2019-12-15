package com.wgfxer.projectpurpose.presentation.view.purposeinfo.reportsfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.models.domain.Report;
import com.wgfxer.projectpurpose.presentation.view.MainActivity;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * Фрагмент содержащий календарь с отчетами и сам отчет по выбранной или кнопку для добавления
 */
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
    private ImageButton copyReportImageButton;

    private CardView cardViewReport;

    private MainViewModel viewModel;
    private Purpose purpose;
    private Report report;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reports_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        selectedDate = Utils.getIndependentTime(calendarView.getDate());
        calendarView.setMaxDate(System.currentTimeMillis());
        observeViewModel();
        setListeners();
        if (savedInstanceState != null) {
            selectedDate = savedInstanceState.getLong(KEY_PICKED_DATE);
            calendarView.setDate(selectedDate);
        }
    }

    /**
     * Сохранение состояния выбранной даты
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PICKED_DATE, selectedDate);
    }

    /**
     * возвращает новый экземпляр фрагмента для конкретной цели
     */
    public static ReportsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, id);
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Находит все нужные view в заданном view
     */
    private void findViews(@NonNull View view) {
        calendarView = view.findViewById(R.id.calendar_view_reports);
        reportTitleTextView = view.findViewById(R.id.text_view_report_title);
        reportDescriptionTextView = view.findViewById(R.id.text_view_report_body);
        reportDidGoodTextView = view.findViewById(R.id.what_did_good_text_view);
        reportCouldBetterTextView = view.findViewById(R.id.what_could_better_text_view);
        createReportButton = view.findViewById(R.id.button_write_report);
        editReportImageButton = view.findViewById(R.id.edit_image_button);
        copyReportImageButton = view.findViewById(R.id.copy_report_image_button);
        cardViewReport = view.findViewById(R.id.card_view_report);
    }

    /**
     * подписка на вьюмодель
     * после подписки отображает текущий отчет
     */
    private void observeViewModel() {
        viewModel = ViewModelProviders.of(this, new MainViewModelFactory(getContext()))
                .get(MainViewModel.class);
        viewModel.getPurposeById(getArguments().getInt(KEY_PURPOSE_ID)).observe(this, new Observer<Purpose>() {
            @Override
            public void onChanged(Purpose purpose) {
                if (purpose != null) {
                    ReportsFragment.this.purpose = purpose;
                    showCurrentReport();
                }
            }
        });
    }

    /**
     * устанавливает слушатели на календарь, кнопку изменения и кнопку добавления отчета
     */
    private void setListeners() {
        editReportImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReportDialog editReportDialog = EditReportDialog.getEditInstance(report, new EditReportDialog.OnEditReportListener() {
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
                EditReportDialog addReportDialog = EditReportDialog.getAddInstance(selectedDate, new EditReportDialog.OnEditReportListener() {
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
                selectedDate = Utils.getIndependentTime(calendar.getTimeInMillis());
                showCurrentReport();
            }
        });

        copyReportImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportString = Utils.getStringFromDate(new Date(report.getDateReport())) +
                        " " + report.getTitleReport() +
                        "\n" + report.getDescriptionReport() +
                        "\n" + getString(R.string.what_did_good_text) +
                        "\n" + report.getWhatDidGood() +
                        "\n" + getString(R.string.what_could_better_text) +
                        "\n" + report.getWhatCouldBetter();
                copyText(reportString.replaceAll("null", ""));
            }
        });
    }

    /**
     * Копирует текст
     *
     * @param text текст для копирования
     */
    private void copyText(String text) {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(
                    ClipData.newPlainText(
                            getString(R.string.clipboard_title), text
                    )
            );
            Toast.makeText(getContext(), R.string.toast_copied, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * добавляет отчет в цель
     */
    private void addReport(Report report) {
        purpose.getReportsList().add(report);
        viewModel.updatePurpose(purpose);
    }

    /**
     * сохраняет уже созданный отчет у цели
     */
    private void saveReport(Report report) {
        for (int i = 0; i < purpose.getReportsList().size(); i++) {
            if (purpose.getReportsList().get(i).getDateReport() == report.getDateReport()) {
                purpose.getReportsList().set(i, report);
            }
            viewModel.updatePurpose(purpose);
        }
    }

    /**
     * отображает текущий отчет по дате, если отчета нет показывает кнопку для добавления
     */
    private void showCurrentReport() {
        report = getReportBySelectedDate();
        if (report != null) {
            showReport(report);
        } else {
            createReportButton.setVisibility(View.VISIBLE);
            cardViewReport.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * ищет отчет в цели, если его нет возвращает null
     */
    @Nullable
    private Report getReportBySelectedDate() {
        Report report = null;
        for (Report rep : purpose.getReportsList()) {
            if (rep.getDateReport() == selectedDate) {
                report = rep;
            }
        }
        return report;
    }

    /**
     * Скрывает кнопку добавления и отображает отчет
     *
     * @param report отчет для отображения
     */
    private void showReport(Report report) {
        createReportButton.setVisibility(View.INVISIBLE);
        cardViewReport.setVisibility(View.VISIBLE);
        reportTitleTextView.setText(report.getTitleReport());
        reportDescriptionTextView.setText(report.getDescriptionReport());
        reportDidGoodTextView.setText(report.getWhatDidGood());
        reportCouldBetterTextView.setText(report.getWhatCouldBetter());
    }


}
