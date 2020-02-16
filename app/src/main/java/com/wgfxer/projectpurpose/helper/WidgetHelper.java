package com.wgfxer.projectpurpose.helper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.MainActivity;

public class WidgetHelper {
    private Context context;

    public WidgetHelper(Context context) {
        this.context = context;
    }

    public RemoteViews getRemoteViewsForPurpose(Purpose purpose){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.relative_layout, pendingIntent);
        int daysToPurpose = Utils.getDaysFromDate(purpose.getDate());
        if (daysToPurpose > 0) {
            remoteViews.setCharSequence(R.id.text_view_days, "setText",
                    context.getResources().getQuantityString(R.plurals.days_count,
                            daysToPurpose, daysToPurpose));
        } else {
            remoteViews.setCharSequence(R.id.text_view_days, "setText", context.getString(R.string.time_end_text));
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
        return remoteViews;
    }
}
