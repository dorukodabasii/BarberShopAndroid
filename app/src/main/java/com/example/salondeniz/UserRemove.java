package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salondeniz.Model.Person;
import com.example.salondeniz.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRemove extends AppCompatActivity {


    Person personRemove = new Person();
    EditText edtRemoveUser;
    Button btnRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_remove_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Kullanıcı Sil");
        edtRemoveUser = findViewById(R.id.edtKisiSil);
        btnRemove = findViewById(R.id.btnKisiSil);


        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtRemoveUser.getText().toString() != "") {

                    personAdd();

                } else {
                    Toast.makeText(UserRemove.this, "Telefon Numarası Giriniz", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void userRemove() {

        if (edtRemoveUser.getText().toString() != "") {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(personRemove.getUserID());
            reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserRemove.this, "Başarılı", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserRemove.this, "Silme İşlemi Başarısız!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }


    private void personAdd() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Person person = snapshot.getValue(Person.class);
                    person.setUserID(snapshot.getKey());
                    if (person.getTelNo().equalsIgnoreCase(edtRemoveUser.getText().toString())){
                        personRemove.setUserID(person.getUserID());
                        break;
                    }
                }
                reference.removeEventListener(this);
                userRemove();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
