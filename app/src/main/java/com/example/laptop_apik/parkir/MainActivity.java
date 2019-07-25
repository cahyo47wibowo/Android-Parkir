package com.example.laptop_apik.parkir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView MonitoringButton;
    TextView UserTextView;

    FirebaseDatabase mFirebaseDatabase;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

    DatabaseReference databaseReference;

    public static final String Database_Path="location";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(MainActivity.this);
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        MonitoringButton = (ImageView) findViewById(R.id.MonitoringButton);
        UserTextView= (TextView)findViewById(R.id.user);

        databaseReference=mFirebaseDatabase.getReference(Database_Path);
        //databaseReference= FirebaseDatabase.getInstance().getReference(Database_Path);
        Log.d(TAG, "onCreate: "+databaseReference.toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot);
                Log.d(TAG, "onDataChange: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: "+databaseError);
            }
        });

        loadUserInformation();
        mFirebaseUser.getDisplayName();
        mFirebaseUser.getUid();
        mFirebaseUser.getEmail();
        Log.d(TAG, "onCancelled: "+mFirebaseUser);

        MonitoringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowSpotDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void loadUserInformation(){
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        String User = user.getEmail();
        UserTextView.setText("Login as : "+user.getDisplayName());
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
