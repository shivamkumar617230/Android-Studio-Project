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

public class Accelerometer extends AppCompatActivity implements SensorEventListener {

    private MediaPlayer mp;
    private ImageView iv;
    private SensorManager sm;
    private Sensor accelerometer;
    private Button b1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        iv = findViewById(R.id.imageView);
        b1 = findViewById(R.id.button75);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Accelerometer.this, MobileService.class);
                startActivity(i);
                finish();
            }
        });

        mp = MediaPlayer.create(this, R.raw.o);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sm != null) {
            accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (accelerometer != null) {
                sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Detect movement
        if (Math.abs(x) > 2 || Math.abs(y) > 2 || Math.abs(z) > 12) {

            if (!mp.isPlaying()) {
                mp.start();
            }

            iv.setImageResource(R.drawable.on);

        } else {

            if (mp.isPlaying()) {
                mp.pause();
                mp.seekTo(0);
            }

            iv.setImageResource(R.drawable.off);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sm != null) {
            sm.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sm != null && accelerometer != null) {
            sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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