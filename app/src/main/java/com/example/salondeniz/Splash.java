package com.example.salondeniz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread timerThread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    startActivity(new Intent(Splash.this, LoginActivity.class));
                    finish();
                }
            }
        };
        timerThread.start();




    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
