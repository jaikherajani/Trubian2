package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HomeActivity extends BaseActivity {

    static String TAG = "HomeActivity";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView emailField, usernameField, enrollmentnumberField;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>();
    RecyclerView recyclerView;
    HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(getResources().getString(R.string.app_name) + " : Home");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Collections.addAll(itemList, "News Feed", "Time Table", "Calendar", "Resources", "Contacts", "About Truba", "Maps & Directions", "Courses Offered", "MIS", "About Us");
        Collections.addAll(imageList, R.drawable.news_feed, R.drawable.time_table, R.drawable.calendar, R.drawable.resources, R.drawable.contacts, R.drawable.about, R.drawable.maps, R.drawable.courses, R.drawable.mis, R.drawable.others);
        //Collections.addAll(activitiesList, "HomeActivity.class","HomeActivity.class","TimeTableActivity.class","HomeActivity.class","HomeActivity.class","HomeActivity.class","HomeActivity.class");
        mAdapter = new HomeAdapter(HomeActivity.this, itemList, imageList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        //recyclerView.hasFixedSize();
        System.out.println("HomeActivity: " + mAdapter.getItemCount());
        recyclerView.setAdapter(mAdapter);

        //final HashMap<String, String> userValues = (HashMap<String, String>) getIntent().getSerializableExtra("user_values_map");
        trubian2 t2 = (trubian2) getApplicationContext();
        HashMap<String, String> userValues = t2.getData();
        emailField = findViewById(R.id.email);
        usernameField = findViewById(R.id.user_name);
        enrollmentnumberField = findViewById(R.id.enrollment_number);
        System.out.println("HomeActivity : User Picture : " + userValues.get("picture"));
        /*userPicture = findViewById(R.id.user_picture);
        display(userValues);

        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
               *//* HomeActivity.this.finish();*//*
            }
        });

        //App is launched first time
        new MaterialShowcaseView.Builder(this)
                .setTarget(userPicture)
                .setDismissText("GOT IT")
                .setContentText("Click here to see your profile related settings")
                .setDelay(500) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse("1") // provide a unique ID used to ensure it is only shown once
                .show();*/

    }

    /*void display(HashMap<String, String> userValues) {
        emailField.setText(userValues.get("email"));
        usernameField.setText(userValues.get("name"));
        enrollmentnumberField.setText(userValues.get("enrollment_number"));
    }*/
}
