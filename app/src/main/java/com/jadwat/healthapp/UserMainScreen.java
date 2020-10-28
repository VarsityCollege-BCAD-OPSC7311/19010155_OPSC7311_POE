package com.jadwat.healthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMainScreen extends AppCompatActivity {

    //---------------------------------code attribution---------------------------------
    //Author:   Technical Skillz
    //Link:     https://www.youtube.com/watch?v=JT8jKshHVXU
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        BottomNavigationView mainBottomNav = findViewById(R.id.mainBottomNav);
        mainBottomNav.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;

            if (item.getItemId() == R.id.home) {
                frag = new HomeFragment();
            }
            else if (item.getItemId() == R.id.camera) {
                frag = new CaptureMealFragment();
            }
            else if(item.getItemId() == R.id.progress) {
                frag = new WeightProgressFragment();
            }
            else if (item.getItemId() == R.id.settings) {
                frag = new SettingsFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

            return true;
        }
    };
    //--------------------------------------end-----------------------------------------
}