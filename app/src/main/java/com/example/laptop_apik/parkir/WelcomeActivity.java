package com.example.laptop_apik.parkir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_SIGNIN = 0;
    Button masukButton;
    Button daftarButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        masukButton = (Button) findViewById(R.id.btn_masuk);
        daftarButton = (Button) findViewById(R.id.btn_daftar);

        masukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_SIGNIN);
            }
        });

        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), DaftarActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
