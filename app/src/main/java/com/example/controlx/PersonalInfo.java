package com.example.controlx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfo extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1,b2;
    TextInputEditText te;
    FirebaseAuth fa;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_info);
        e1=(EditText)findViewById(R.id.editTextText9);
        e2=(EditText)findViewById(R.id.editTextText10);
        e3=(EditText)findViewById(R.id.editTextText11);
        b1=(Button) findViewById(R.id.button19);
        b2=(Button) findViewById(R.id.button21);
        firebaseDatabase=FirebaseDatabase.getInstance();
        fa=FirebaseAuth.getInstance();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PersonalInfo.this, Login.class);
                startActivity(i);
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference= firebaseDatabase.getReference("Users");
                String s1=e1.getText().toString().trim();
                String s2=e2.getText().toString();
                String s3=e3.getText().toString();

                if (s3.length()!=10)
                {
                    e3.setError("Fill phone Number");
                    return;
                }
                else
                {
                    Users Users= new Users(s1,s2,s3);
                    databaseReference.child(s3).setValue(Users);
                    Toast.makeText(PersonalInfo.this, "Info Saved", Toast.LENGTH_SHORT).show();
                }
                Intent i=new Intent(PersonalInfo.this,ExploreServices.class);
                startActivity(i);
                finish();
            }
        });
    }
}