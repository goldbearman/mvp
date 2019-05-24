package com.bignerdranch.android.drawgeekbrain;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import javax.security.auth.login.LoginException;

public class Lolipop extends AppCompatActivity {

    private static final String TAG = "Lolipop";

    RelativeLayout mainLayoet;
    ImageView mImageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lolipop_layout);

        mainLayoet = findViewById(R.id.main_layout);
        mImageView = findViewById(R.id.img_1);

        mainLayoet.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "onTouch: " + mImageView.getVisibility());
                        if (mImageView.getVisibility() == View.INVISIBLE) {
                            Log.i(TAG, "onTouch: " + "here");
                            show(mImageView, (int) event.getX(), (int) event.getY());
                        } else {
                            hide(mImageView, (int) event.getX(), (int) event.getY());
                        }
                        break;
                    case MotionEvent.ACTION_UP:


                        break;
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void show(final View view1, int cx, int cy) {
        int maxRadius = view1.getHeight();
        Animator animator = ViewAnimationUtils.createCircularReveal(view1, cx, cy, 0, maxRadius);
        animator.setDuration(750);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationEnd(animation);
                view1.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void hide(final View view, int cx, int cy) {
        int maxRadius = view.getHeight();
        Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, maxRadius, 0);
        animator.setDuration(750);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

        animator.start();
    }
}
