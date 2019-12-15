package com.wgfxer.projectpurpose.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Реализация интерфейса репозиторий
 * Обращается к dao через database
 */
public class PurposesRepository implements IPurposesRepository {


    private static ProjectPurposeDatabase database;

    /**
     * Конструктор репозитория
     * @param context нужен для получения экземпляра бд
     */
    public PurposesRepository(Context context) {
        database = ProjectPurposeDatabase.getInstance(context.getApplicationContext());
    }


    /**
     * получить не выполненные цели
     * @return liveData с невыполненными целями
     */
    @Override
    public LiveData<List<Purpose>> getAllPurposes() {
        return database.purposeDao().getAllPurposes();
    }

    /**
     * получить выполненные цели
     * @return liveData с выполненными целями
     */
    @Override
    public LiveData<List<Purpose>> getDonePurposes() {
        return database.purposeDao().getDonePurposes();
    }

    /**
     * Получить purpose по id
     * @param id цели
     * @return liveData с целью
     */
    @Override
    public LiveData<Purpose> getPurposeById(int id) {
        return database.purposeDao().getPurposeById(id);
    }

    /**
     * Вставить цель
     * @param purpose цель для вставки
     */
    public void insertPurpose(Purpose purpose) {
        new InsertPurposeTask().execute(purpose);
    }

    /**
     * Обновить цель в бд
     * @param purpose цель для обновления
     */
    public void updatePurpose(Purpose purpose) {
        new UpdatePurposeTask().execute(purpose);
    }

    /**
     * удалить цель
     * @param purpose цель для удаления
     */
    public void deletePurpose(Purpose purpose) {
        new DeletePurposeTask().execute(purpose);
    }

    /**
     * Асинктаск для асинхронной вставки цели
     */
    private static class InsertPurposeTask extends AsyncTask<Purpose, Void, Void> {
        @Override
        protected Void doInBackground(Purpose... purposes) {
            if (purposes != null && purposes.length > 0) {
                database.purposeDao().insertPurpose(purposes[0]);
            }
            return null;
        }
    }

    /**
     * АсинкТаск для асинхронного обновления цели
     */
    private static class UpdatePurposeTask extends AsyncTask<Purpose, Void, Void> {
        @Override
        protected Void doInBackground(Purpose... purposes) {
            if (purposes != null && purposes.length > 0) {
                database.purposeDao().updatePurpose(purposes[0]);
            }
            return null;
        }
    }


    /**
     * Асинктаск для асинхронного удаления цели
     */
    private static class DeletePurposeTask extends AsyncTask<Purpose, Void, Void> {
        @Override
        protected Void doInBackground(Purpose... purposes) {
            if (purposes != null && purposes.length > 0) {
                database.purposeDao().deletePurpose(purposes[0]);
            }
            return null;
        }
    }
}
