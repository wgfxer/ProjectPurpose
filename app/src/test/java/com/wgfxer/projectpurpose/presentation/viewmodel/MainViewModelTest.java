package com.wgfxer.projectpurpose.presentation.viewmodel;

import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.data.Purpose;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    Observer observer;

    @Mock
    IPurposesRepository repository;

    MainViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new MainViewModel(repository);
    }

    @Test
    public void getPurposesTest() {
        LiveData<List<Purpose>> liveDataPurposes = liveDataListExample();
        Mockito.when(repository.getFuturePurposes()).thenReturn(liveDataPurposes);

        viewModel.getFuturePurposes().observeForever(observer);

        Mockito.verify(observer).onChanged(liveDataPurposes.getValue());
    }

    @Test
    public void getDonePurposesTest() {
        LiveData<List<Purpose>> liveDataPurposes = liveDataListExample();

        Mockito.when(repository.getCompletedPurposes()).thenReturn(liveDataPurposes);

        viewModel.getCompletedPurposes().observeForever(observer);

        Mockito.verify(observer).onChanged(liveDataPurposes.getValue());
    }

    @Test
    public void getPurposeById() {
        LiveData<Purpose> liveDataPurpose = liveDataPurposeExample();

        Mockito.when(repository.getPurposeById(Mockito.anyInt())).thenReturn(liveDataPurpose);

        viewModel.getPurposeById(4).observeForever(observer);

        Mockito.verify(observer).onChanged(liveDataPurpose.getValue());
    }

    @Test
    public void insertPurpose() {
        Purpose purpose = new Purpose();

        viewModel.insertPurpose(purpose);

        Mockito.verify(repository).insertPurpose(purpose);
    }

    @Test
    public void updatePurpose() {
        Purpose purpose = new Purpose();

        viewModel.updatePurpose(purpose);

        Mockito.verify(repository).updatePurpose(purpose);
    }

    @Test
    public void deletePurpose() {
        Purpose purpose = new Purpose();

        viewModel.deletePurpose(purpose);

        Mockito.verify(repository).deletePurpose(purpose);
    }

    private LiveData<List<Purpose>> liveDataListExample() {
        List<Purpose> purposes = new ArrayList<>(Arrays.asList(new Purpose(), new Purpose()));
        return new MutableLiveData<>(purposes);
    }

    private LiveData<Purpose> liveDataPurposeExample() {
        return new MutableLiveData<>(new Purpose());
    }


}