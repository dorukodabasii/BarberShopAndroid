package com.example.salondeniz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salondeniz.Model.User;

public class LoginHomePage extends AppCompatActivity {

    Button btnKullaniciEkle,btnKullaniciSil,btnVolkanRandevu,btnCaglarRandevu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        btnKullaniciEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginHomePage.this, UserAdd.class));
                finish();
            }
        });


    }

    private void initialize() {
        btnKullaniciEkle=findViewById(R.id.btnKullaniciyiEkle);
        btnKullaniciSil=findViewById(R.id.btnKullaniciSil);
        btnVolkanRandevu=findViewById(R.id.btnVolkanRand);
        btnCaglarRandevu=findViewById(R.id.btnCaglarRand);

    }
}
