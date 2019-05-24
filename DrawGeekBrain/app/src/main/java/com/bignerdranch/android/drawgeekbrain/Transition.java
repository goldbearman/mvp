package com.bignerdranch.android.drawgeekbrain;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Transition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_layout);

        ImageView imageView = findViewById(R.id.transition_image);
        final TransitionDrawable transitionDrawableransitionDrawable = (TransitionDrawable) imageView
                .getDrawable();

        Button startTransition = findViewById(R.id.start_transition_button);
        startTransition.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionDrawableransitionDrawable.startTransition(1000);
            }
        });

        Button reverseTransition = findViewById(R.id.reverse_transition_button);
        reverseTransition.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionDrawableransitionDrawable.reverseTransition(1000);
            }
        });
    }


}
