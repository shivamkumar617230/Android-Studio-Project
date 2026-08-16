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

import androidx.appcompat.app.AppCompatActivity;

public class LightSensor extends AppCompatActivity implements SensorEventListener {

    private MediaPlayer mp;
    private Button b1;
    private SensorManager sm;
    private Sensor lightSensor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        b1 = findViewById(R.id.button74);

        mp = MediaPlayer.create(this, R.raw.o);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sm != null) {
            lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

            if (lightSensor != null) {
                sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        b1.setOnClickListener(v -> {

            Intent i = new Intent(LightSensor.this, MobileService.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float lightValue = event.values[0];

        if (lightValue > 1) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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