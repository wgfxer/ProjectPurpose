package com.wgfxer.projectpurpose.presentation.viewmodel;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.WidgetProvider;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_DATE;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_GRADIENT_ALPHA;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_GRADIENT_ID;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_IMAGE_PATH;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_TITLE;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.SHARED_PREFS;


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
        updateWidget(purpose);
        repository.updatePurpose(purpose);
    }

    private void updateWidget(Purpose purpose) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        int id = purpose.getId();
        String daysLeft;
        if (Utils.getDaysFromDate(purpose.getDate()) > 0) {
            daysLeft = context.getResources()
                    .getQuantityString(R.plurals.days_count,
                            Utils.getDaysFromDate(purpose.getDate()), Utils.getDaysFromDate(purpose.getDate()));
        } else {
            daysLeft = context.getResources().getString(R.string.time_end_text);
        }
        prefs.edit().putString(KEY_PURPOSE_TITLE + id, purpose.getTitle())
                .putString(KEY_PURPOSE_DATE + id, daysLeft)
                .putFloat(KEY_PURPOSE_GRADIENT_ALPHA + id, purpose.getTheme().getGradientAlpha())
                .putInt(KEY_PURPOSE_GRADIENT_ID + id, purpose.getTheme().getGradientId())
                .putString(KEY_PURPOSE_IMAGE_PATH + id, purpose.getTheme().getImagePath())
                .apply();
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
