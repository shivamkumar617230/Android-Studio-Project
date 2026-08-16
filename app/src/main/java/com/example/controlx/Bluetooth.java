package com.example.controlx;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Bluetooth extends AppCompatActivity {

    BluetoothAdapter ba;
    Button b1, b2,b3;

    ActivityResultLauncher<Intent> enableBtLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bluetooth);

        b1 = findViewById(R.id.button63);
        b2 = findViewById(R.id.button66);
        b3 = findViewById(R.id.button67);
        ba = BluetoothAdapter.getDefaultAdapter();

        if (ba == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the launcher for enabling Bluetooth
        enableBtLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (ba.isEnabled()) {
                        Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Bluetooth NOT enabled", Toast.LENGTH_SHORT).show();
                    }
                });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Bluetooth.this, MobileService.class);
                startActivity(i);
                finish();
            }
        });

        b1.setOnClickListener(v -> {
            if (checkPermission()) {
                if (!ba.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    enableBtLauncher.launch(enableBtIntent);
                }else {
                    Toast.makeText(this, "Bluetooth is already ON", Toast.LENGTH_SHORT).show();
                }
            } else {
                requestPermission();
            }
        });

        b2.setOnClickListener(v -> {
            if (checkPermission()) {
                if (ba.isEnabled()) {
                    // Open Bluetooth settings so user can turn it off manually
                    Intent intent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Bluetooth is already OFF", Toast.LENGTH_SHORT).show();
                }
            } else {
                requestPermission();
            }
        });



    }

    private boolean checkPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
        }
    }
}