package com.wgfxer.projectpurpose.presentation.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgfxer.projectpurpose.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
    }

    public static SettingsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if(getString(R.string.pref_key_dark_theme).equals(preference.getKey())){
            //сменить дизай
            return true;
        }else if(getString(R.string.pref_key_greeting).equals(preference.getKey())){
            //отключить приветствие
            return true;
        }else if(getString(R.string.pref_key_send_error).equals(preference.getKey())){
            //запуск интента с адресом отправки
            return true;
        }else if(getString(R.string.pref_key_notifications).equals(preference.getKey())){
            //отключить уведомления
            return true;
        }else if(getString(R.string.pref_key_time_notifications).equals(preference.getKey())){
            //сменить время уведомления
            return true;
        }
        return super.onPreferenceTreeClick(preference);
    }
}
