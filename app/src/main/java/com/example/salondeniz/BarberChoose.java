package com.example.salondeniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class BarberChoose extends AppCompatActivity {
    public static String randevu="";
    CheckBox chxVolkan, chxCaglar;
    Button dvmEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barber_choose_main);
        initialize();


        chxVolkan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chxVolkan.isChecked()) {
                    chxCaglar.setEnabled(false);
                    Toast.makeText(BarberChoose.this, "Volkan seçildi", Toast.LENGTH_LONG).show();
                    randevu = "Volkan";
                } else if (!chxVolkan.isChecked()) {
                    randevu="";
                    chxCaglar.setEnabled(true);
                }

            }
        });

        chxCaglar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chxCaglar.isChecked()) {
                    chxVolkan.setEnabled(false);
                    Toast.makeText(BarberChoose.this, "Çağlar seçildi", Toast.LENGTH_LONG).show();
                    randevu = "Çağlar";
                } else if (!chxCaglar.isChecked()) {
                    randevu="";
                    chxVolkan.setEnabled(true);

                }
            }
        });


        dvmEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (randevu==""){

                    Toast.makeText(BarberChoose.this,"Berber Seçmeden devam edemezsiniz",Toast.LENGTH_LONG).show();

                }else
                    {
                    Intent ıntent= new Intent(BarberChoose.this,Services.class);
                    startActivity(ıntent);
                    finish();
                }

            }
        });




    }

    private void initialize() {

        chxVolkan = findViewById(R.id.chkVolkan);
        chxCaglar = findViewById(R.id.chkCaglar);
        dvmEt=findViewById(R.id.btnDevam);

    }
}
