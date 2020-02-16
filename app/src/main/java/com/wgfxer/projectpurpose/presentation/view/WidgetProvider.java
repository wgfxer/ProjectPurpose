package com.wgfxer.projectpurpose.presentation.view;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.repository.PurposesRepository;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.helper.PreferencesHelper;
import com.wgfxer.projectpurpose.helper.WidgetHelper;
import com.wgfxer.projectpurpose.models.data.Purpose;

import java.util.concurrent.Executors;

import androidx.lifecycle.Observer;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(final Context context,final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (final int appWidgetId : appWidgetIds) {
            PreferencesHelper preferencesHelper = new PreferencesHelper(context);
            ProjectPurposeDatabase database = ProjectPurposeDatabase.getInstance(context);
            IPurposesRepository purposesRepository = new PurposesRepository(database, Executors.newSingleThreadExecutor());
            purposesRepository.getPurposeById(preferencesHelper.getPurposeIdForWidget(appWidgetId)).observeForever(new Observer<Purpose>() {
                @Override
                public void onChanged(Purpose purpose) {
                    WidgetHelper widgetHelper = new WidgetHelper(context);
                    RemoteViews remoteViews = widgetHelper.getRemoteViewsForPurpose(purpose);
                    appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                }
            });
        }
    }
}