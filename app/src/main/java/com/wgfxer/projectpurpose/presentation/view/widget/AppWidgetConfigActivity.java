package com.wgfxer.projectpurpose.presentation.view.widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.helper.PreferencesHelper;
import com.wgfxer.projectpurpose.helper.WidgetHelper;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.purposeslist.PurposesAdapter;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.util.List;

public class AppWidgetConfigActivity extends AppCompatActivity {

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private RecyclerView recyclerViewPurposes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_widget_config);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED,resultIntent);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        recyclerViewPurposes = findViewById(R.id.recycler_view_purposes);
        final PurposesAdapter adapter = new PurposesAdapter();
        recyclerViewPurposes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPurposes.setAdapter(adapter);
        MainViewModel viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this)).get(MainViewModel.class);
        viewModel.getFuturePurposes().observe(this, new Observer<List<Purpose>>() {
            @Override
            public void onChanged(List<Purpose> purposes) {
                if (purposes != null) {
                    adapter.setPurposesList(purposes);
                }
            }
        });
        adapter.setOnPurposeClickListener(new PurposesAdapter.OnPurposeClickListener() {
            @Override
            public void onPurposeClick(Purpose purpose) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(AppWidgetConfigActivity.this);
                WidgetHelper widgetHelper = new WidgetHelper(AppWidgetConfigActivity.this);
                RemoteViews remoteViews = widgetHelper.getRemoteViewsForPurpose(purpose, 110, 40);

                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

                PreferencesHelper preferencesHelper = new PreferencesHelper(AppWidgetConfigActivity.this);
                preferencesHelper.putPurposeIdForWidget(purpose.getId(),appWidgetId);

                Intent resultIntent = new Intent();
                resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
    }
}
