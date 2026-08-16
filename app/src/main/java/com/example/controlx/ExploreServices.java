package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ExploreServices extends AppCompatActivity {
    Button b1,b2;
    FirebaseAuth fa;
    FirebaseDatabase firebaseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore_services);
       b1=(Button) findViewById(R.id.button23);
       b2=(Button) findViewById(R.id.button20);
       firebaseDatabase=FirebaseDatabase.getInstance();
       fa=FirebaseAuth.getInstance();
       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fa.signOut();
               Toast.makeText(ExploreServices.this, "Logout Successful", Toast.LENGTH_SHORT).show();
               Intent i=new Intent(ExploreServices.this,Second.class);
               startActivity(i);
               finish();
           }
       });
       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(ExploreServices.this,Services.class);
               startActivity(i);
               finish();
           }
       });
    }
}