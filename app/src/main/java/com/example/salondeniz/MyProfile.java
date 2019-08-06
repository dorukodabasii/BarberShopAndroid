package com.example.salondeniz;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class MyProfile extends AppCompatActivity {


    Button btnTelChanged,btnPasswordChanged;
    ImageView imgProfilePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_main);
        btnPasswordChanged=findViewById(R.id.btnSifreDegis);
        btnTelChanged=findViewById(R.id.btnTelDegis);
        imgProfilePhoto=findViewById(R.id.imgProfil);


        imgProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,1);
            }
        });

        btnPasswordChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MyProfile.this,PasswordChange.class));
                finish();
            }
        });
        btnTelChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MyProfile.this,TelephoneChange.class));
                finish();
            }
        });
















    }
}
