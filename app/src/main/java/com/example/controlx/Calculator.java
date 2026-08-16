package com.example.controlx;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Calculator extends AppCompatActivity {
    EditText e1,e2;
    TextView t1;
    Button b1,b2,b3,b4,b5,b6;
    TextToSpeech ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        e1 = (EditText) findViewById(R.id.editTextText14);
        e2 = (EditText) findViewById(R.id.editTextText15);
        t1 = (TextView) findViewById(R.id.textView6);
        b1 = (Button) findViewById(R.id.button47);
        b2 = (Button) findViewById(R.id.button48);
        b3 = (Button) findViewById(R.id.button49);
        b4 = (Button) findViewById(R.id.button54);
        b5 = (Button) findViewById(R.id.button57);
        b6 = (Button) findViewById(R.id.button56);
        ts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ts.setLanguage(Locale.ENGLISH);
                ts.setSpeechRate(0.9f);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                float f1=Float.parseFloat(s1);
                float f2=Float.parseFloat(s2);
                float f3= f1+f2;
                String s3= Float.toString(f3);
                t1.setText(s3);
                ts.speak(s3,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                float f1=Float.parseFloat(s1);
                float f2=Float.parseFloat(s2);
                float f3= f1-f2;
                String s3= Float.toString(f3);
                t1.setText(s3);
                ts.speak(s3,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                float f1=Float.parseFloat(s1);
                float f2=Float.parseFloat(s2);
                float f3= f1*f2;
                String s3= Float.toString(f3);
                t1.setText(s3);
                ts.speak(s3,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                float f1=Float.parseFloat(s1);
                float f2=Float.parseFloat(s2);
                float f3= f1/f2;
                String s3= Float.toString(f3);
                t1.setText(s3);
                ts.speak(s3,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ts.speak("Back",TextToSpeech.QUEUE_FLUSH,null);
                Intent i=new Intent(Calculator.this, ApplicationService.class);
                startActivity(i);
                finish();

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
                e2.setText("");
                t1.setText("");
                ts.speak("Clear", TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }
}