package com.example.controlx;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OnlineLogin extends AppCompatActivity {
    EditText e1,e2;
    ProgressBar p1;
    Button b1,b2;
    TextView t1;
    FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_online_login);
        e1=(EditText)findViewById(R.id.editTextText5);
        e2=(EditText)findViewById(R.id.editTextText6);
        e2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        p1=(ProgressBar)findViewById(R.id.progressBar);
        b1=(Button)findViewById(R.id.button15);
        b2=(Button)findViewById(R.id.button16);
        t1=(TextView)findViewById(R.id.textView3);
        fa= FirebaseAuth.getInstance();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OnlineLogin.this, Login.class);
                startActivity(i);
                finish();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(OnlineLogin.this, OnlineRegister.class);
                startActivity(i);
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=e1.getText().toString().trim();
                String s2=e2.getText().toString();
                if (s1.isEmpty())
                {
                    e1.setError("Fill Email");
                    return;
                }
                else
                {
                    if (s2.isEmpty())
                    {
                        e2.setError("Fill Password");
                        return;
                    }
                    else
                    {
                        p1.setVisibility(VISIBLE);
                        fa.signInWithEmailAndPassword(s1,s2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    p1.setVisibility(INVISIBLE);
                                    Toast.makeText(OnlineLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(OnlineLogin.this,PersonalInfo.class);
                                    startActivity(i);
                                    finish();
                                }
                                else
                                {
                                    p1.setVisibility(INVISIBLE);
                                    String errorMsg = task.getException() != null ? task.getException().getMessage() : "Login Failed";
                                    Toast.makeText(OnlineLogin.this, errorMsg, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            }
        });
    }
}