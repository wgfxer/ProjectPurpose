package com.wgfxer.projectpurpose.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PurposeDao {
    @Query("SELECT * FROM purposes")
    LiveData<List<Purpose>> getAllPurposes();

    @Insert
    void insertPurpose(Purpose purpose);

    @Delete
    void deletePurpose(Purpose purpose);

    @Update
    void updatePurpose(Purpose purpose);

    @Query("SELECT * FROM purposes WHERE id = :purposeId")
    LiveData<Purpose> getPurposeById(int purposeId);
}