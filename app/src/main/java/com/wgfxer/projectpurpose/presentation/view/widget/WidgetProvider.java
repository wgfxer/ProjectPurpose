package com.wgfxer.projectpurpose.presentation.view.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.repository.PurposesRepository;
import com.wgfxer.projectpurpose.domain.IPurposesRepository;
import com.wgfxer.projectpurpose.helper.PreferencesHelper;
import com.wgfxer.projectpurpose.helper.WidgetHelper;
import com.wgfxer.projectpurpose.models.Purpose;

import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context,AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context,appWidgetManager,appWidgetId,appWidgetManager.getAppWidgetOptions(appWidgetId));
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        updateAppWidget(context,appWidgetManager,appWidgetId,newOptions);
    }

    void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, final Bundle appWidgetOptions){
        PreferencesHelper preferencesHelper = new PreferencesHelper(context);
        ProjectPurposeDatabase database = ProjectPurposeDatabase.getInstance(context);
        IPurposesRepository purposesRepository = new PurposesRepository(database, Executors.newSingleThreadExecutor());
        final LiveData<Purpose> purposeLiveData = purposesRepository.getPurposeById(preferencesHelper.getPurposeIdForWidget(appWidgetId));
        Observer<Purpose> observer = new Observer<Purpose>() {
            @Override
            public void onChanged(Purpose purpose) {
                if(purpose != null){
                    int width = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
                    int height = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
                    WidgetHelper widgetHelper = new WidgetHelper(context);
                    RemoteViews remoteViews = widgetHelper.getRemoteViewsForPurpose(purpose,width,height);
                    appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                    purposeLiveData.removeObserver(this);
                }
            }
        };
        purposeLiveData.observeForever(observer);
    }
}