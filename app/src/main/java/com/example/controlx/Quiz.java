package com.example.controlx;

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

import com.google.android.material.color.utilities.Score;

public class Quiz extends AppCompatActivity {
    RadioButton r1,r2,r3,r4;
    Button b1;
    TextView t1;
    static int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        r1=(RadioButton) findViewById(R.id.radioButton2);
        r2=(RadioButton) findViewById(R.id.radioButton3);
        r3=(RadioButton) findViewById(R.id.radioButton5);
        r4=(RadioButton) findViewById(R.id.radioButton6);
        t1=(TextView)findViewById(R.id.textView5);
        b1=(Button) findViewById(R.id.button43);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score =0;
                if (r1.isChecked())
                {
                    ++score;
                }
                else
                {
                    --score;
                }
                Intent i=new Intent(Quiz.this,Quiz2.class);
                startActivity(i);
            }
        });
    }
}