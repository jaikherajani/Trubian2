package com.example.jaikh.trubian2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    static String TAG = "com.example.jaikh.trubian2.com.example.jaikh.trubian2.SplashActivity";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(SplashActivity.this, HomeActivity.class);
        //firebase initialization
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(final FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            firebaseUser.reload();
            databaseReference = firebaseDatabase.getReference("users/students/" + firebaseUser.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String, String> values = (HashMap<String, String>) dataSnapshot.getValue();
                    Log.d(TAG, "Link is " + databaseReference.toString());
                    Log.d(TAG, "Value is: " + values);
                    Log.d(TAG, "name is: " + values.get("name"));
                    Log.d(TAG, "e_key is: " + values.get("enrollment_number"));
                    Log.d(TAG, "email is: " + values.get("email"));
                    intent.putExtra("user_values_map", values);
                    if (firebaseUser.isEmailVerified()) {
                        //if user has got his email verified
                        Toast.makeText(SplashActivity.this, "Signed in as " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                        trubian2 t2 = (trubian2) getApplicationContext();
                        t2.setData(values);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    } else {
                        //if user's email is not verified
                        startActivity(new Intent(SplashActivity.this,SignInActivity.class));
                        SplashActivity.this.finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    startActivity(new Intent(SplashActivity.this,SignInActivity.class));
                    SplashActivity.this.finish();
                }
            });
        } else {
            startActivity(new Intent(SplashActivity.this,SignInActivity.class));
            SplashActivity.this.finish();
        }
    }
}
