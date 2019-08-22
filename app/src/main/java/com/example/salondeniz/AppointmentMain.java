package com.example.salondeniz;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salondeniz.Model.Appointment;
import com.example.salondeniz.Model.Clock;
import com.example.salondeniz.Model.Constraint;
import com.example.salondeniz.Model.Person;
import com.example.salondeniz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentMain extends AppCompatActivity {

    List<Constraint> constraintsList=new ArrayList<>();
    List<Clock> clockList = new ArrayList<>();
    List<Appointment> specialAppointmentList = new ArrayList<>();
    List<Appointment> AppointmentList = new ArrayList<>();
    List<Appointment> AppointmentList2 = new ArrayList<>();
    List<Appointment> AppointmentList3 = new ArrayList<>();
    List<Person> BarberList = new ArrayList<>();
    List<Person> BarberList2 = new ArrayList<>();
    ArrayList<String> clock = new ArrayList<>();
    ArrayList<String> barbers = new ArrayList<>();
    Spinner spBarber,spAppointmentDate;
    String barberID="",clockID="";
    TextView twDate;
    Button btnAppointment;
    String barberName="",clockInformation="";
    int[] appointmentNumber;
    int[] list;
    DatePickerDialog.OnDateSetListener dpdRandevuAl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_add_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Randevu Al");
        initialize();

        spBarber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                barberID=BarberList.get(i).getUserID();
                barberName=BarberList.get(i).getNameSurname();
                clockID="";
                clockInformation="";
                clock.clear();
                clockList.clear();
                constraintsList.clear();
                appointmentGet();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spAppointmentDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clockID=clockList.get(i).getclockID();
                clockInformation=clockList.get(i).getDate();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        twDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AppointmentMain.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dpdRandevuAl, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        dpdRandevuAl=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                clockID="";
                barberID="";
                clockInformation="";
                barberName="";
                clock.clear();
                clockList.clear();
                BarberList.clear();

                 month = month + 1;
                String tarih = dayOfMonth + "/" + month + "/" + year;
                Date simdikiZaman = new Date();
                DateFormat df_gun = new SimpleDateFormat("dd");
                DateFormat df_ay = new SimpleDateFormat("MM");
                DateFormat df_yil = new SimpleDateFormat("yyyy");
                int gun = Integer.parseInt(df_gun.format(simdikiZaman));
                int ay = Integer.parseInt(df_ay.format(simdikiZaman));
                int yil = Integer.parseInt(df_yil.format(simdikiZaman));
                if (yil==year){
                    if (ay==month){
                        if (gun<=dayOfMonth){
                            twDate.setText(tarih);
                            barberID="";
                            barberName="";
                            specialAppointment();
                            barberGet();
                        } else {
                            Toast.makeText(AppointmentMain.this, "Geçerli Bir Tarih Giriniz!", Toast.LENGTH_SHORT).show();
                        }
                    }else if (ay < month) {
                        twDate.setText(tarih);
                        barberID = "";
                        barberName = "";
                        specialAppointment();
                        barberGet();
                    }else {
                        Toast.makeText(AppointmentMain.this, "Geçerli Bir Tarih Giriniz!", Toast.LENGTH_SHORT).show();
                    }
                } else if (yil < year) {
                    twDate.setText(tarih);
                    barberID = "";
                    barberName = "";
                    specialAppointment();
                    barberGet();
                } else {
                    Toast.makeText(AppointmentMain.this, "Geçerli Bir Tarih Giriniz!", Toast.LENGTH_SHORT).show();
                }


            }

            private void barberGet() {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        barbers.clear();
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Person person = snapshot.getValue(Person.class);
                            person.setUserID(snapshot.getKey());
                            if (person.getRole().equalsIgnoreCase("Berber")&& !(person.getUserID().equalsIgnoreCase(User.uID)) ){
                                BarberList.add(person);
                            }
                        }
                        specialRequest();
                        reference.removeEventListener(this);
                    }

                    private void specialRequest() {
                        appointmentNumber = new int[BarberList.size()];
                        int sayac = 0;
                        if (specialAppointmentList.size() != 0) {
                            for (Appointment item : specialAppointmentList) {
                                sayac = 0;
                                for (Person item2 : BarberList) {
                                    if (item.getBarberID().equalsIgnoreCase(item2.getUserID())) {
                                        appointmentNumber[sayac] = appointmentNumber[sayac] + 1;
                                        break;
                                    }
                                    sayac++;
                                }
                            }
                        }
                        specialRequest2();
                    }

                    private void specialRequest2() {
                        list = new int[BarberList.size()];

                        for(int i = 0; i<appointmentNumber.length; i++){
                            list[i]=appointmentNumber[i];
                        }

                        for (int i = 0; i < list.length-1; i++) { //Dizimizin değerlerini sırası ile alıyoruz

                            int sayi = list[i]; //sıradaki değeri sayi değişkenine atıyoruz
                            int temp = i; //sayi 'nin indeksini temp değerine atıyoruz

                            for (int j = i+1; j < list.length ; j++) { //dizimizde i' den sonraki elemanlara bakıyoruz
                                if(list[j]<sayi){ //sayi değişkeninden küçük sayı var mı
                                    sayi = list[j]; //varsa sayi değişkenimizide değiştiriyoruz
                                    temp = j; //indeks değerinide değiştiriyoruz
                                }
                            }

                            if(temp != i){ //temp değeri başlangıç değeri ile aynı değil ise , yani list[i]'nin değerinden küçük sayı varsa onları yer değiştiriyoruz
                                list[temp] = list[i];
                                list[i] = sayi;
                            }
                        }
                        spinnerReload();
                    }

                    private void spinnerReload() {
                        for(int i = 0; i<list.length; i++){
                            for (int j = 0; j<appointmentNumber.length; j++){
                                if(list[i]==appointmentNumber[j]){
                                    barbers.add(BarberList.get(j).getNameSurname());
                                    BarberList2.add(BarberList.get(j));
                                    appointmentNumber[j]=-1;
                                    break;
                                }
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AppointmentMain.this, android.R.layout.simple_list_item_1, barbers);
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                        spBarber.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barberID !=""&& clockID !="" && !(twDate.getText().equals("Tarih Seçmek İçin Dokunun"))){
                    createAppointment();
                }else{
                    Toast.makeText(AppointmentMain.this, "Bilgiler eksiksiz doldurulmalıdır!", Toast.LENGTH_SHORT).show();

                }

            }

            private void createAppointment() {
                if (User.uID!=""){
                    Intent ıntent = new Intent(AppointmentMain.this,AppointmentOK.class);
                    ıntent.putExtra("barberID",barberID);
                    ıntent.putExtra("barberName",barberName);
                    ıntent.putExtra("clockID",clockID);
                    ıntent.putExtra("clockInformation",clockInformation);
                    ıntent.putExtra("date",twDate.getText().toString());
                    ıntent.putExtra("userID",User.uID);
                    startActivity(ıntent);
                    finish();
                }else {
                    Toast.makeText(AppointmentMain.this, "İşlem Başarısız!", Toast.LENGTH_SHORT).show();
                }
            }
        });

                }

    private void appointmentGet() {
        if ( !(twDate.getText().equals("Tarih Seçmek İçin Dokunun")) && barberID != "") {
            btnAppointment.setEnabled(true);
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    AppointmentList.clear();
                    AppointmentList2.clear();
                    AppointmentList3.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Appointment randevu2 = snapshot.getValue(Appointment.class);
                        randevu2.setAppointmentID(snapshot.getKey().toString());
                        if (randevu2.getBarberID().equalsIgnoreCase(barberID) && randevu2.getDate().equalsIgnoreCase(twDate.getText().toString())) {
                            AppointmentList.add(randevu2);
                        }
                        if (randevu2.getDate().equalsIgnoreCase(twDate.getText().toString()) && randevu2.getUserID().equalsIgnoreCase(User.uID)) {
                            AppointmentList2.add(randevu2);
                        }
                        if (randevu2.getDate().equalsIgnoreCase(twDate.getText().toString()) && randevu2.getUserID().equalsIgnoreCase(User.uID) && randevu2.getBarberID().equalsIgnoreCase(barberID)) {
                            AppointmentList3.add(randevu2);
                        }
                    }
                    constraint();
                    reference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void constraint() {
        if (!(twDate.getText().equals("Tarih Seçmek İçin Dokunun")) && barberID != "") {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Constraint");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    constraintsList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Constraint kisit2 = snapshot.getValue(Constraint.class);
                        kisit2.setConstraintID(snapshot.getKey());
                        if (kisit2.getBarberID().equalsIgnoreCase(barberID)) {
                            constraintsList.add(kisit2);
                        }
                    }
                    clockGet();
                    reference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void clockGet() {
        if (!(twDate.getText().equals("Tarih Seçmek İçin Dokunun")) && barberID != "") {
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Times");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    clock.clear();
                    clockList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Clock saat2 = snapshot.getValue(Clock.class);
                        saat2.setClockID(snapshot.getKey());
                        clockList.add(saat2);
                        clock.add(saat2.getDate());
                        if (AppointmentList.size() != 0) {
                            for (Appointment item : AppointmentList) {
                                clockRemove(item.getClockID());
                            }
                        }
                        if (constraintsList.size() != 0) {
                            for (Constraint item2 : constraintsList) {
                                clockRemove(item2.getClockID());
                            }
                        }
                        if (AppointmentList2.size() != 0) {
                            for (Appointment item3 : AppointmentList2) {
                                clockRemove(item3.getClockID());
                            }
                        }
                        clockControl(saat2.getclockID(), saat2.getDate());

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AppointmentMain.this, android.R.layout.simple_list_item_1, clock);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spAppointmentDate.setAdapter(adapter);
                    reference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void clockControl(String clockID, String clockInformation) {
        String[] Tarih_ayir = twDate.getText().toString().split("/");
        Date simdikiZaman = new Date();
        DateFormat df_gun = new SimpleDateFormat("dd");
        DateFormat df_ay = new SimpleDateFormat("MM");
        DateFormat df_yil = new SimpleDateFormat("yyyy");
        int gun = Integer.parseInt(df_gun.format(simdikiZaman));
        int ay = Integer.parseInt(df_ay.format(simdikiZaman));
        int yil = Integer.parseInt(df_yil.format(simdikiZaman));
        if (yil == Integer.parseInt(Tarih_ayir[2])) {
            if (ay == Integer.parseInt(Tarih_ayir[1])) {
                if (gun == Integer.parseInt(Tarih_ayir[0])) {
                    String[] Saat_ayir = clockInformation.split(":");
                    DateFormat df_saat = new SimpleDateFormat("H");
                    DateFormat df_dakika = new SimpleDateFormat("m");
                    int saat = Integer.parseInt(df_saat.format(simdikiZaman));
                    int dakika = Integer.parseInt(df_dakika.format(simdikiZaman));

                    if (saat > Integer.parseInt(Saat_ayir[0])) {
                        clockRemove(clockID);
                    } else if (saat == Integer.parseInt(Saat_ayir[0])) {
                        if (dakika >= Integer.parseInt(Saat_ayir[1])) {
                            clockRemove(clockID);
                        }
                    }
                }
            }
        }
    }

    private void clockRemove(String clockID) {
        String clockInformation = "";
        for (Clock item : clockList) {
            if (item.getclockID().equalsIgnoreCase(clockID)) {
                clockInformation = item.getDate();
                clockList.remove(item);
                break;
            }
        }
        for (String item2 : clock) {
            if (item2.equalsIgnoreCase(clockInformation)) {
                clock.remove(item2);
                break;
            }
        }

    }

    private void specialAppointment(){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                specialAppointmentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    appointment.setAppointmentID(snapshot.getKey());
                    if (appointment.getDate().equalsIgnoreCase(twDate.getText().toString().trim())){
                        specialAppointmentList.add(appointment);
                    }
                }
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {
        spBarber=findViewById(R.id.spnBarber);
        spAppointmentDate=findViewById(R.id.spnRandSaat);
        twDate=findViewById(R.id.txtDate);
        btnAppointment=findViewById(R.id.btnRandAl);
    }
}
