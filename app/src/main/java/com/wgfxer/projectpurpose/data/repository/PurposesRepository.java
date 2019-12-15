package com.wgfxer.projectpurpose.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;

/**
 * Реализация интерфейса репозиторий
 * Обращается к dao через database
 */
public class PurposesRepository implements IPurposesRepository {


    private ProjectPurposeDatabase database;
    private Executor executor;

    public PurposesRepository(ProjectPurposeDatabase database,
                              Executor executor) {
        this.database = database;
        this.executor = executor;
    }


    /**
     * получить не выполненные цели
     *
     * @return liveData с невыполненными целями
     */
    @Override
    public LiveData<List<Purpose>> getAllPurposes() {
        return database.purposeDao().getAllPurposes();
    }

    /**
     * получить выполненные цели
     *
     * @return liveData с выполненными целями
     */
    @Override
    public LiveData<List<Purpose>> getDonePurposes() {
        return database.purposeDao().getDonePurposes();
    }

    /**
     * Получить purpose по id
     *
     * @param id цели
     * @return liveData с целью
     */
    @Override
    public LiveData<Purpose> getPurposeById(int id) {
        return database.purposeDao().getPurposeById(id);
    }

    /**
     * Вставить цель
     *
     * @param purpose цель для вставки
     */
    public void insertPurpose(final Purpose purpose) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.purposeDao().insertPurpose(purpose);
            }
        });
    }

    /**
     * Обновить цель в бд
     *
     * @param purpose цель для обновления
     */
    public void updatePurpose(final Purpose purpose) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.purposeDao().updatePurpose(purpose);
            }
        });
    }

    /**
     * удалить цель
     *
     * @param purpose цель для удаления
     */
    public void deletePurpose(final Purpose purpose) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.purposeDao().deletePurpose(purpose);
            }
        });
    }
}
