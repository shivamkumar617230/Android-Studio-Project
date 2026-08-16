package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz3 extends AppCompatActivity {
    RadioButton r1,r2,r3,r4;
    Button b1;
    TextView t1;
    static int score=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz3);
        r1=(RadioButton) findViewById(R.id.radioButton13);
        r2=(RadioButton) findViewById(R.id.radioButton14);
        r3=(RadioButton) findViewById(R.id.radioButton15);
        r4=(RadioButton) findViewById(R.id.radioButton16);
        t1=(TextView)findViewById(R.id.textView7);
        b1=(Button) findViewById(R.id.button44);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score =0;
                if (r2.isChecked())
                {
                    ++Quiz.score;
                }
                else
                {
                    --Quiz.score;
                }
                Intent i=new Intent(Quiz3.this,Quiz4.class);
                startActivity(i);
            }
        });
    }
}