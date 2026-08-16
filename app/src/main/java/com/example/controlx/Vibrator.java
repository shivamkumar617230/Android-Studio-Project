package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Vibrator extends AppCompatActivity {

    Button b1, b2;
    android.os.Vibrator vibrator;

    @SuppressLint({"MissingInflatedId", "ServiceCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vibrator);

        b1 = findViewById(R.id.button61);
        b2 = findViewById(R.id.button60);

        vibrator = (android.os.Vibrator) getSystemService(VIBRATOR_SERVICE);

        b1.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                        VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE)
                );
            } else {
                vibrator.vibrate(5000);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Vibrator.this, MobileService.class);
                startActivity(i);
                finish();

            }
        });
    }
}