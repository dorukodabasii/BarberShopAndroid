package com.example.salondeniz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginMenuMain extends AppCompatActivity {
    Button btnRandevuAl, btnProfilim, btnRandevularim, btnIletisim, btnAdresimiz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_menu_main);

        initialize();


        btnRandevuAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginMenuMain.this, BarberChoose.class));
                finish();

            }
        });

        btnProfilim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginMenuMain.this,Contact.class));
                finish();
            }
        });
        btnProfilim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginMenuMain.this,MyProfile.class));
                finish();
            }
        });

        btnIletisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginMenuMain.this,Contact.class));
                finish();
            }
        });
    }

    private void initialize() {
        btnRandevuAl=findViewById(R.id.btnRandevuAl);
        btnProfilim=findViewById(R.id.btnProfil);
        btnRandevularim=findViewById(R.id.btnRandevularim);
        btnIletisim=findViewById(R.id.btninfo);
        btnAdresimiz=findViewById(R.id.btnAdres);

    }
}