package com.example.salondeniz;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salondeniz.Model.Appointment;
import com.example.salondeniz.Model.Clock;
import com.example.salondeniz.Model.Person;
import com.example.salondeniz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentPast extends AppCompatActivity {
    ArrayList<Appointment> appointmentsList = new ArrayList<>();
    ArrayList<String> appointments = new ArrayList<>();
    ArrayList<Person> barberList = new ArrayList<>();
    ArrayList<String> barbers = new ArrayList<>();
    ArrayList<Clock> clockList = new ArrayList<>();
    ArrayList<String> clocks = new ArrayList<>();
    ArrayList<Person> getBarberList = new ArrayList<>();
    ArrayList<String> gidecek = new ArrayList<>();

    String barberID = "",appointmentID="",clockInformation="";
    String barberName = "";
    ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Randevu Geçmişi");

        appointments.clear();
        appointmentsList.clear();
        barberList.clear();
        barbers.clear();
        clockList.clear();
        clocks.clear();
        getBarberList.clear();


        barberID = "";
        appointmentID = "";
        clockInformation = "";
        barberName = "";

        listview=findViewById(R.id.listViewRand);
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    appointment.setAppointmentID(snapshot.getKey().toString());
                    if (appointment.getUserID().equalsIgnoreCase(User.uID)) {
                        appointmentsList.add(appointment);
                    }
                    barberGet();
                    reference.removeEventListener(this);
                }
            }

            private void barberGet() {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        barbers.clear();
                        barberList.clear();
                        getBarberList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Person person = snapshot.getValue(Person.class);
                            person.setUserID(snapshot.getKey().toString());
                            if (person.getRole().equalsIgnoreCase("Berber")) {
                                barbers.add(person.getNameSurname());
                                barberList.add(person);
                            }
                        }
                        barberSort();
                        reference.removeEventListener(this);
                    }

                    private void barberSort() {
                        getBarberList.clear();
                        if (barberList.size() != 0) {
                            for (Appointment item : appointmentsList) {
                                for (Person item2 : barberList) {
                                    if (item.getBarberID().equalsIgnoreCase(item2.getUserID())) {
                                        getBarberList.add(item2);

                                    }
                                }
                            }
                        }
                        dateSort();
                    }



                    private void dateSort() {
                        int sayac = 0;
                        gidecek.clear();
                        if (appointmentsList.size() != 0 ) {
                            for (Appointment item : appointmentsList) {
                                gidecek.add("Randevu Tarihi: " + item.getDate());
                                sayac++;
                            }
                        }
                        clockGet();

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
                                listReload();
                                reference.removeEventListener(this);
                            }

                            private void listReload() {
                                CustomAdapterAppointment customAdapterRandevu = new CustomAdapterAppointment(AppointmentPast.this, gidecek, appointmentsList);
                                listview.setAdapter(customAdapterRandevu);
                                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        appointmentID = "";
                                        clockInformation = "";
                                        barberName = "";
                                        appointmentID = appointmentsList.get(position).getAppointmentID();
                                        barberID = appointmentsList.get(position).getBarberID();


                                        for (Appointment item2 : appointmentsList) {
                                            if (appointmentID.equalsIgnoreCase(item2.getAppointmentID())) {

                                                for (Clock item3 : clockList) {
                                                    if (item3.getclockID().equalsIgnoreCase(item2.getClockID())) {
                                                        clockInformation = item3.getDate();
                                                        break;
                                                    }
                                                }
                                                for (Person kisi : barberList) {
                                                    if (item2.getBarberID().equalsIgnoreCase(kisi.getUserID())) {
                                                        barberID = kisi.getNameSurname() ;
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        Intent ıntent = new Intent(AppointmentPast.this, AppointmentPastDetails.class);
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

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
