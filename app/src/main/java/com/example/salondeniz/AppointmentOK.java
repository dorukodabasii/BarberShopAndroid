package com.example.salondeniz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.salondeniz.Model.Appointment;
import com.example.salondeniz.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class AppointmentOK extends AppCompatActivity {
    TextView twBarber, twDate, twClock, twOperation;
    Button btnRandOnay;
    String barberID = "", clockID = "", dateInformation = "", operation = "";
    String barberName = "", clockInformation = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_ok_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Randevu Bilgileri");
        initialize();

        final Intent ıntent = getIntent();
        operation = ıntent.getStringExtra("operation");
        barberID = ıntent.getStringExtra("barberID");
        clockID = ıntent.getStringExtra("clockID");
        dateInformation = ıntent.getStringExtra("date");
        barberName = ıntent.getStringExtra("barberName");
        clockInformation = ıntent.getStringExtra("clockInformation");
        atamaYap();

        btnRandOnay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appointmentGet();
                Toast.makeText(AppointmentOK.this,"Randevunuz Onaylanmıştır",Toast.LENGTH_SHORT).show();
                gotoLoginMain();

            }



            private void appointmentGet() {


                if (barberID != "" && User.uID != "" && clockID != "" && dateInformation != "" && operation != "") {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
                    final HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("barberID", barberID);
                    hashMap.put("userID", User.uID);
                    hashMap.put("clockID", clockID);
                    hashMap.put("date", dateInformation);


                    reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            appointmentsControl();

                        }
                    });
                }
            }
        });


    }

    private void gotoLoginMain() {
        startActivity(new Intent(AppointmentOK.this,LoginMenuMain.class));
        finish();
    }

    private void appointmentsControl() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment randevu2 = snapshot.getValue(Appointment.class);
                    randevu2.setAppointmentID(snapshot.getKey().toString());
                    if (randevu2.getClockID().equalsIgnoreCase(clockID) &&
                            randevu2.getBarberID().equalsIgnoreCase(barberID)
                            && randevu2.getUserID().equalsIgnoreCase(User.uID) &&
                            randevu2.getDate().equalsIgnoreCase(dateInformation)) {
                        appointmentsDetail(randevu2.getAppointmentID());
                        break;
                    }
                }
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void appointmentsDetail(String appointmentID) {
        if (Services.mArrayList.size() != 0) {
            for (String item : Services.mArrayList) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AppointmentsDetail");
                final HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("operation", item);
                hashMap.put("appointmentID", appointmentID);


                reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        }
        Services.mArrayList.clear();
    }


    private void initialize() {
        twBarber = findViewById(R.id.txtBarberName);
        twClock = findViewById(R.id.txtClock);
        twDate = findViewById(R.id.txtDate);
        twOperation = findViewById(R.id.txtOperation);
        btnRandOnay = findViewById(R.id.btnRandOnay);
    }

    private void atamaYap() {
        twBarber.setText("Berber Adı : " + barberName);
        twDate.setText("Tarih :" + dateInformation);
        twClock.setText("Saat :" + clockInformation);
        String mesaj = "Servis : ";
        if (Services.mArrayList.size() != 0) {
            for (String item : Services.mArrayList) {
                mesaj += item + "\n";
            }
        }
        twOperation.setText(mesaj);


    }



}
