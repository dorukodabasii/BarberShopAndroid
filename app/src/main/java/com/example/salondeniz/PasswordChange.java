package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;

public class PasswordChange extends AppCompatActivity {

    EditText edteskiSifre, edtyeniSifre, edtyeniSifre2;
    Button btndegis;
    String PasswordInfo = "", role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasword_changed_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Şifre Güncelleme");
        initialize();

        PasswordInfo = "";
        role = "";
        Intent ıntent = getIntent();
        role = ıntent.getStringExtra("role");


        btndegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordVerify() && newPasswordVerify() && passwordAcceptVerify()) {
                    if (edtyeniSifre.getText().toString().trim().equalsIgnoreCase(edtyeniSifre2.getText().toString().trim())) {
                        PasswordInfo = "";
                        passwordControl();
                    } else {
                        Toast.makeText(PasswordChange.this, "Şifreler Uyuşmuyor..!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }


    private void passwordControl() {
        PasswordInfo = "";
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Person person2 = snapshot.getValue(Person.class);
                    person2.setUserID(snapshot.getKey().toString());
                    if (person2.getUserID().equalsIgnoreCase(User.uID)) {
                        if (person2.getPassword().equalsIgnoreCase(edteskiSifre.getText().toString())) {
                            PasswordInfo = "1";
                            break;
                        } else {
                            PasswordInfo = "";
                            break;
                        }
                    }
                }
                passwordUpdate();
                reference.removeEventListener(this);
            }

            private void passwordUpdate() {

                if (PasswordInfo=="1"){
                DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users").child(User.uID);
                final HashMap<String,Object> hashMap= new HashMap<>();
                hashMap.put("password",edtyeniSifre.getText().toString());

                reference1.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        PasswordInfo="";
                        Toast.makeText(PasswordChange.this,"Şifreniz Başarıyla Değiştirilmiştir!",Toast.LENGTH_LONG).show();
                    }
                });


                }else{
                    Toast.makeText(PasswordChange.this,"Şifreniz Hatalı...",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void initialize() {
        edteskiSifre = findViewById(R.id.edtEskiSifre);
        edtyeniSifre = findViewById(R.id.edtYeniSifre);
        edtyeniSifre2 = findViewById(R.id.edtYeniSifre2);
        btndegis = findViewById(R.id.btnOnay);

    }
    private boolean passwordVerify() {
        String passwordInput = edteskiSifre.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            edteskiSifre.setError("Boş Geçilemez!");
            return false;
        }
         else {
            edteskiSifre.setError(null);
            return true;
        }
    }
    private  boolean newPasswordVerify(){

        String newPasswordInput = edtyeniSifre.getText().toString().trim();
        if (newPasswordInput.isEmpty()){
            edtyeniSifre.setError("Boş Geçilemez!");
            return false;
        }
        else{
            edtyeniSifre.setError(null);
            return true;
        }

    }

    private  boolean passwordAcceptVerify(){

        String passwordInput = edtyeniSifre2.getText().toString().trim();
        if (passwordInput.isEmpty()){
            edtyeniSifre2.setError("Şifre Boş geçilemez");
            return false;
        }else {
            edtyeniSifre2.setError(null);
            return true;
        }

        }
}
