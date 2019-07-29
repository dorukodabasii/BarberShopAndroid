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

import com.example.salondeniz.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
EditText edtTel,edtPass;
Button btnLogin,btndrk;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_main);
    User.uID="0";
    initialize();
    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (telVerifty()&&passwordVerifty()){

                final DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("Users");
                User admin=new User("77777777777","", "Adminimiz","000000","Admin");
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("Telefon",admin.getTelNo());
                hashMap.put("Şifre",admin.getPassword());
                hashMap.put("İsim",admin.getNameSurname());
                hashMap.put("Rol",admin.getRole());
                hashMap.put("UserId",admin.getUserID());

                mRef.push().setValue(hashMap);
                login();
            }
        }



    });


}
private void login(){
    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                User user = snapshot.getValue(User.class);
                if ((user.getTelNo().equalsIgnoreCase(edtPass.getText().toString()) && (user.getPassword().equalsIgnoreCase(edtPass.getText().toString())))){
                    User.uID=snapshot.getKey();
                    if (user.getRole().equalsIgnoreCase("Kullanıcı")){
                        Intent intent = new Intent(LoginActivity.this,LoginHomePage.class);
                        startActivity(intent);
                        finish();
                        break;
                    }else if(user.getRole().equalsIgnoreCase("Berber")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Giriş Seçimi");
                        builder.setMessage("Hangi panelde oturum açmak istiyorsunuz ? ");
                        builder.setNegativeButton("Kullanıcı Olarak Devam Et", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent1=new Intent(LoginActivity.this,LoginHomePage.class);
                            startActivity(intent1);
                            finish();
                            }
                        });
                        builder.setPositiveButton("Berber Olarak Devam Et", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent ıntent= new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(ıntent);
                                finish();
                            }
                        });
                        builder.show();

                    }
                }
            }
            if (User.uID=="" || User.uID=="0"){
                edtTel.setText("");
                Toast.makeText(LoginActivity.this,"Telefon Numarası veya Şifre Hatalı!",Toast.LENGTH_LONG).show();


            }
            reference.removeEventListener(this);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

    private void initialize() {

    edtTel=findViewById(R.id.edtTelefonLog);
    edtPass=findViewById(R.id.edtPassLog);
    btnLogin=findViewById(R.id.btnLog);

}
private boolean telVerifty(){
    String telVerifty = edtTel.getText().toString().trim();
    if (telVerifty.isEmpty()){
        edtTel.setError("Boş geçilemez..!");
        return false;

    }else if(telVerifty.length() !=11){
        edtTel.setError("Telefon 11 Haneli Olmalıdır!");
        return false;
    }else{
        edtTel.setError(null);
        return true;
    }
}

private boolean passwordVerifty(){
    String passwordInput=edtPass.getText().toString().trim();
    if (passwordInput.isEmpty()){
        edtPass.setError("Boş Geçilemez..!");
        return false;
    }else {
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
