package com.example.jaikh.trubian2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    static String TAG = "HomeActivity";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView emailField, usernameField, enrollmentnumberField;
    SimpleDraweeView userPicture;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set title of this activity
        setTitle("Trubian");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Collections.addAll(itemList, "News Feed", "Notifications", "Time Table", "Calendar", "Resources", "Contacts", "About");
        Collections.addAll(imageList, R.drawable.news_feed, R.drawable.notifications, R.drawable.time_table, R.drawable.calendar, R.drawable.resources, R.drawable.contacts, R.drawable.about);

        mAdapter = new RecyclerAdapter(itemList, imageList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //recyclerView.hasFixedSize();
        System.out.println("HomeActivity: " + mAdapter.getItemCount());
        recyclerView.setAdapter(mAdapter);

        final HashMap<String, String> userValues = (HashMap<String, String>) getIntent().getSerializableExtra("user_values_map");
        emailField = findViewById(R.id.email);
        usernameField = findViewById(R.id.user_name);
        enrollmentnumberField = findViewById(R.id.enrollment_number);
        userPicture = findViewById(R.id.user_picture);
        display(userValues);

        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                intent.putExtra("user_data", userValues);
                startActivity(intent);
                /*HomeActivity.this.finish();*/
            }
        });
    }

    void display(HashMap<String, String> userValues) {
        emailField.setText(userValues.get("email"));
        usernameField.setText(userValues.get("name"));
        enrollmentnumberField.setText(userValues.get("enrollment_number"));
    }
}
