package com.example.laptop_apik.parkir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(getApplicationContext());

        Thread background = new Thread() {
            public void run() {
                Log.i("SplashScreen","1");
                try {
                    Log.i("SplashScreen","2");
                    // Thread will sleep for 3 seconds
                    sleep(3*1000);

                    if (sessionManager.checkLogin()){
                        Log.i("SplashScreen","Not Logged in");
                        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.i("SplashScreen","Logged in");
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.i("SplashScreen","3");

                } catch (Exception e) {
                    Log.i("SplashScreen","error" + e);
                }
            }
        };
        // start thread
        background.start();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
