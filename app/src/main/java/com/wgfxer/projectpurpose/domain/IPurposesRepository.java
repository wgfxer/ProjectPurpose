package com.wgfxer.projectpurpose.domain;


import com.wgfxer.projectpurpose.models.Purpose;
import com.wgfxer.projectpurpose.presentation.viewmodel.PurposeViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;


/**
 * интерфейс репозитория для доступа к целям
 */
public interface IPurposesRepository {
    LiveData<List<Purpose>> getFuturePurposes();

    LiveData<List<Purpose>> getCompletedPurposes();

    LiveData<List<Purpose>> getExpiredPurposes();

    LiveData<Purpose> getPurposeById(int id);

    void insertPurpose(Purpose purpose, PurposeViewModel.OnPurposeInsertedListener onPurposeInsertedListener);

    void updatePurpose(Purpose purpose);

    void deletePurpose(Purpose purpose);
}
