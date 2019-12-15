package com.wgfxer.projectpurpose.presentation.view.settings;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.presentation.view.MainActivity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Воркер для работы с уведомлениями
 */
public class NotificationWorker extends Worker {
    private static final int NOTIFICATION_ID = 13;
    private String NOTIFICATION_REPORT_CHANNEL = "Notification report channel";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * работа состоит в том,что он создает канал и показывает уведомление
     *
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {
        createChannel();
        showNotification();

        return Result.success();
    }

    /**
     * создание канала нотификаций для определенных апи
     */
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(NOTIFICATION_REPORT_CHANNEL, NOTIFICATION_REPORT_CHANNEL,
                            NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * создание и отображение уведомления
     */
    private void showNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);

        String notificationTitle = getApplicationContext().getResources().getString(R.string.notification_report_title);
        String notificationText = getApplicationContext().getResources().getString(R.string.notification_report_text);

        Notification notification =
                new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_REPORT_CHANNEL)
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
