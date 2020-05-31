package com.wgfxer.projectpurpose.presentation.view.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.RemoteViews;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.helper.Utils;
import com.wgfxer.projectpurpose.models.Purpose;
import com.wgfxer.projectpurpose.models.PurposeTheme;
import com.wgfxer.projectpurpose.presentation.view.MainActivity;

public class WidgetHelper {
    private Context context;

    public WidgetHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public RemoteViews getRemoteViewsForPurpose(Purpose purpose, int width, int height){
        int layoutResId = getLayoutResId(width,height);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layoutResId);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.relative_layout, pendingIntent);

        setupTextViews(purpose, remoteViews);

        setupTextViewsColor(purpose, remoteViews);

        setupImage(purpose, remoteViews);

        return remoteViews;
    }

    private void setupTextViews(Purpose purpose, RemoteViews remoteViews) {
        remoteViews.setCharSequence(R.id.text_view_title,"setText",purpose.getTitle());
        remoteViews.setCharSequence(R.id.text_view_date,"setText", Utils.getStringFromDate(purpose.getDate()));
        int daysToPurpose = Utils.getDaysFromDate(purpose.getDate());
        if (daysToPurpose > 0) {
            remoteViews.setCharSequence(R.id.text_view_days, "setText",
                    String.valueOf(daysToPurpose));
            String daysLabel = (context.getResources().getQuantityString(R.plurals.days_count,daysToPurpose)).substring(5);
            remoteViews.setCharSequence(R.id.label_days, "setText",
                    daysLabel);
            remoteViews.setViewVisibility(R.id.label_days, View.VISIBLE);
        } else {
            remoteViews.setCharSequence(R.id.text_view_days, "setText", context.getString(R.string.time_end_text_widget));
            remoteViews.setViewVisibility(R.id.label_days, View.GONE);
        }
    }

    private void setupTextViewsColor(Purpose purpose, RemoteViews remoteViews) {
        if(purpose.getTheme().isWhiteFont()){
            remoteViews.setTextColor(R.id.text_view_title, Color.WHITE);
            remoteViews.setTextColor(R.id.text_view_date,Color.WHITE);
            remoteViews.setTextColor(R.id.text_view_days,Color.WHITE);
            remoteViews.setTextColor(R.id.label_days,Color.WHITE);
        }else{
            remoteViews.setTextColor(R.id.text_view_title, Color.BLACK);
            remoteViews.setTextColor(R.id.text_view_date,Color.BLACK);
            remoteViews.setTextColor(R.id.text_view_days,Color.BLACK);
            remoteViews.setTextColor(R.id.label_days,Color.BLACK);
        }
    }

    private void setupImage(Purpose purpose, RemoteViews remoteViews) {
        remoteViews.setImageViewResource(R.id.image_view_gradient, PurposeTheme.GRADIENTS[purpose.getTheme().getGradientPosition()]);
        if (purpose.getTheme().getImagePath() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(purpose.getTheme().getImagePath());
            if (bitmap.getByteCount() > 6913000) {
                double coef = getCoef();
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * coef), (int) (bitmap.getHeight() * coef), false);
            }
            remoteViews.setImageViewBitmap(R.id.image_view_photo, bitmap);
            remoteViews.setInt(R.id.image_view_gradient, "setAlpha", (int) (purpose.getTheme().getGradientAlpha() * 255));
        }else{
            remoteViews.setInt(R.id.image_view_gradient, "setAlpha", 255);
        }
    }

    /**
     * Получения id ресурса с нужным layoutом в зависимости от ширины и высоты виджета
     */
    private int getLayoutResId(int width, int height){
        int layoutResId = R.layout.widget_layout_small;

        if(width > 240){
            if(height > 100){
                layoutResId = R.layout.widget_layout_big;
            }else{
                layoutResId = R.layout.widget_layout_wide;
            }
        }
        return layoutResId;
    }


    private double getCoef(){
        return 0.3;
        //TODO настроить коэффициент сжатия изображения
        //return Math.max(width/(float) bitmap.getWidth(), height/(float)bitmap.getHeight());
    }
}
