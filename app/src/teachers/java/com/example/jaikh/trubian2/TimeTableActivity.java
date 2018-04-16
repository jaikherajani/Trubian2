package com.example.jaikh.trubian2;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class TimeTableActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    AppCompatImageView imageView;
    StorageReference firebaseStorage;
    String tt = "CS141";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        //Set title of this activity
        setTitle(getResources().getString(R.string.app_name) + " : Time Table");

        imageView = findViewById(R.id.timetable);
        firebaseStorage = FirebaseStorage.getInstance().getReference("time_tables/");

        // Spinner element
        Spinner spinner = findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("CS141");
        categories.add("CS151");
        categories.add("CS161");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        trubian2 t2 = (trubian2) getApplicationContext();
        //String tt = t2.getData().get("enrollment_number").substring(4, 9) + ".jpg";

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tt = parent.getItemAtPosition(position).toString();
        fetch();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void fetch()
    {
        tt +=".jpg";
        System.out.println(tt);
        System.out.println(firebaseStorage.child(tt).getPath());
        firebaseStorage.child(tt).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                System.out.println("download url - " + uri);
                System.out.println("download url - " + uri);
                Glide.with(TimeTableActivity.this)
                        .load(uri)
                        .into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(TimeTableActivity.this, "Failed to load the requested time table.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
