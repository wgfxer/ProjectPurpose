package com.wgfxer.projectpurpose.domain;


import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;


/**
 * интерфейс репозитория, реализует PurposesRepository
 */
public interface IPurposesRepository {
    LiveData<List<Purpose>> getFuturePurposes();

    LiveData<List<Purpose>> getCompletedPurposes();

    LiveData<List<Purpose>> getExpiredPurposes();

    LiveData<Purpose> getPurposeById(int id);

    void insertPurpose(Purpose purpose);

    void updatePurpose(Purpose purpose);

    void deletePurpose(Purpose purpose);
}
