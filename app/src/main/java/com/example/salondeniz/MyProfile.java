package com.example.salondeniz;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salondeniz.Model.Person;
import com.example.salondeniz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MyProfile extends AppCompatActivity {


    Button btnTelChanged, btnPasswordChanged;
    ImageView imgProfilePhoto;
    TextView txUserName, txPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_main);
        initialize();
        update();


        btnPasswordChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MyProfile.this, PasswordChange.class));
                finish();
            }
        });
        btnTelChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MyProfile.this, TelephoneChange.class));
                finish();
            }
        });

    }

    private void update() {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Person person = snapshot.getValue(Person.class);
                    person.setUserID(snapshot.getKey());

                    if (person.getUserID().equalsIgnoreCase(User.uID)) {
                        txUserName.setText(person.getNameSurname());
                        txPhone.setText(person.getTelNo());
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

        btnPasswordChanged = findViewById(R.id.btnSifreDegis);
        btnTelChanged = findViewById(R.id.btnTelDegis);
        imgProfilePhoto = findViewById(R.id.imageView11);
        txUserName = findViewById(R.id.txtKullaniciAd);
        txPhone = findViewById(R.id.txtKullaniciTel);


    }
}
