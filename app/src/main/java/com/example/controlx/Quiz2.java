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

public class Quiz2 extends AppCompatActivity {
    RadioButton r1,r2,r3,r4;
    Button b2;
    TextView t1;
    static int score=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz2);
        r1=(RadioButton) findViewById(R.id.radioButton21);
        r2=(RadioButton) findViewById(R.id.radioButton22);
        r3=(RadioButton) findViewById(R.id.radioButton23);
        r4=(RadioButton) findViewById(R.id.radioButton24);
        t1=(TextView)findViewById(R.id.textView8);
        b2=(Button) findViewById(R.id.button45);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score =0;
                if (r4.isChecked())
                {
                    ++Quiz.score;
                }
                else
                {
                    --Quiz.score;
                }
                Intent i=new Intent(Quiz2.this,Quiz3.class);
                startActivity(i);
            }
        });
    }
}