package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    static String TAG = "HomeActivity";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView emailField, usernameField, enrollmentnumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users/students/" + firebaseUser.getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> values = (Map<String, String>) dataSnapshot.getValue();
                Log.d(TAG, "Link is " + databaseReference.toString());
                Log.d(TAG, "Value is: " + values);
                Log.d(TAG, "name is: " + values.get("name"));
                Log.d(TAG, "e_key is: " + values.get("enrollment_number"));
                Log.d(TAG, "email is: " + values.get("email"));
                display(values);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        emailField = findViewById(R.id.email);
        usernameField = findViewById(R.id.user_name);
        enrollmentnumberField = findViewById(R.id.enrollment_number);
    }

    void display(Map<String, String> userValues) {
        emailField.setText(userValues.get("email"));
        usernameField.setText(userValues.get("name"));
        enrollmentnumberField.setText(userValues.get("enrollment_number"));
    }
}
