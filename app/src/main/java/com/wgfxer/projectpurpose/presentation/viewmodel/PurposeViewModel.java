package com.wgfxer.projectpurpose.presentation.viewmodel;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.models.Purpose;
import com.wgfxer.projectpurpose.presentation.view.widget.WidgetProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


/**
 * MainViewModel, в методах вызывает методы интерфейса IPurposesRepository
 */
public class MainViewModel extends ViewModel {

    private IPurposesRepository repository;
    private Context context;

    public MainViewModel(IPurposesRepository repository, Context context) {
        this.repository = repository;
        this.context = context.getApplicationContext();
    }

    public LiveData<List<Purpose>> getFuturePurposes() {
        return repository.getFuturePurposes();
    }

    public LiveData<List<Purpose>> getCompletedPurposes() {
        return repository.getCompletedPurposes();
    }

    public LiveData<List<Purpose>> getExpiredPurposes() {
        return repository.getExpiredPurposes();
    }

    public LiveData<Purpose> getPurposeById(int id) {
        return repository.getPurposeById(id);
    }

    public void insertPurpose(Purpose purpose) {
        repository.insertPurpose(purpose);
    }

    public void updatePurpose(Purpose purpose) {
        updateWidgets();
        repository.updatePurpose(purpose);
    }

    private void updateWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        Intent updateIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(updateIntent);
    }

    public void deletePurpose(Purpose purpose) {
        repository.deletePurpose(purpose);
    }

}
