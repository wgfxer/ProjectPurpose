package com.wgfxer.projectpurpose.domain;


import androidx.lifecycle.LiveData;

import com.wgfxer.projectpurpose.models.Report;
import com.wgfxer.projectpurpose.presentation.viewmodel.ReportViewModel;


public interface IReportsRepository {

    LiveData<Report> getReportById(int reportId);

    //LiveData<Report> getReportByDate(long reportDate, int purposeId);
    Report getReportByDate(long reportDate, int purposeId);

    void insertReport(Report report, ReportViewModel.OnReportInsertedListener onReportInsertedListener);

    void updateReport(Report report, ReportViewModel.OnReportUpdatedListener onReportUpdatedListener);

}