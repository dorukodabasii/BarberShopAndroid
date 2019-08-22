package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.salondeniz.Model.Appointment;
import com.example.salondeniz.Model.Clock;
import com.example.salondeniz.Model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentPastBarber extends AppCompatActivity {
    ArrayList<Appointment> appointmentsList = new ArrayList<>();
    ArrayList<String> appointments = new ArrayList<>();
    ArrayList<Person> barberList = new ArrayList<>();
    ArrayList<String> barbers = new ArrayList<>();
    ArrayList<Person> userList = new ArrayList<>();
    ArrayList<Clock> clockList = new ArrayList<>();
    ArrayList<String> clocks = new ArrayList<>();
    ArrayList<Person> getBarberList = new ArrayList<>();
    ArrayList<Person> getUserList = new ArrayList<>();
    ArrayList<String> gidecek = new ArrayList<>();

    String barberID = "", appointmentID = "", clockInformation = "";
    String barberName = "";
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_past_barber);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Randevu Geçmişi");

        appointments.clear();
        appointmentsList.clear();
        barberList.clear();
        barbers.clear();
        clockList.clear();
        clocks.clear();
        getBarberList.clear();


        Intent ıntent = getIntent();
        barberID = ıntent.getStringExtra("barberID");
        appointmentID = "";
        clockInformation = "";
        barberName = "";

        listview = findViewById(R.id.listViewRandBarber);
        appointmentGet();
    }

    private void appointmentGet() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                appointmentsList.clear();
                barberList.clear();
                barbers.clear();
                clockList.clear();
                clocks.clear();
                getBarberList.clear();
                appointmentID = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    appointment.setAppointmentID(snapshot.getKey().toString());
                    if (appointment.getBarberID().equalsIgnoreCase(barberID)) {
                        appointmentsList.add(appointment);
                    }
                    clockGet();
                    reference.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clockGet() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Times");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clocks.clear();
                clockList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Clock saat2 = snapshot.getValue(Clock.class);
                    saat2.setClockID(snapshot.getKey().toString());

                    if (appointmentsList.size() != 0) {
                        for (Appointment item : appointmentsList) {
                            if (saat2.getclockID().equalsIgnoreCase(item.getClockID())) {
                                clockList.add(saat2);
                                clocks.add(saat2.getDate());
                            }
                        }
                    }
                }
                userSort();


                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void dateSort() {
        int sayac = 0;
        gidecek.clear();
        if (appointmentsList.size() != 0) {
            for (Appointment item : appointmentsList) {
                gidecek.add("Randevu Tarihi: " + item.getDate());
                sayac++;
            }
        }

        listReload();
    }

    private void userSort() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                getUserList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Person person = snapshot.getValue(Person.class);
                    person.setUserID(snapshot.getKey());
                    userList.add(person);

                }
                personControl();
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void personControl() {
        if(userList.size()!=0){
            for (Appointment item : appointmentsList){
                for (Person item2 : userList){
                    if(item.getUserID().equalsIgnoreCase(item2.getUserID())){
                        getUserList.add(item2);
                    }
                }
            }
        }
        dateSort();
    }

    private void listReload() {

        CustomAdapterAppointment customAdapterRandevu = new CustomAdapterAppointment(getApplicationContext(), gidecek, appointmentsList);
        listview.setAdapter(customAdapterRandevu);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appointmentID = "";
                clockInformation = "";
                barberName = "";
                appointmentID = appointmentsList.get(position).getAppointmentID();
                barberID = appointmentsList.get(position).getBarberID();
                barberName = getUserList.get(position).getNameSurname();


                for (Appointment item2 : appointmentsList) {
                    if (appointmentID.equalsIgnoreCase(item2.getAppointmentID())) {

                        for (Clock item3 : clockList) {
                            if (item3.getclockID().equalsIgnoreCase(item2.getClockID())) {
                                clockInformation = item3.getDate();
                                break;
                            }
                        }
                        for (Person kisi : userList) {

                            if (item2.getUserID().equalsIgnoreCase(kisi.getUserID())) {
                                barberID = kisi.getNameSurname();
                                barberName=kisi.getNameSurname();
                                break;
                            }
                        }
                    }
                }

                Intent ıntent = new Intent(getApplicationContext(), AppointmentPastDetails.class);
                ıntent.putExtra("appointmentID", appointmentID);
                ıntent.putExtra("barberName", barberName);
                ıntent.putExtra("barberID", barberID);
                ıntent.putExtra("clockInformation", clockInformation);
                ıntent.putExtra("date", appointmentsList.get(position).getDate());
                ıntent.putExtra("operation", Services.mArrayList);
                startActivity(ıntent);
                finish();
            }
        });
    }

}
