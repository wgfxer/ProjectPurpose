package com.wgfxer.projectpurpose.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.purposeslist.PurposesAdapter;
import com.wgfxer.projectpurpose.presentation.view.purposeslist.PurposesListFragment;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModelFactory;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AppWidgetConfigActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "prefs";
    public static final String KEY_PURPOSE_TITLE = "PURPOSE_TITLE";
    public static final String KEY_PURPOSE_DATE = "PURPOSE_DATE";
    public static final String KEY_PURPOSE_GRADIENT_ID = "PURPOSE_GRADIENT_ID";
    public static final String KEY_PURPOSE_IMAGE_PATH = "PURPOSE_IMAGE_PATH";
    public static final String KEY_PURPOSE_GRADIENT_ALPHA = "PURPOSE_GRADIENT_ALPHA";
    public static final String KEY_PURPOSE_ID = "PURPOSE_ID";

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
        setResult(RESULT_CANCELED);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }


        recyclerViewPurposes = findViewById(R.id.recycler_view_purposes);
        final PurposesAdapter adapter = new PurposesAdapter();
        recyclerViewPurposes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPurposes.setAdapter(adapter);
        MainViewModel viewModel = ViewModelProviders.of(this, new MainViewModelFactory(this)).get(MainViewModel.class);
        viewModel.getPurposes().observe(this, new Observer<List<Purpose>>() {
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

                Intent intent = new Intent(AppWidgetConfigActivity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(AppWidgetConfigActivity.this, 0, intent, 0);


                RemoteViews remoteViews = new RemoteViews(AppWidgetConfigActivity.this.getPackageName(), R.layout.widget_layout);
                remoteViews.setOnClickPendingIntent(R.id.relative_layout, pendingIntent);
                if (Utils.getDaysFromDate(purpose.getDate()) > 0) {
                    remoteViews.setCharSequence(R.id.text_view_days, "setText",
                            getResources().getQuantityString(R.plurals.days_count,
                                    Utils.getDaysFromDate(purpose.getDate()), Utils.getDaysFromDate(purpose.getDate())));
                } else {
                    remoteViews.setCharSequence(R.id.text_view_days, "setText", AppWidgetConfigActivity.this.getString(R.string.time_end_text));
                }
                remoteViews.setImageViewResource(R.id.image_view_gradient, purpose.getTheme().getGradientId());
                if (purpose.getTheme().getImagePath() != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(purpose.getTheme().getImagePath());
                    if (bitmap.getByteCount() > 6913000) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.1), (int) (bitmap.getHeight() * 0.1), false);

                    }
                    remoteViews.setImageViewBitmap(R.id.image_view_photo, bitmap);
                }
                remoteViews.setInt(R.id.image_view_gradient, "setAlpha", (int) (purpose.getTheme().getGradientAlpha() * 100));
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                sharedPreferences.edit().putInt(KEY_PURPOSE_ID + appWidgetId, purpose.getId()).apply();

                Intent resultIntent = new Intent();
                resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
