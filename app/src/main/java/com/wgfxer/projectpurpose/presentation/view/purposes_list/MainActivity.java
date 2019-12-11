package com.wgfxer.projectpurpose.presentation.view.purposes_list;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wgfxer.projectpurpose.R;
import com.wgfxer.projectpurpose.presentation.view.SettingsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            showFragment(PurposesListFragment.newInstance(PurposesListFragment.MODE_FUTURE_PURPOSES));
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.purposes_menu_item:
                        showFragment(PurposesListFragment.newInstance(PurposesListFragment.MODE_FUTURE_PURPOSES));
                        return true;
                    case R.id.done_purposes_menu_item:
                        showFragment(PurposesListFragment.newInstance(PurposesListFragment.MODE_DONE_PURPOSES));
                        return true;
                    case R.id.settings_menu_item:
                        showFragment(SettingsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

            }
        });
    }

    private void showFragment(Fragment fragment){
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.fragment_container, fragment).commit();
    }
}
