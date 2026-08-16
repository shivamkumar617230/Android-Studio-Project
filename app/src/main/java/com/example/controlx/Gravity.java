package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Gravity extends AppCompatActivity implements SensorEventListener {

    private MediaPlayer mp;
    private ImageView iv;
    private SensorManager sm;
    private Sensor gravitySensor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);

        mp = MediaPlayer.create(this, R.raw.h);
        iv = findViewById(R.id.imageView3);
        Button b1 = findViewById(R.id.button76);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Gravity.this, MobileService.class);
                startActivity(i);
                finish();
            }
        });

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sm != null) {
            gravitySensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mp == null) return;

        float x = event.values[0];
        float y = event.values[1];

        // Detect tilt or gravity changes (using a threshold for stability)
        if (Math.abs(x) > 1.0 || Math.abs(y) > 1.0) {
            if (!mp.isPlaying()) {
                mp.start();
            }
            iv.setImageResource(R.drawable.on);
        } else {
            if (mp.isPlaying()) {
                mp.pause();
            }
            iv.setImageResource(R.drawable.off);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sm != null && gravitySensor != null) {
            sm.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sm != null) {
            sm.unregisterListener(this);
        }
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}
