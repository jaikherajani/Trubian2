package com.example.jaikh.trubian2;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TimeTableActivity extends AppCompatActivity {

    SimpleDraweeView imageView;
    StorageReference firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        //Set title of this activity
        setTitle("Trubian");

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
                /*ImageRequest imageRequest = ImageRequestBuilder
                        .newBuilderWithSource(uri)
                        .setRotationOptions(RotationOptions.autoRotate())
                        .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE)
                        .setProgressiveRenderingEnabled(true)
                        //.setResizeOptions(new ResizeOptions(width, height))
                        .build();

                System.out.println("imagerequest uri - "+imageRequest.getSourceUri());

                DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setImageRequest(ImageRequest.fromUri(uri))
                        .build();
                imageView.setController(draweeController);
                imageView.setScrollContainer(true);*/
                imageView.setImageURI(uri);
                /*imageView.setController(
                        Fresco.newDraweeControllerBuilder()
                                .setImageRequest(imageRequest)
                                .build());*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}
