package com.hfad.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_WORCKOUT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //////////// ПОЛУЧАЕМ ССЫЛКУ НА ФРАКМЕНТ ///////////////
        WorkoutDetaleFragment frag = (WorkoutDetaleFragment) getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        ////// ПРИСВАЕВАЕМ ЕМУ ID O //////////
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_WORCKOUT_ID,2);
        frag.setWorkout(id);

    }
}
