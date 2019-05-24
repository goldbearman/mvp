package com.bignerdranch.android.drawgeekbrain;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AnimationActivity extends AppCompatActivity {

    ImageView mImageButton, mImageCoin;
    ScaleAnimation scale;
    TextView mTextView;
    Float mF = 0f;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_layout);

        mImageButton = findViewById(R.id.img_button);
        mImageCoin = findViewById(R.id.img_bit_coin);
        mTextView = findViewById(R.id.textView_m);

        mImageButton.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(AnimationActivity.this, R.string.hi, Toast.LENGTH_SHORT).show();






                    case MotionEvent.ACTION_UP:
                        break;

                }
                return false;
            }
        });
    }
}