package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controlx.MobileService;
import com.example.controlx.R;

public class Wifi extends AppCompatActivity {

    Button b1, b2, b3;
    WifiManager wm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wifi);   // Change if your layout name is different

        b1 = findViewById(R.id.button68);
        b2 = findViewById(R.id.button69);
        b3 = findViewById(R.id.button40);

        wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        b1.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startActivity(new Intent(Settings.Panel.ACTION_WIFI));
            } else {
                wm.setWifiEnabled(true);
            }
        });

        b2.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startActivity(new Intent(Settings.Panel.ACTION_WIFI));
            } else {
                wm.setWifiEnabled(false);
            }
        });

        b3.setOnClickListener(v -> {
            startActivity(new Intent(Wifi.this, MobileService.class));
            finish();
        });
    }
}