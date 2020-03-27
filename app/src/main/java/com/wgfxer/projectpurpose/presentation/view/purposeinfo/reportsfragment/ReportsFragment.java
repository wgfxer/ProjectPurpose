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
import com.wgfxer.projectpurpose.models.Report;
import com.wgfxer.projectpurpose.presentation.viewmodel.ReportViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.ViewModelFactory;

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
    private static final String KEY_PURPOSE_ID = "KEY_PURPOSE_ID";

    private CalendarView calendarView;

    private long selectedDate;
    private int purposeId;

    private TextView reportTitleTextView;
    private TextView reportDescriptionTextView;
    private TextView reportDidGoodTextView;
    private TextView reportCouldBetterTextView;

    private Button createReportButton;
    private ImageButton editReportImageButton;
    private ImageButton copyReportImageButton;

    private CardView cardViewReport;

    private ReportViewModel viewModel;
    private Report currentReport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.reports_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        if (savedInstanceState != null) {
            calendarView.setDate(selectedDate);
        }
        selectedDate = Utils.getIndependentTime(calendarView.getDate());
        purposeId = getArguments().getInt(KEY_PURPOSE_ID);
        calendarView.setMaxDate(System.currentTimeMillis());
        observeViewModel();
        viewModel.updateReportLiveData(selectedDate,purposeId);
        setListeners();
    }

    private void observeViewModel() {
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(getContext()))
                .get(ReportViewModel.class);
        viewModel.getReport().observe(this, new Observer<Report>() {
            @Override
            public void onChanged(Report report) {
                ReportsFragment.this.currentReport = report;
                if(report == null){
                    showButtonNewNote();
                }else{
                    showReport(report);
                }
            }
        });
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
     * Устанавливает слушатели на календарь, кнопку изменения и кнопку добавления отчета
     */
    private void setListeners() {
        editReportImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReportDialogFragment editReportDialog = EditReportDialogFragment.getEditInstance(currentReport, new EditReportDialogFragment.OnEditReportListener() {
                    @Override
                    public void onEditReport(Report report) {
                        viewModel.updateReport(report, new ReportViewModel.OnReportUpdatedListener() {
                            @Override
                            public void onReportUpdated() {
                                viewModel.updateReportLiveData(selectedDate,purposeId);
                            }
                        });
                    }
                });
                editReportDialog.show(getChildFragmentManager(), null);
            }
        });

        createReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReportDialogFragment addReportDialog = EditReportDialogFragment.getAddInstance(selectedDate,purposeId, new EditReportDialogFragment.OnEditReportListener() {
                    @Override
                    public void onEditReport(Report report) {
                        viewModel.insertReport(report, new ReportViewModel.OnReportInsertedListener() {
                            @Override
                            public void onReportInserted(long reportId) {
                                viewModel.updateReportLiveData(selectedDate,purposeId);
                            }
                        });
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
                viewModel.updateReportLiveData(selectedDate,purposeId);
            }
        });

        copyReportImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportString = Utils.getStringFromDate(new Date(currentReport.getReportDate())) +
                        " " + currentReport.getTitleReport() +
                        "\n" + currentReport.getDescriptionReport() +
                        "\n" + getString(R.string.what_did_good_text) +
                        "\n" + currentReport.getWhatDidGood() +
                        "\n" + getString(R.string.what_could_better_text) +
                        "\n" + currentReport.getWhatCouldBetter();
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
     * Скрывает отчет и отображает кнопку добавления отчета
     */
    private void showButtonNewNote(){
        createReportButton.setVisibility(View.VISIBLE);
        cardViewReport.setVisibility(View.INVISIBLE);
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

    /**
     * Возвращает новый экземпляр фрагмента для конкретной цели
     */
    public static ReportsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(KEY_PURPOSE_ID, id);
        ReportsFragment fragment = new ReportsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
