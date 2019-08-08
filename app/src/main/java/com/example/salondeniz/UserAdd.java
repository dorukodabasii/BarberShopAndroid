package com.example.salondeniz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.salondeniz.Model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;


public class UserAdd extends AppCompatActivity {

    EditText edtname, edttel;
    Button btnuseradd;
    Person person = new Person();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_main);
        initialize();
        person.setTelNo("");

        btnuseradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telVerify() && nameVerify()) {
                    person.setNameSurname(edtname.getText().toString());
                    person.setTelNo(edttel.getText().toString());
                    person.setRole("Kullanıcı");
                    userControl();


                }

            }
        });

    }

    private void userControl() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (person.getTelNo() == "") {
                    person.setTelNo("0");
                }
                if (person.getTelNo() != "0") {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Person person2 = snapshot.getValue(Person.class);
                        if (person2.getTelNo().equals(person.getTelNo())) {
                            person.setTelNo("0");
                            break;
                        }
                    }
                    register();
                    reference.removeEventListener(this);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void register() {
        if (person.getTelNo() != "0" && person.getTelNo() != "") {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("telNo", person.getTelNo());
            hashMap.put("password", "123456");
            hashMap.put("nameSurname", person.getNameSurname());
            hashMap.put("role", "Kullanıcı");


            reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    person.setTelNo("0");
                    Toast.makeText(UserAdd.this, "Başarılı", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(UserAdd.this, "Bu Telefon Numarası ile Kayıtlı Kullanıcı Mevcut!", Toast.LENGTH_SHORT).show();
        }
    }


    private void initialize() {

        edtname = findViewById(R.id.edtname);
        edttel = findViewById(R.id.edtTel);
        btnuseradd = findViewById(R.id.btnKullaniciyiEkle);


    }

    private boolean telVerify() {
        String telInput = edttel.getText().toString().trim();
        if (telInput.isEmpty()) {
            edttel.setError("Boş Geçilemez!");
            return false;
        } else if (telInput.length() != 11) {
            edttel.setError("Telefon 11 Haneli Olmalıdır!");
            return false;
        } else {
            edttel.setError(null);
            return true;
        }
    }

    private boolean nameVerify() {
        String nameInput = edtname.getText().toString().trim();
        if (nameInput.isEmpty()) {
            edtname.setError("Boş geçilemez!");
            return false;
        } else {
            edtname.setError(null);
            return true;
        }
    }


}
