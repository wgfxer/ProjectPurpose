package com.wgfxer.projectpurpose.domain;


import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;


/**
 * интерфейс репозитория, реализует PurposesRepository
 */
public interface IPurposesRepository {
    LiveData<List<Purpose>> getAllPurposes();

    LiveData<List<Purpose>> getDonePurposes();

    LiveData<Purpose> getPurposeById(int id);

    void insertPurpose(Purpose purpose);

    void updatePurpose(Purpose purpose);

    void deletePurpose(Purpose purpose);
}
