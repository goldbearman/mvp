package com.sabirovfarit.android.rx;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(QueryPreferences.getLastTheme(this));
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_container, fragment);
            ft.commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    loadFragment(ProfileFragment.newInstance());
                    return true;
                case R.id.navigation_search:

                    loadFragment(SearchFragment.newInstance());
                    return true;
                case R.id.navigation_learn:

                    loadFragment(LearningFragment.newInstance());
                    return true;
                case R.id.navigation_dictation:
                    loadFragment(DictationFragment.newInstance());

                    return true;
            }
            return false;
        }
    };
}
