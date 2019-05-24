package com.hfad.workout;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopFragment extends Fragment implements View.OnClickListener {


    public StopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_stop, container, false);
        tvTime = layout.findViewById(R.id.tvTime);
        Button buttonStart = layout.findViewById(R.id.btnStart);
        buttonStart.setOnClickListener(this);
        Button buttonStop = layout.findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(this);
        Button buttonReset = layout.findViewById(R.id.btnReset);
        buttonReset.setOnClickListener(this);
        runTime();
        return layout;


    }

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    TextView tvTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////// Если Boundle не null //////////////
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
//        runTime();
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    private void onClickStart() {
        running = true;
    }

    private void onClickStop() {
        running = false;

    }

    private void onClickReset() {
        running = false;
        seconds = 0;
    }

    private void runTime() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                tvTime.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                onClickStart();
                break;
            case R.id.btnStop:
                onClickStop();
                break;
            case R.id.btnReset:
                onClickReset();
                break;
        }
    }
}
