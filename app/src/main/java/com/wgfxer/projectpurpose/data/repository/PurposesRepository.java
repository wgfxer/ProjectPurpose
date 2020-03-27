package com.wgfxer.projectpurpose.data.repository;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.database.PurposeDao;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.Purpose;
import com.wgfxer.projectpurpose.presentation.viewmodel.PurposeViewModel;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * Реализация интерфейса IPurposesRepository для доступа к таблице с целями
 * Обращается к базе данных через Dao
 */
public class PurposesRepository implements IPurposesRepository {


    private PurposeDao purposeDao;
    private Executor executor;

    public PurposesRepository(ProjectPurposeDatabase database,
                              Executor executor) {
        purposeDao = database.purposeDao();
        this.executor = executor;
    }


    /**
     * получить будущие цели
     *
     * @return liveData с будущими целями
     */
    @Override
    public LiveData<List<Purpose>> getFuturePurposes() {
        return purposeDao.getFuturePurposes(System.currentTimeMillis());
    }

    /**
     * получить выполненные цели
     *
     * @return liveData с выполненными целями
     */
    @Override
    public LiveData<List<Purpose>> getCompletedPurposes() {
        return purposeDao.getCompletedPurposes();
    }

    /**
     * получить просроченные цели
     *
     * @return liveData с просроченными целями
     */
    public LiveData<List<Purpose>> getExpiredPurposes() {
        return purposeDao.getExpiredPurposes(System.currentTimeMillis());
    }

    /**
     * Получить purpose по id
     *
     * @param id цели
     * @return liveData с целью
     */
    @Override
    public LiveData<Purpose> getPurposeById(int id) {
        return purposeDao.getPurposeById(id);
    }

    /**
     * Вставить цель
     *
     * @param purpose цель для вставки
     */
    public void insertPurpose(final Purpose purpose,@Nullable final PurposeViewModel.OnPurposeInsertedListener onPurposeInsertedListener) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                long purposeId = purposeDao.insertPurpose(purpose);
                if(onPurposeInsertedListener != null){
                    onPurposeInsertedListener.onPurposeInserted(purposeId);
                }
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
                purposeDao.updatePurpose(purpose);
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
                purposeDao.deletePurpose(purpose);
            }
        });
    }
}
