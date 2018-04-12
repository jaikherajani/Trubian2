package com.example.jaikh.trubian2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    static final boolean RESET_PWD = true; //when form is validated only for resetting the password
    static String TAG = "com.example.jaikh.trubian2.com.example.jaikh.trubian2.SignInActivity";
    ProgressBar progressBar;
    Button signIn, signUp, forgotPassword;
    EditText emailField, passwordField;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //stops keyboard from popping up automatically
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //binding UI
        progressBar = findViewById(R.id.login_progress);
        signIn = findViewById(R.id.sign_in_button);
        signUp = findViewById(R.id.register_button);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.reset_password_button);

        //firebase initialization
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        intent = new Intent(SignInActivity.this, HomeActivity.class);

        //onClickListeners
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm(false)) {
                    signIn(emailField.getText().toString(), passwordField.getText().toString());
                    showProgressBar();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar();
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        if (validateForm(RESET_PWD)) {
            mAuth.sendPasswordResetEmail(emailField.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                hideProgressBar();
                                Toast.makeText(SignInActivity.this, "Email containing password reset link has been sent to the above email id.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        hideProgressBar();
    }

    void showProgressBar() {
        progressBar.isIndeterminate();
        signIn.setVisibility(Button.GONE);
        forgotPassword.setVisibility(Button.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
        signIn.setVisibility(Button.VISIBLE);
        forgotPassword.setVisibility(Button.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed. Reason - " + task.getException(),
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
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
                        Toast.makeText(SignInActivity.this, "Signed in as " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                        hideProgressBar();
                        trubian2 t2 = (trubian2) getApplicationContext();
                        t2.setData(values);
                        startActivity(intent);
                        SignInActivity.this.finish();
                    } else {
                        //if user's email is not verified
                        Toast.makeText(SignInActivity.this, "Please verify your email first.", Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(SignInActivity.this, "Interrupted by User!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            hideProgressBar();
            Toast.makeText(this, "Please Sign-In first.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm(boolean status) {
        boolean valid = true;
        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }
        if (!status) {
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
        }
        return valid;
    }
}
