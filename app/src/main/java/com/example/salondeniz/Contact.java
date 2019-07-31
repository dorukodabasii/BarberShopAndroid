package com.example.salondeniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Contact extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    ImageView imgphone,imgphone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication_main);

        imgphone=findViewById(R.id.imgPhoneCall);
        imgphone2=findViewById(R.id.imgPhoneCall2);

        imgphone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeVolkanPhoneCall();
            }
        });

        imgphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCaglarPhoneCall();
            }
        });

    }
    private void makeVolkanPhoneCall(){
        String number="05362532262";
        if (number.length()>0){
            if (ContextCompat.checkSelfPermission(Contact.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Contact.this,
                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else {
                String dial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }

    }

    private void makeCaglarPhoneCall(){
        String number="05372532262";
        if (number.length()>0){
            if (ContextCompat.checkSelfPermission(Contact.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Contact.this,
                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else {
                String dial = "tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makeCaglarPhoneCall();
            }else if (grantResults.length>0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                makeVolkanPhoneCall();
            }
            else{
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_LONG).show();
            }
        }
    }
}
