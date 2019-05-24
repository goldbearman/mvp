package com.hfad.workoutagain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WorkoutDetaleFragment fragment = (WorkoutDetaleFragment) getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        fragment.setWorkoutId(0);
    }
}
