package com.wgfxer.projectpurpose.domain;


import com.wgfxer.projectpurpose.data.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface IPurposesRepository {
    LiveData<List<Purpose>> getAllPurposes();

    LiveData<Purpose> getPurposeById(int id);

    void insertPurpose(Purpose purpose);

    void updatePurpose(Purpose purpose);

    void deletePurpose(Purpose purpose);
}
