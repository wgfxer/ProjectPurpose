package com.wgfxer.projectpurpose.presentation.view;

import android.app.Application;

import com.wgfxer.projectpurpose.helper.PreferencesHelper;

import androidx.appcompat.app.AppCompatDelegate;

public class ProjectPurposeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesHelper preferencesHelper = new PreferencesHelper(this);
        if(preferencesHelper.getIsDarkTheme()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
