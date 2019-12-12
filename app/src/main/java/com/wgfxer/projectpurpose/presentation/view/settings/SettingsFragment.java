package com.wgfxer.projectpurpose.presentation.view.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.wgfxer.projectpurpose.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


public class SettingsFragment extends Fragment {

    private static final String WORK_TAG = "NOTIFICATION_WORK";
    private static final String PREFERENCES_NAME = "PREFERENCES_NAME";
    private static final String KEY_NOTIFICATIONS_ENABLED = "KEY_NOTIFICATIONS_ENABLED";
    private static final String KEY_NOTIFICATION_HOURS = "KEY_NOTIFICATION_HOURS";
    private static final String KEY_NOTIFICATION_MINUTES = "KEY_NOTIFICATION_MINUTES";

    private static final long ONE_DAY_IN_MILLIS = 24*60*60*1000;

    private SharedPreferences preferences;

    private Switch notificationsSwitch;
    private TextView notificationsTimeTextView;

    private boolean isNotificationsEnabled = false;
    private int notificationHours = 21;
    private int notificationMinutes = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationsSwitch = view.findViewById(R.id.notifications_switch);
        notificationsTimeTextView = view.findViewById(R.id.time_notification_text_view);
        preferences = getContext().getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
        updateUiFromPreferences();
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    beginNewWork();
                    enableViewNotificationTime();
                }else{
                    cancelWork();
                    disableViewNotificationTime();
                }
                putIsNotificationEnabled(isChecked);
            }
        });
    }


    private void cancelWork(){
        WorkManager.getInstance(getContext()).cancelAllWorkByTag(WORK_TAG);
    }

    private void beginNewWork(){
        PeriodicWorkRequest notificationsWork = new PeriodicWorkRequest.Builder(NotificationWorker.class,
                ONE_DAY_IN_MILLIS+1,TimeUnit.MILLISECONDS,ONE_DAY_IN_MILLIS,TimeUnit.MILLISECONDS)
                .setInitialDelay(getMillisUntilNext(),TimeUnit.MILLISECONDS)
                .addTag(WORK_TAG)
                .build();
        WorkManager.getInstance(getContext()).enqueue(notificationsWork);
    }

    private long getMillisUntilNext() {
        long currentTimeInMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY,notificationHours);
        calendar.set(Calendar.MINUTE,notificationMinutes);
        if(calendar.getTimeInMillis() - currentTimeInMillis > 0){
            return calendar.getTimeInMillis() - currentTimeInMillis;
        }else{
            return calendar.getTimeInMillis() - currentTimeInMillis + ONE_DAY_IN_MILLIS;
        }
    }

    private void enableViewNotificationTime(){
        notificationsTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(notificationHours,notificationMinutes);
                timePickerFragment.setOnTimeSetListener(new TimePickerFragment.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(int hours, int minutes) {
                        notificationHours = hours;
                        notificationMinutes = minutes;
                        notificationsTimeTextView.setText(getString(R.string.time_notification_text,hours,minutes));
                        putTime(hours,minutes);
                        cancelWork();
                        beginNewWork();
                    }
                });
                timePickerFragment.show(getActivity().getSupportFragmentManager(),null);
            }
        });
        notificationsTimeTextView.setEnabled(true);
        notificationsTimeTextView.setTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
    }

    private void disableViewNotificationTime(){
        notificationsTimeTextView.setEnabled(false);
        notificationsTimeTextView.setTextColor(ContextCompat.getColor(getContext(),R.color.colorDisabled));
    }

    private void updateUiFromPreferences() {
        isNotificationsEnabled = preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED,false);
        notificationHours = preferences.getInt(KEY_NOTIFICATION_HOURS,21);
        notificationMinutes = preferences.getInt(KEY_NOTIFICATION_MINUTES,0);

        notificationsSwitch.setChecked(isNotificationsEnabled);
        notificationsTimeTextView.setText(getString(R.string.time_notification_text,notificationHours,notificationMinutes));
        if(isNotificationsEnabled){
            enableViewNotificationTime();
        }else{
            disableViewNotificationTime();
        }
    }

    private void putTime(int hours,int minutes){
        preferences.edit()
                .putInt(KEY_NOTIFICATION_HOURS,hours)
                .putInt(KEY_NOTIFICATION_MINUTES,minutes)
                .apply();
    }

    private void putIsNotificationEnabled(boolean isNotificationEnabled){
        preferences.edit()
                .putBoolean(KEY_NOTIFICATIONS_ENABLED,isNotificationEnabled)
                .apply();
    }

    public static SettingsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
