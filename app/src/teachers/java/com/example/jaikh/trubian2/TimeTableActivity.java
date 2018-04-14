package com.example.jaikh.trubian2;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TimeTableActivity extends AppCompatActivity {

    ImageView imageView;
    StorageReference firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        //Set title of this activity
        setTitle(getResources().getString(R.string.app_name) + " : Time Table");

        imageView = findViewById(R.id.timetable);
        firebaseStorage = FirebaseStorage.getInstance().getReference("time_tables/");

        trubian2 t2 = (trubian2) getApplicationContext();
        String tt = t2.getData().get("enrollment_number").substring(4, 9) + ".jpg";
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
                Toast.makeText(TimeTableActivity.this, "Failed to load time table.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
