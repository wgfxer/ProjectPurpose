package com.wgfxer.projectpurpose.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String PREFERENCES_NAME = "PREFERENCES_NAME";

    private static final String KEY_NOTIFICATIONS_ENABLED = "KEY_NOTIFICATIONS_ENABLED";
    private static final String KEY_NOTIFICATION_HOURS = "KEY_NOTIFICATION_HOURS";
    private static final String KEY_NOTIFICATION_MINUTES = "KEY_NOTIFICATION_MINUTES";
    private static final String KEY_IS_EXPIRED_PURPOSE_SHOW = "KEY_IS_EXPIRED_PURPOSE_SHOW";
    private static final String KEY_IS_DARK_THEME = "KEY_IS_DARK_THEME";

    private static final String KEY_PURPOSE_ID = "PURPOSE_ID";


    private SharedPreferences preferences;

    public PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public boolean getIsExpiredPurposeShow() {
        return preferences.getBoolean(KEY_IS_EXPIRED_PURPOSE_SHOW, false);
    }

    public void putIsExpiredPurposeShow(boolean isExpiredPurposesShow) {
        preferences.edit()
                .putBoolean(KEY_IS_EXPIRED_PURPOSE_SHOW,isExpiredPurposesShow)
                .apply();
    }

    public boolean isNotificationEnabled(){
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, false);
    }

    public int getNotificationHours(){
        return preferences.getInt(KEY_NOTIFICATION_HOURS, 21);
    }

    public int getNotificationMinutes(){
        return preferences.getInt(KEY_NOTIFICATION_MINUTES, 0);
    }

    public void putNotificationTime(int hours, int minutes) {
        preferences.edit()
                .putInt(KEY_NOTIFICATION_HOURS, hours)
                .putInt(KEY_NOTIFICATION_MINUTES, minutes)
                .apply();
    }

    public void putIsNotificationEnabled(boolean isNotificationEnabled) {
        preferences.edit()
                .putBoolean(KEY_NOTIFICATIONS_ENABLED, isNotificationEnabled)
                .apply();
    }

    public void putPurposeIdForWidget(int purposeId,int appWidgetId){
        preferences.edit().putInt(KEY_PURPOSE_ID + appWidgetId, purposeId).apply();
    }

    public int getPurposeIdForWidget(int appWidgetId) {
        return preferences.getInt(KEY_PURPOSE_ID + appWidgetId, -1);
    }

    public void putIsDarkTheme(boolean isDarkTheme) {
        preferences.edit().putBoolean(KEY_IS_DARK_THEME,isDarkTheme).apply();
    }

    public boolean getIsDarkTheme(){
        return preferences.getBoolean(KEY_IS_DARK_THEME,false);
    }
}
