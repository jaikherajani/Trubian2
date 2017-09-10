package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        Map<String, String> userValues = (Map<String, String>) getIntent().getSerializableExtra("user_values_map");
        emailField = findViewById(R.id.email);
        usernameField = findViewById(R.id.user_name);
        enrollmentnumberField = findViewById(R.id.enrollment_number);
        display(userValues);
    }

    void display(Map<String, String> userValues) {
        emailField.setText(userValues.get("email"));
        usernameField.setText(userValues.get("name"));
        enrollmentnumberField.setText(userValues.get("enrollment_number"));
    }
}
