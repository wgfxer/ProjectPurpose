package com.wgfxer.projectpurpose.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgfxer.projectpurpose.models.Report;

/**
 * Dao для таблицы с отчетами
 */
@Dao
public interface ReportDao {

    /**
     * Получить отчет по id
     * @param reportId id отчета
     * @return LiveData с отчетом
     */
    @Query("SELECT * FROM reports WHERE reportId = :reportId")
    LiveData<Report> getReportById(int reportId);

    /**
     * Получить отчет по дате для конкретной цели
     * @param reportDate дата отчета
     * @return LiveData с отчетом
     */
    /*@Query("SELECT * FROM reports WHERE reportDate = :reportDate AND purposeId = :purposeId")
    LiveData<Report> getReportByDate(long reportDate, int purposeId);*/

    /**
     * Получить отчет по дате для конкретной цели
     * @param reportDate дата отчета
     * @return отчет
     */
    @Query("SELECT * FROM reports WHERE reportDate = :reportDate AND purposeId = :purposeId")
    Report getReportByDate(long reportDate, int purposeId);

    /**
     * Вставить отчет в базу данных
     * @param report отчет для вставки
     */
    @Insert
    long insertReport(Report report);

    /**
     * Обновить отчет в базе данных
     * @param report отчет для обновления
     */
    @Update
    void updateReport(Report report);

}
