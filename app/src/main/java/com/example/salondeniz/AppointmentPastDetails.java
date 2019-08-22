package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentPastDetails extends AppCompatActivity {
    Button btnRandevuSil;
    String appointmentID = "", barberName = "", clockInformation = "", date = "", operation = "";
    String randevudurumu = "";
    TextView twberberIsmi, twRandevuSaati, twRandevuTarih, twOperasyon;
    String userType ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_past_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Randevu Bilgileri");
        userType="";
        initiliaze();
        reload();
        operationGet();

        btnRandevuSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateControl();
            }
        });
    }

    private void dateControl() {
        randevudurumu = "";
        String[] Tarih_ayir = date.split("/");
        Date simdikiZaman = new Date();
        DateFormat df_gun = new SimpleDateFormat("dd");
        DateFormat df_ay = new SimpleDateFormat("MM");
        DateFormat df_yil = new SimpleDateFormat("yyyy");
        int gun = Integer.parseInt(df_gun.format(simdikiZaman));
        int ay = Integer.parseInt(df_ay.format(simdikiZaman));
        int yil = Integer.parseInt(df_yil.format(simdikiZaman));
        if (yil < Integer.parseInt(Tarih_ayir[2])) {
            randevudurumu = "";
            appointmentCancel();
        } else if (yil == Integer.parseInt(Tarih_ayir[2])) {
            randevudurumu = "";
            if (ay == Integer.parseInt(Tarih_ayir[1])) {
                if (gun == Integer.parseInt(Tarih_ayir[0])) {
                    String[] Saat_ayir = clockInformation.split(":");
                    DateFormat df_saat = new SimpleDateFormat("H");
                    DateFormat df_dakika = new SimpleDateFormat("m");
                    int saat = Integer.parseInt(df_saat.format(simdikiZaman));
                    int dakika = Integer.parseInt(df_dakika.format(simdikiZaman));
                    if (saat < Integer.parseInt(Saat_ayir[0])) {
                        randevudurumu = "";
                        appointmentCancel();
                    } else if (saat == Integer.parseInt(Saat_ayir[0])) {
                        if (dakika < Integer.parseInt(Saat_ayir[1])) {
                            randevudurumu = "";
                            appointmentCancel();
                        }else {
                            randevudurumu = "1";
                        }
                    } else {
                        randevudurumu = "1";
                    }
                } else if(gun < Integer.parseInt(Tarih_ayir[0])){
                    randevudurumu="";
                    appointmentCancel();
                }else {
                    randevudurumu = "1";
                }
            } else if (ay < Integer.parseInt(Tarih_ayir[1])) {
                randevudurumu = "";
                appointmentCancel();
            } else {
                randevudurumu = "1";
            }

        } else {
            randevudurumu = "1";
        }
        if (randevudurumu.equalsIgnoreCase("1")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Favori Ekleme");
            builder.setMessage("Randevu Tarihi Geçtiği İçin İptal Edilemez!\nDoktoru Favoriye Eklemek için Favori Ekle Butonuna Basınız");
            builder.setNegativeButton("Geri Gel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "İşlem İptal Edildi", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
    }

    private void appointmentCancel() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments").child(appointmentID);
        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Randevu İptal Edildi!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginMenuMain.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Randevu İptal Etme Başarısız!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void operationGet() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AppointmentsDetail");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Services.mArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentsDetail randevu2 = snapshot.getValue(AppointmentsDetail.class);
                    randevu2.setAppointmentDetailID(snapshot.getKey().toString());
                    if (randevu2.getAppointmentID().equalsIgnoreCase(appointmentID)) {
                        Services.mArrayList.add(randevu2.getOperation());
                    }
                }
                twberberIsmi.setText("Ad Soyad : " + barberName);
                twOperasyon.setText("Operasyon : " + operation);
                twRandevuSaati.setText("Randevu Saati :" + clockInformation);
                twRandevuTarih.setText("Randevu Tarihiniz : " + date);
                String operations = "işlemler : ";
                if (Services.mArrayList.size() != 0) {
                    for (String item : Services.mArrayList) {
                        operations += item + "\n";
                    }
                }
                twOperasyon.setText(operations);
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void reload() {
        Intent ıntent = getIntent();
        appointmentID = ıntent.getStringExtra("appointmentID");
        barberName = ıntent.getStringExtra("barberID");
        clockInformation = ıntent.getStringExtra("clockInformation");
        date = ıntent.getStringExtra("date");
        operation = ıntent.getStringExtra("operation");
        userType = ıntent.getStringExtra("userType");
        if(userType.equalsIgnoreCase("User")){
            btnRandevuSil.setVisibility(View.GONE);
        }

    }

    private void initiliaze() {
        twberberIsmi = findViewById(R.id.twBerberAdii);
        twRandevuSaati = findViewById(R.id.twRSaat);
        twRandevuTarih = findViewById(R.id.twRtarih);
        twOperasyon = findViewById(R.id.twOperasyon);
        btnRandevuSil = findViewById(R.id.btnRandevuSil);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ıntent;
        if(userType.equalsIgnoreCase("Barber")){
            ıntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(ıntent);
            finish();
        }
        else if(userType.equalsIgnoreCase("User")){
            ıntent = new Intent(getApplicationContext(),LoginMenuMain.class);
            startActivity(ıntent);
            finish();
        }
    }
}

