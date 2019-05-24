package com.hfad.audioproject;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        media = MediaPlayer.create(this, R.raw.skriptonit);
    }
    public void play(View view) {
        switch (view.getId()) {
            case R.id.start:
                media.start();
                break;
            case R.id.stop:
                media.stop();
                media.release();
            case R.id.pause:
                media.pause();
                break;
        }
    }

    public void recordMy(View view) {
        short[] myAudio = new short[10000];
        AudioRecord audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC, 11025,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 10000);

        audioRecord.read(myAudio, 0, 10000);
    }
}
