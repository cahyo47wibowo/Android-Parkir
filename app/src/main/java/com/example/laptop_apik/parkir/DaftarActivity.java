package com.example.laptop_apik.parkir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;

public class DaftarActivity extends AppCompatActivity {

    private static final String TAG="DaftarActivity";
    private FirebaseAuth firebaseAuth;
    EditText nameText;
    EditText emailText;
    EditText passwordText;
    EditText repasswordText;
    Button daftarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Daftar");
        setContentView(R.layout.activity_daftar);
        firebaseAuth=FirebaseAuth.getInstance();
        nameText=(EditText)findViewById(R.id.input_nama);
        emailText=(EditText)findViewById(R.id.input_email);
        passwordText=(EditText)findViewById(R.id.input_password);
        repasswordText=(EditText)findViewById(R.id.input_repassword);
        daftarButton=(Button) findViewById(R.id.btn_daftar);
        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "signup");

        if(!validate()){
            onSignupFailed();
        }

        daftarButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(DaftarActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Membuat Akun...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        final String name = nameText.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(DaftarActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        if (!task.isSuccessful()) {
                            onSignupFailed();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(DaftarActivity.this, "Pengguna dengan email ini sudah ada ", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(DaftarActivity.this, "Gagal membuat akun, cek konetivitas." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            UserProfileChangeRequest user = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(user);
                            onSignupSuccess();
                        }
                    }
                });
    }

    public void onSignupSuccess() {
        Toast.makeText(DaftarActivity.this, "Akun berhasil dibuat, silahkan login", Toast.LENGTH_SHORT).show();
        daftarButton.setEnabled(true);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void  onSignupFailed(){
        daftarButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String repassword = repasswordText.getText().toString().trim();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (repassword.isEmpty() || !repassword.equals(password)) {
            repasswordText.setError("password not matched");
            valid = false;
        } else {
            repasswordText.setError(null);
        }

        return valid;
    }

}
