package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentPastDetails extends AppCompatActivity {
    String appointmentID = "", barberName = "", clockInformation = "", date = "", operation = "";
    TextView twberberIsmi, twRandevuSaati, twRandevuTarih, twOperasyon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_past_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Randevu Bilgileri");
        initiliaze();
        reload();
        operationGet();

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


    }

    private void initiliaze() {
        twberberIsmi = findViewById(R.id.twBerberAdii);
        twRandevuSaati = findViewById(R.id.twRSaat);
        twRandevuTarih = findViewById(R.id.twRtarih);
        twOperasyon = findViewById(R.id.twOperasyon);
    }
}
