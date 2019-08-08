package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class TelephoneChange extends AppCompatActivity {

    EditText edtTelephone, edtnewTelephone, edtnewTelephone2;
    Button btnAccept;
    String telephoneInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tel_changed_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Telefon Güncelleme");
        initialize();

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telephoneVerify() && newTelephoneVerify() && telephoneAcceptVerify()) {
                    if (edtnewTelephone.getText().toString().trim().equalsIgnoreCase(edtnewTelephone2.getText().toString().trim())) {
                        telephoneInfo = "";
                        telephoneControl();
                    } else {
                        Toast.makeText(TelephoneChange.this, "Telefonlar Uyuşmuyor..!", Toast.LENGTH_LONG).show();
                    }
                }
            }


        });
    }

    private final void telephoneControl() {
        telephoneInfo = "";
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Person person2 = snapshot.getValue(Person.class);
                    person2.setUserID(snapshot.getKey().toString());
                    if (person2.getUserID().equalsIgnoreCase(User.uID)) {
                        if (person2.getTelNo().equalsIgnoreCase(edtTelephone.getText().toString())) {
                            telephoneInfo = "1";
                            break;
                        } else {
                            telephoneInfo = "";
                            break;
                        }
                    }
                }
                telephoneUpdate();
                reference.removeEventListener(this);
            }

            private void telephoneUpdate() {

                if (telephoneInfo == "1") {
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(User.uID);
                    final HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("telNo", edtnewTelephone.getText().toString());

                    reference1.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            telephoneInfo = "";
                            Toast.makeText(TelephoneChange.this, "Telefonunuz Başarıyla Değiştirilmiştir!", Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    Toast.makeText(TelephoneChange.this, "Telefon numaranız Hatalı...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initialize() {

        edtTelephone = findViewById(R.id.edtEskiTel);
        edtnewTelephone = findViewById(R.id.edtYeniTel1);
        edtnewTelephone2 = findViewById(R.id.edtYeniTel2);
        btnAccept = findViewById(R.id.btnOnayliyorum);
    }

    private boolean telephoneVerify() {
        String telephoneInput = edtTelephone.getText().toString().trim();
        if (telephoneInput.isEmpty()) {
            edtTelephone.setError("Boş Geçilemez!");
            return false;
        } else {
            edtTelephone.setError(null);
            return true;
        }
    }

    private boolean newTelephoneVerify() {
        String newtelephoneInput = edtnewTelephone.getText().toString().trim();
        if (newtelephoneInput.isEmpty()) {
            edtnewTelephone.setError("Boş Geçilemez!");
            return false;
        } else {
            edtnewTelephone.setError(null);
            return true;
        }
    }

    private boolean telephoneAcceptVerify() {
        String telInput = edtnewTelephone2.getText().toString().trim();
        if (telInput.isEmpty()) {
            edtnewTelephone2.setError("Şifre Boş geçilemez");
            return false;
        } else {
            edtnewTelephone2.setError(null);
            return true;
        }
    }
}






