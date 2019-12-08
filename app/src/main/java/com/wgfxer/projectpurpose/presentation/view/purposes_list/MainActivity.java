package com.wgfxer.projectpurpose.presentation.view.purposes_list;

import android.os.Bundle;

import com.wgfxer.projectpurpose.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment purposesListFragment = new PurposesListFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, purposesListFragment).commit();
        }
    }
}
