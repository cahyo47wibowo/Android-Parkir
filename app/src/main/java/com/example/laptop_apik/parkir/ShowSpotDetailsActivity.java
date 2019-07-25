package com.example.laptop_apik.parkir;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowSpotDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ShowSpotDetailsActivity";
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    List<SpotDetails> list = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Monitoring");
        setContentView(R.layout.activity_show_spot_details);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new LineDividerItemDecoration(this,R.drawable.line_divider));

        adapter = new RecyclerViewAdapter(ShowSpotDetailsActivity.this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(ShowSpotDetailsActivity.this);
        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.Database_Path);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                Iterable<DataSnapshot> snapshotIterable = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator= snapshotIterable.iterator();
                while (iterator.hasNext()){
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String name = (String)next.child("name").getValue();
                    String status = (String)next.child("status").getValue();

                    String key=next.getKey();
                    list.add(new SpotDetails(name,status));
                    adapter.notifyDataSetChanged();
//                    Log.d(TAG, "onDataChange: "+key);
//                    Log.d(TAG, "onDataChange: "+status);
                }
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);
        }
}
