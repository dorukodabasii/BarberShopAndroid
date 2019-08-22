package com.example.salondeniz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnKullaniciEkle, btnKullaniciSil, btnVolkanRandevu, btnCaglarRandevu,btnAdminCikis;

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
        btnAdminCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginActivity.login=false;
                startActivity(new Intent(MainActivity.this,LoginActivity.class));

            }
        });
        btnVolkanRandevu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(),AppointmentPastBarber.class);
                ıntent.putExtra("barberID","-Lmd_2LDQGGr05lxFnQe");
                startActivity(ıntent);
                finish();
            }
        });
        btnCaglarRandevu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(),AppointmentPastBarber.class);
                ıntent.putExtra("barberID","-Lmd_-e75bG1OL9MSupO");
                startActivity(ıntent);
                finish();
            }
        });


    }

    private void initialize() {
        btnKullaniciEkle = findViewById(R.id.btnKullaniciyiEkle);
        btnKullaniciSil = findViewById(R.id.btnKullaniciyiSil);
        btnVolkanRandevu = findViewById(R.id.btnVolkanRand);
        btnCaglarRandevu = findViewById(R.id.btnCaglarRand);
        btnVolkanRandevu = findViewById(R.id.btnVolkanRand);
        btnAdminCikis=findViewById(R.id.btnAdminCik);

    }
}
