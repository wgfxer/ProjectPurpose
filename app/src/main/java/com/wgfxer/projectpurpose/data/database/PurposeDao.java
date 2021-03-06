package com.wgfxer.projectpurpose.data.database;

import com.wgfxer.projectpurpose.models.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * DAO для таблицы с целями
 */
@Dao
public interface PurposeDao {
    /**
     * Запрос всех будущих целей
     *
     * @return liveData с целями
     */
    @Query("SELECT * FROM purposes WHERE isDone = 0 AND date >=  :currentDate")
    LiveData<List<Purpose>> getFuturePurposes(long currentDate);

    /**
     * Запрос всех выполненных целей
     *
     * @return liveData с целями
     */
    @Query("SELECT * FROM purposes WHERE isDone = 1")
    LiveData<List<Purpose>> getCompletedPurposes();

    /**
     * Запрос всех просроченных целей
     *
     * @return liveData с целями
     */
    @Query("SELECT * FROM purposes WHERE isDone = 0 AND date < :currentDate")
    LiveData<List<Purpose>> getExpiredPurposes(long currentDate);


    /**
     * Вставляет новую цель
     *
     * @param purpose цель для вставки
     */
    @Insert
    long insertPurpose(Purpose purpose);


    /**
     * Удаляет цель
     *
     * @param purpose цель для удаления
     */
    @Delete
    void deletePurpose(Purpose purpose);

    /**
     * Обновляет цель
     *
     * @param purpose цель для обновления
     */
    @Update
    void updatePurpose(Purpose purpose);

    /**
     * Получает цель по id
     *
     * @param purposeId id цели
     * @return liveData с целью
     */
    @Query("SELECT * FROM purposes WHERE id = :purposeId")
    LiveData<Purpose> getPurposeById(int purposeId);
}
