package com.example.salondeniz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnKullaniciEkle, btnKullaniciSil, btnVolkanRandevu, btnCaglarRandevu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();


        btnKullaniciEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserAdd.class));

            }
        });

        btnKullaniciSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserRemove.class));

            }
        });


    }

    private void initialize() {
        btnKullaniciEkle = findViewById(R.id.btnKullaniciyiEkle);
        btnKullaniciSil = findViewById(R.id.btnKullaniciyiSil);
        btnVolkanRandevu = findViewById(R.id.btnVolkanRand);
        btnCaglarRandevu = findViewById(R.id.btnCaglarRand);
        btnVolkanRandevu = findViewById(R.id.btnVolkanRand);

    }
}
