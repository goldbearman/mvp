package com.bignerdranch.android.drawgeekbrain;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ViewGroup container;
    Scene current;
    Scene other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scene_layout);
        setTitle("Немая сцена");

        container = (ViewGroup) findViewById(R.id.container);

        current = Scene.getSceneForLayout(container, R.layout.scene1, this);
        other = Scene.getSceneForLayout(container, R.layout.scene2, this);

        current.enter();

    }

    public void onClick(View v) {
        changeScenes();
    }

    private void changeScenes() {
        Scene tmp = other;
        other = current;
        current = tmp;

        TransitionManager.go(current);
    }
}
