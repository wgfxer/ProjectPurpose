package com.wgfxer.projectpurpose.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.database.ReportDao;
import com.wgfxer.projectpurpose.domain.IReportsRepository;
import com.wgfxer.projectpurpose.models.Report;
import com.wgfxer.projectpurpose.presentation.viewmodel.ReportViewModel;

import java.util.concurrent.Executor;

/**
 * Реализация интерфейса IReportsRepository для доступа к таблице с отчетами
 * Обращается к базе данных через Dao
 */
public class ReportsRepository implements IReportsRepository {

    private ReportDao reportDao;
    private Executor executor;

    public ReportsRepository(ProjectPurposeDatabase database,
                           Executor executor) {
        reportDao = database.reportDao();
        this.executor = executor;
    }

    /**
     * Получить отчет по id
     * @param reportId id отчета
     * @return LiveData с отчетом
     */
    public LiveData<Report> getReportById(int reportId){
        return reportDao.getReportById(reportId);
    }

    /**
     * Получить отчет по дате для конкретной цели
     * @param reportDate дата отчета
     * @param purposeId id цели для которой получаем отчет
     * @return LiveData c отчетом
     */
    /*@Override
    public LiveData<Report> getReportByDate(long reportDate, int purposeId) {
        return reportDao.getReportByDate(reportDate, purposeId);
    }*/
    @Override
    public Report getReportByDate(long reportDate, int purposeId) {
        return reportDao.getReportByDate(reportDate, purposeId);
    }

    /**
     * Вставить отчет
     * @param report отчет для вставки
     */
    @Override
    public void insertReport(final Report report,@Nullable final ReportViewModel.OnReportInsertedListener onReportInsertedListener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long reportId = reportDao.insertReport(report);
                if(onReportInsertedListener != null){
                    onReportInsertedListener.onReportInserted(reportId);
                }
            }
        });
    }

    /**
     * Обновить отчет
     * @param report отчет для обновления
     */
    @Override
    public void updateReport(final Report report,@Nullable final ReportViewModel.OnReportUpdatedListener onReportUpdatedListener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                reportDao.updateReport(report);
                if(onReportUpdatedListener != null){
                    onReportUpdatedListener.onReportUpdated();
                }
            }
        });
    }
}
