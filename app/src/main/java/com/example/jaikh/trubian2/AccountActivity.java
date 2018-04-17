package com.example.jaikh.trubian2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AccountActivity extends BaseActivity {

    static String TAG = "AccountActivity";
    FloatingActionButton signOut, save, cancel, resetPassword;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView enrollmentNumber;
    EditText userName, userEmail, userPassword;
    boolean status = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HashMap<String, String> userValues = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //stops keyboard from popping up automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Set title of this activity
        setTitle(getResources().getString(R.string.app_name) + " : Account Settings");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users/students").child(mUser.getUid());

        signOut = findViewById(R.id.sign_out_button);
        save = findViewById(R.id.save_button);
        cancel = findViewById(R.id.cancel_button);
        enrollmentNumber = findViewById(R.id.enrollment_number);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        resetPassword = findViewById(R.id.reset_password_button);

        trubian2 t2 = (trubian2) getApplicationContext();
        userValues = t2.getData();

        userEmail.setText(userValues.get("email"));
        userName.setText(userValues.get("name"));
        enrollmentNumber.setText(userValues.get("enrollment_number"));

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(AccountActivity.this, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                //startActivity(new Intent(AccountActivity.this,com.example.jaikh.trubian2.com.example.jaikh.trubian2.SignInActivity.class));
                Toast.makeText(AccountActivity.this, "User successfully signed-out!", Toast.LENGTH_SHORT).show();
                AccountActivity.this.finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail.setText(userValues.get("email"));
                userName.setText(userValues.get("name"));
                enrollmentNumber.setText(userValues.get("enrollment_number"));
                userPassword.setText("");
                status = false;
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    authenticateUser();

                }
            }
        });
        /*userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayButtons();
            }
        });*/
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = true;
                if (validateForm()) {
                    authenticateUser();

                }
            }
        });
        /*userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayButtons();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //create an intent to previous activity and put the data into it
                Intent intent = new Intent(this, HomeActivity.class);
                //intent.putExtra("user_values_map", userValues);
                AccountActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = userEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Required.");
            valid = false;
        } else {
            userEmail.setError(null);
        }
        String name = userName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            userName.setError("Required.");
            valid = false;
        } else {
            userName.setError(null);
        }
        String password = userPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Required.");
            valid = false;
        } else if (password.length() < 6) {
            userPassword.setError("Must be atleast 6 characters");
            valid = false;
        } else {
            userPassword.setError(null);
        }
        return valid;
    }

    private void authenticateUser() {
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mUser.getEmail(), userPassword.getText().toString());

        // Prompt the user to re-provide their sign-in credentials
        mUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated.");
                            if (status) {
                                mAuth.sendPasswordResetEmail(mUser.getEmail())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "Email sent.");
                                                    status = false;
                                                }
                                            }
                                        });
                                mAuth.signOut();
                                startActivity(new Intent(AccountActivity.this, SignInActivity.class));
                                Toast.makeText(AccountActivity.this, "Please Sign-In again!", Toast.LENGTH_SHORT).show();
                                AccountActivity.this.finish();
                                return;
                            } else {
                                if (!mUser.getEmail().equals(userEmail.getText().toString())) {
                                    mUser.updateEmail(userEmail.getText().toString());
                                    mUser.sendEmailVerification()
                                            .addOnCompleteListener(AccountActivity.this, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    // [START_EXCLUDE]
                                                    // Re-enable button
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AccountActivity.this,
                                                                "Verification email sent !",
                                                                Toast.LENGTH_SHORT).show();
                                                        databaseReference.child("email").setValue(userEmail.getText().toString());
                                                /*mAuth.signOut();
                                                AccountActivity.this.finish();*/
                                                    } else {
                                                        Log.e(TAG, "sendEmailVerification", task.getException());
                                                        Toast.makeText(AccountActivity.this,
                                                                "Failed to send verification email. Reason - " + task.getException(),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                    // [END_EXCLUDE]
                                                }
                                            });
                                    // [END send_email_verification]
                                }
                                if (!databaseReference.child("name").getKey().equals(userName.getText().toString())) {
                                    Log.d(TAG, databaseReference.child("name").getKey());
                                    databaseReference.child("name").setValue(userName.getText().toString());
                                }
                                mAuth.signOut();
                                startActivity(new Intent(AccountActivity.this, SignInActivity.class));
                                Toast.makeText(AccountActivity.this, "Please Sign-In again!", Toast.LENGTH_SHORT).show();
                                AccountActivity.this.finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AccountActivity.this, "Authentication failed. Reason - " + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    /*private void displayButtons() {
        save.setVisibility(Button.VISIBLE);
        cancel.setVisibility(Button.VISIBLE);
    }*/
}

