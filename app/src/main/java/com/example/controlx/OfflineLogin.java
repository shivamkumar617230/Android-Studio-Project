package com.example.controlx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.jar.Attributes;

public class OfflineLogin extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    TextView t1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offline_login);
       e1=(EditText)findViewById(R.id.editTextText);
       e2=(EditText)findViewById(R.id.editTextText2);
       b1=(Button)findViewById(R.id.button11);
       b2=(Button)findViewById(R.id.button12);
       t1=(TextView)findViewById(R.id.textView);
       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String s1=e1.getText().toString().trim();
               String s2=e2.getText().toString();
               if (s1.equals("")||s2.equals(""))
               {
                   Toast.makeText(OfflineLogin.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   SQLiteDatabase Database= openOrCreateDatabase("OfflineDatabase",MODE_PRIVATE,null);
                   Database.execSQL("create table if not exists User(email varchar,password varchar)");
                   String s3="Select* from User where (email='"+s1+"' and password='"+s2+"')";
                   Cursor c1= Database.rawQuery(s3,null);
                   if (c1.getCount()>0)
                   {
                       Toast.makeText(OfflineLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                       Intent i=new Intent(OfflineLogin.this,PersonalInfo.class);
                       startActivity(i);
                       finish();
                   }
                   else
                   {
                       Toast.makeText(OfflineLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                   }
               }
           }
       });
       t1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i= new Intent(OfflineLogin.this, OfflineRegister.class);
               startActivity(i);
               finish();
           }
       });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(OfflineLogin.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}