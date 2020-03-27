package com.wgfxer.projectpurpose.presentation.viewmodel;



import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wgfxer.projectpurpose.domain.IReportsRepository;
import com.wgfxer.projectpurpose.models.Report;

import java.util.concurrent.Executors;

/**
 * ВьюМодель для отчетов
 */
public class ReportViewModel extends ViewModel {

    private IReportsRepository repository;
    private MutableLiveData<Report> report;

    public ReportViewModel(IReportsRepository repository) {
        this.repository = repository;
        report = new MutableLiveData<>();
    }

    public LiveData<Report> getReportById(int reportId){
        return repository.getReportById(reportId);
    }

    public void updateReportLiveData(final long reportDate,final int purposeId){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                report.postValue(repository.getReportByDate(reportDate,purposeId));
            }
        });
    }

    public LiveData<Report> getReport(){
        return report;
    }

    public void insertReport(Report report,OnReportInsertedListener onReportInsertedListener ){
        repository.insertReport(report, onReportInsertedListener);
    }

    public void updateReport(Report report, OnReportUpdatedListener onReportUpdatedListener){
        repository.updateReport(report, onReportUpdatedListener);
    }

    public interface OnReportInsertedListener{
        void onReportInserted(long reportId);
    }

    public interface OnReportUpdatedListener{
        void onReportUpdated();
    }
}