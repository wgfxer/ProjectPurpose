package com.wgfxer.projectpurpose.presentation.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.data.database.ProjectPurposeDatabase;
import com.wgfxer.projectpurpose.data.repository.PurposesRepository;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.viewmodel.MainViewModel;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executors;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_DATE;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_GRADIENT_ALPHA;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_GRADIENT_ID;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_ID;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_IMAGE_PATH;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.KEY_PURPOSE_TITLE;
import static com.wgfxer.projectpurpose.presentation.view.AppWidgetConfigActivity.SHARED_PREFS;

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            int id = sharedPreferences.getInt(KEY_PURPOSE_ID + appWidgetId, -1);
            final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);


            if (id != -1) {
                String title = sharedPreferences.getString(KEY_PURPOSE_TITLE + id, null);
                int gradientId = sharedPreferences.getInt(KEY_PURPOSE_GRADIENT_ID + id, -1);
                float gradientAlpha = sharedPreferences.getFloat(KEY_PURPOSE_GRADIENT_ALPHA + id, 1);
                String days = sharedPreferences.getString(KEY_PURPOSE_DATE + id, null);
                String imagePath = sharedPreferences.getString(KEY_PURPOSE_IMAGE_PATH + id, null);
                remoteViews.setOnClickPendingIntent(R.id.relative_layout, pendingIntent);
                remoteViews.setCharSequence(R.id.text_view_days, "setText", days);
                remoteViews.setImageViewResource(R.id.image_view_gradient, gradientId);
                if (imagePath != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    if (bitmap.getByteCount() > 6913000) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.2), (int) (bitmap.getHeight() * 0.2), false);
                    }
                    remoteViews.setImageViewBitmap(R.id.image_view_photo, bitmap);
                }
                remoteViews.setInt(R.id.image_view_gradient, "setAlpha", (int) (gradientAlpha * 200));
            }
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }


}
