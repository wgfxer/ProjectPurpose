package com.wgfxer.projectpurpose.data.repository;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.database.PurposeDao;
import com.wgfxer.projectpurpose.models.data.Purpose;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.impl.utils.SynchronousExecutor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class PurposesRepositoryTest {

    @Mock
    ProjectPurposeDatabase database;

    @Mock
    PurposeDao dao;

    PurposesRepository repository;

    @Before
    public void setUp() {
        repository = new PurposesRepository(database, new SynchronousExecutor());
        when(database.purposeDao()).thenReturn(dao);

    }

    @Test
    public void getAllPurposesTest() {
        LiveData<List<Purpose>> liveDataPurposes = liveDataListExample();

        when(dao.getAllPurposes()).thenReturn(liveDataPurposes);

        assertThat(repository.getAllPurposes(), is(liveDataPurposes));
    }

    @Test
    public void getDonePurposesTest() {
        LiveData<List<Purpose>> liveDataPurposes = liveDataListExample();

        when(dao.getDonePurposes()).thenReturn(liveDataPurposes);

        assertThat(repository.getDonePurposes(), is(liveDataPurposes));
    }


    @Test
    public void getPurposeByIdTest() {
        LiveData<Purpose> liveDataPurpose = liveDataPurposeExample();

        when(dao.getPurposeById(Mockito.anyInt())).thenReturn(liveDataPurpose);

        assertThat(repository.getPurposeById(6), is(liveDataPurpose));
    }

    @Test
    public void insertPurposeTest() {
        Purpose purpose = new Purpose();

        repository.insertPurpose(purpose);

        verify(dao).insertPurpose(purpose);
    }

    @Test
    public void updatePurposeTest() {
        Purpose purpose = new Purpose();

        repository.updatePurpose(purpose);

        verify(dao).updatePurpose(purpose);
    }

    @Test
    public void deletePurposeTest() {
        Purpose purpose = new Purpose();

        repository.deletePurpose(purpose);

        verify(dao).deletePurpose(purpose);
    }


    private LiveData<List<Purpose>> liveDataListExample() {
        List<Purpose> purposes = new ArrayList<>(Arrays.asList(new Purpose(), new Purpose()));
        return new MutableLiveData<>(purposes);
    }


    private LiveData<Purpose> liveDataPurposeExample() {
        return new MutableLiveData<>(new Purpose());
    }
}