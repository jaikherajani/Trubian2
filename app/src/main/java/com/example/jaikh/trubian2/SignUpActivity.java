package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    static String TAG = "SignUpActivity";
    ProgressBar progressBar;
    Button verifyEmail;
    EditText emailField, passwordField, userNameField, enrollmentNumberField;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users/students");

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        userNameField = findViewById(R.id.user_name);
        enrollmentNumberField = findViewById(R.id.enrollment_number);

        progressBar = findViewById(R.id.signup_progress);
        verifyEmail = findViewById(R.id.sign_up_button);
        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    showProgressBar();
                    createAccount(emailField.getText().toString(), passwordField.getText().toString());
                }
            }
        });
    }

    void showProgressBar() {
        verifyEmail.setVisibility(Button.GONE);
        progressBar.isIndeterminate();
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
        verifyEmail.setVisibility(Button.VISIBLE);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed. Reason - " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    /*public void updateUI(FirebaseUser firebaseUser) {
        hideProgressBar();
        if (firebaseUser != null) {
            Toast.makeText(this, "Signed in as " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            sendEmailVerification();
        } else {
            Toast.makeText(this, "Signed Out!", Toast.LENGTH_SHORT).show();
        }
    }*/

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else if (password.length() < 6) {
            passwordField.setError("Must be atleast 6 characters");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        String username = userNameField.getText().toString();
        if (TextUtils.isEmpty(username)) {
            userNameField.setError("Required.");
            valid = false;
        } else if (username.length() < 3) {
            userNameField.setError("User Name mus be atleast 3 characters long.");
        } else {
            userNameField.setError(null);
        }

        String enrollmentnumber = enrollmentNumberField.getText().toString();
        if (TextUtils.isEmpty(username)) {
            enrollmentNumberField.setError("Required.");
            valid = false;
        } else if (enrollmentnumber.length() != 12) {
            enrollmentNumberField.setError("Invalid Enrollment Number");
        } else {
            enrollmentNumberField.setError(null);
        }

        return valid;
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        hideProgressBar();

                        if (task.isSuccessful()) {
                            String enrollmentNumber = enrollmentNumberField.getText().toString();
                            Toast.makeText(SignUpActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            Map<String, String> userData = new HashMap<String, String>();
                            userData.put("name", userNameField.getText().toString());
                            userData.put("enrollment_number", enrollmentNumber);
                            userData.put("email", mAuth.getCurrentUser().getEmail());
                            //databaseReference = databaseReference.child(enrollmentNumber.substring(4,5)+"/"+enrollmentNumber.substring(6,8)+"/"+enrollmentNumber);
                            databaseReference = databaseReference.child(user.getUid());
                            databaseReference.setValue(userData);
                            mAuth.signOut();
                            SignUpActivity.this.finish();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(SignUpActivity.this,
                                    "Failed to send verification email. Reason - " + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
}
