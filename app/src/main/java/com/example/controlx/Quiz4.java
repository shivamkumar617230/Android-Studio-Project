package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz4 extends AppCompatActivity {
    EditText e1;
    Button b1,b2;
    TextView t1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz4);
        e1=(EditText) findViewById(R.id.editTextText13);
        t1=(TextView) findViewById(R.id.textView9);
        b1=(Button) findViewById(R.id.button42);
        b2=(Button)findViewById(R.id.button46);
        t1.setText("Your Score is: "+ Quiz.score);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Quiz4.this,Quiz.class);
                startActivity(i);
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Quiz4.this, ApplicationService.class);
                startActivity(i);
                finish();
            }
        });
    }
}