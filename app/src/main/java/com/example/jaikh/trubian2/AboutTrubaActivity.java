package com.example.jaikh.trubian2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class AboutTrubaActivity extends BaseActivity {

    TextView desc;
    ImageView i1, i2;
    FloatingActionButton b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_truba);
        //Set title of this activity
        setTitle(getResources().getString(R.string.app_name) + " : About Truba");

        desc = findViewById(R.id.description);
        i1 = findViewById(R.id.video1);
        b1 = findViewById(R.id.button1);
        i2 = findViewById(R.id.video2);
        b2 = findViewById(R.id.button2);

        desc.setText("Truba Group: Top engineering college in MP has been set up by Truba Advance Science Kombine (society known as TASKs).\n" +
                "\n" +
                "The main reason for the Truba to exist in the world is to impart knowledge to the students who are studying in it not only for obtaining higher marks but for improving students capability and out of the box thinking.\n" +
                "\n" +
                "Education is all about creating an environment of academic freedom, where bright minds meet, discover and learn. One would experience top of the world living and learning experience at TRUBA, a place where you have the freedom to fly.\n" +
                "\n" +
                "Truba Group Of Institutes is among the top ranking Engineering colleges in Madhya Pradesh with over 3000 students and more than 150 faculty across the campus, offering a wide range of undergraduate and postgraduate courses in Engineering and Pharmacy.\n" +
                "\n" +
                "Truba offers a variety of facilities, State-of-the-art labs, libraries, Wi-Fi, knowledge centre, 1000 capacity AC auditorium,  online smart classrooms, Hostels with premium facilities, endless convenience on campus including ATM’s, bookstores, cafeterias and more. The First Institute to have IBM sponsored “Centre of Excellence” in M.P .\n" +
                "\n" +
                "Also Rated Under: Top 100 Institutes of India Quest Magazine, Top Engineering Colleges in MP, Best Engineering Colleges in MP .");
        if (isNetworkAvailable()) {
            i1.setVisibility(View.VISIBLE);
            b1.setVisibility(View.VISIBLE);
            i2.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load("https://img.youtube.com/vi/4eApadNKBSg/hqdefault.jpg")
                    .into(i1);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.youtube.com/watch?v=4eApadNKBSg"));
                    startActivity(intent1);
                }
            });
            Glide.with(this)
                    .load("https://img.youtube.com/vi/v1rclp5ATYE/hqdefault.jpg")
                    .into(i2);

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.youtube.com/watch?v=v1rclp5ATYE"));
                    startActivity(intent1);
                }
            });
        } else {
            Toast.makeText(this, "Video playback requires an internet connection!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
