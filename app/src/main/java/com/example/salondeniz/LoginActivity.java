package com.example.salondeniz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salondeniz.Model.Person;
import com.example.salondeniz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    EditText edtTel, edtPass;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);



        User.uID = "0";
        initialize();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telVerifty() && passwordVerifty()) {

                    login();
                }
            }


        });


    }

    private void login() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Person person = snapshot.getValue(Person.class);


                    if ((person.getTelNo().equalsIgnoreCase(edtTel.getText().toString()) && (person.getPassword().equalsIgnoreCase(edtPass.getText().toString())))) {
                        User.uID = snapshot.getKey();
                        if (person.getRole().equalsIgnoreCase("Kullanıcı")) {
                            Intent intent = new Intent(LoginActivity.this, LoginHomePage.class);
                            startActivity(intent);
                            finish();
                            break;
                        } else {

                            Intent ıntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(ıntent);
                            finish();

                        }
                    }
                }
                if (User.uID == "" || User.uID == "0") {
                    edtTel.setText("");
                    Toast.makeText(LoginActivity.this, "Telefon Numarası veya Şifre Hatalı!", Toast.LENGTH_LONG).show();


                }
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {

        edtTel = findViewById(R.id.edtTelefonLog);
        edtPass = findViewById(R.id.edtPassLog);
        btnLogin = findViewById(R.id.btnLog);

    }

    private boolean telVerifty() {
        String telVerifty = edtTel.getText().toString().trim();
        if (telVerifty.isEmpty()) {
            edtTel.setError("Boş geçilemez..!");
            return false;

        } else if (telVerifty.length() != 11) {
            edtTel.setError("Telefon 11 Haneli Olmalıdır!");
            return false;
        } else {
            edtTel.setError(null);
            return true;
        }
    }

    private boolean passwordVerifty() {
        String passwordInput = edtPass.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            edtPass.setError("Boş Geçilemez..!");
            return false;
        } else {
            edtPass.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        //splashe atmam gerek
        // Intent ıntent= new Intent(LoginActivity.)
    }
}
