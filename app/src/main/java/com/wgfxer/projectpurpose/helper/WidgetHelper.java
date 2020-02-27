package com.wgfxer.projectpurpose.helper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.models.data.Purpose;
import com.wgfxer.projectpurpose.presentation.view.MainActivity;



public class WidgetHelper {
    private Context context;

    public WidgetHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public RemoteViews getRemoteViewsForPurpose(Purpose purpose, int width, int height){
        int layoutResId = R.layout.widget_layout_small;

        //логика выбора layouta по ширине и высоте
        if(width > 240){
            if(height > 100){
                layoutResId = R.layout.widget_layout_big;
            }else{
                layoutResId = R.layout.widget_layout_wide;
            }
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutResId);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.relative_layout, pendingIntent);
        remoteViews.setCharSequence(R.id.text_view_title,"setText",purpose.getTitle());
        remoteViews.setCharSequence(R.id.text_view_date,"setText",Utils.getStringFromDate(purpose.getDate()));
        int daysToPurpose = Utils.getDaysFromDate(purpose.getDate());
        if (daysToPurpose > 0) {
            remoteViews.setCharSequence(R.id.text_view_days, "setText",
                    String.valueOf(daysToPurpose));
            String daysLabel = (context.getResources().getQuantityString(R.plurals.days_count,daysToPurpose)).substring(5);
            remoteViews.setCharSequence(R.id.label_days, "setText",
                    daysLabel);
        } else {
            remoteViews.setCharSequence(R.id.text_view_days, "setText", context.getString(R.string.time_end_text));
            remoteViews.setViewVisibility(R.id.label_days, View.GONE);
        }
        remoteViews.setImageViewResource(R.id.image_view_gradient, purpose.getTheme().getGradientId());

        if (purpose.getTheme().getImagePath() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(purpose.getTheme().getImagePath());
            if (bitmap.getByteCount() > 6913000) {
                double coef = getCoef(bitmap,width,height);
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * coef), (int) (bitmap.getHeight() * coef), false);
            }
            remoteViews.setImageViewBitmap(R.id.image_view_photo, bitmap);
            remoteViews.setInt(R.id.image_view_gradient, "setAlpha", (int) (purpose.getTheme().getGradientAlpha() * 255));
        }else{
            remoteViews.setInt(R.id.image_view_gradient, "setAlpha", 255);
        }
        return remoteViews;
    }


    private double getCoef(Bitmap bitmap, int width, int height){
        return 0.3;
        //return Math.max(width/(float) bitmap.getWidth(), height/(float)bitmap.getHeight());
    }
}
