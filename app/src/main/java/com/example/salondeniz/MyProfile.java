package com.example.salondeniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyProfile extends AppCompatActivity {

    Button btnTelChanged,btnPasswordChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_main);
        btnPasswordChanged=findViewById(R.id.btnSifreDegis);
        btnTelChanged=findViewById(R.id.btnTelDegis);




        btnPasswordChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(MyProfile.this,PasswordChange.class);
                startActivity(ıntent);
                finish();
            }
        });
        btnTelChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent1= new Intent(MyProfile.this,TelephoneChange.class);
                startActivity(ıntent1);
                finish();
            }
        });









    }

}
