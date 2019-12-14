package com.wgfxer.projectpurpose.presentation.viewmodel;


import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private IPurposesRepository repository;

    MainViewModel(IPurposesRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Purpose>> getPurposes() {
        return repository.getAllPurposes();
    }

    public LiveData<List<Purpose>> getDonePurposes() {
        return repository.getDonePurposes();
    }

    public LiveData<Purpose> getPurposeById(int id) {
        return repository.getPurposeById(id);
    }

    public void insertPurpose(Purpose purpose) {
        repository.insertPurpose(purpose);
    }

    public void updatePurpose(Purpose purpose) {
        repository.updatePurpose(purpose);
    }

    public void deletePurpose(Purpose purpose) {
        repository.deletePurpose(purpose);
    }

}
