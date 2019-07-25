package com.example.laptop_apik.parkir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG="LoginActivity";
    FirebaseAuth firebaseAuth;

    EditText emailText;
    EditText passwordText;
    Button masukButton;
    Button lupaButton;
    Activity context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()    !=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);

        emailText= (EditText) findViewById(R.id.input_email);
        passwordText= (EditText) findViewById(R.id.input_password);
        masukButton= (Button) findViewById(R.id.btn_masuk);
        lupaButton= (Button) findViewById(R.id.btn_lupa);

        masukButton.setOnClickListener(new View.OnClickListener() {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            @Override
            public void onClick(View v) {
                String email= emailText.getText().toString().trim();
                final String password= passwordText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter email address!", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Memasuki Akun...");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {


                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    Log.d("addOnCompleteListener", "onComplete() called with: task = [" + task + "] login");
                                    progressDialog.dismiss();
                                    if(!task.isSuccessful())
                                    {
                                        Log.d("Login", "onComplete() called with: task = [" + task + "] unsuccess");
                                        if(password.length()<6){
                                            passwordText.setError("Minimum Password");
                                            Toast.makeText(LoginActivity.this, "Password minimum 6 character", Toast.LENGTH_SHORT).show();

                                        }else{
                                            passwordText.setError("Auth Failed");
                                            Toast.makeText(LoginActivity.this, "authentication is failed", Toast.LENGTH_SHORT).show();

                                        }
                                    }else{
                                        Log.d("Login", "onComplete() called with: task = [" + task + "] success");
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
