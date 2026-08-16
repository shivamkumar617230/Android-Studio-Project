package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Torch extends AppCompatActivity {
    ImageButton ib;
    Button b1;
    CameraManager cm;
    private boolean camera =false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_torch);
        ib=(ImageButton) findViewById(R.id.imageButton);
        b1=(Button) findViewById(R.id.button62);
        cm=(CameraManager) getSystemService(CAMERA_SERVICE);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!camera)
                {
                    try {
                        String[] ids = cm.getCameraIdList();
                        if (ids.length > 0) {
                            cm.setTorchMode(ids[0], true);
                            camera = true;
                            ib.setImageResource(R.drawable.on);
                        } else {
                            Toast.makeText(Torch.this, "No flashlight available on this device", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Torch.this, "Error toggling flashlight: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    try {
                        String[] ids = cm.getCameraIdList();
                        if (ids.length > 0) {
                            cm.setTorchMode(ids[0], false);
                            camera = false;
                            ib.setImageResource(R.drawable.off);
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Torch.this, "Error toggling flashlight: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String[] ids = cm.getCameraIdList();
                    if (ids.length > 0) {
                        cm.setTorchMode(ids[0], false);
                    }
                    camera = false;
                    ib.setImageResource(R.drawable.off);
                }
                catch (Exception ignored)
                {

                }
                Intent i=new Intent(Torch.this,MobileService.class);
                startActivity(i);
                finish();
            }
        });

    }
}