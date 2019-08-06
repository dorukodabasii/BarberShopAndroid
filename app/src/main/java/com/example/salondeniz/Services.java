package com.example.salondeniz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Services extends AppCompatActivity {

   static ArrayList<String> mArrayList = new ArrayList<>();
   static ArrayList<String> durations = new ArrayList<>();
    Button btndevam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_main);
        initialize();
        operations();

    }

    private void operations() {


        btndevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mArrayList.isEmpty()) {

                    Toast.makeText(Services.this,"Lütfen işlem Seçiniz",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(Services.this, mArrayList.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(Services.this, durations.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });




    }

    private void initialize() {


        btndevam=findViewById(R.id.btnDevamServices);

        ArrayList<Integer> integers = new ArrayList<>();

        integers.add(R.id.chxNisanbkm);
        integers.add(R.id.chxDamatbkm);
        integers.add(R.id.chxSacTras);
        integers.add(R.id.chxSakalTras);
        integers.add(R.id.chxYıkamaFon);
        integers.add(R.id.chxCocukTras);
        integers.add(R.id.chxDefrize);
        integers.add(R.id.chxKeratin);
        integers.add(R.id.chxBuhar);
        integers.add(R.id.chxKasbkm);
        integers.add(R.id.chxKulakbkm);
        integers.add(R.id.chxRoflegnl);
        integers.add(R.id.chxRofleprc);
        integers.add(R.id.chxSacBoyama);
        integers.add(R.id.chxSakalBoyama);
        integers.add(R.id.chxSirblg);
        integers.add(R.id.chxSirgnl);
        integers.add(R.id.chxLokalMsj);
        integers.add(R.id.chxBuharSac);
        integers.add(R.id.chxCiltbkm);
        integers.add(R.id.chxDus);
        integers.add(R.id.chxUzunCiltbkm);

        for(Integer ınteger :integers)
        {
            final CheckBox checkBox = this.findViewById(ınteger);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String operation=checkBox.getText().toString();
                    String duration=checkBox.getTag().toString();
                    if (checkBox.isChecked()) {
                        Toast.makeText(Services.this, operation, Toast.LENGTH_SHORT).show();
                        mArrayList.add(operation);
                        durations.add(duration);
                    }else if (!checkBox.isChecked()){
                        mArrayList.remove(operation);
                        durations.remove(duration);
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BarberChoose.randevu="";
        mArrayList.clear();
        durations.clear();
        startActivity(new Intent(Services.this,BarberChoose.class));
    }
}
