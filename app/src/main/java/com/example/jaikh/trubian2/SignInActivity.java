package com.example.jaikh.trubian2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    static String TAG = "SignInActivity";
    ProgressBar progressBar;
    Button signIn, signUp;
    AutoCompleteTextView emailField;
    EditText passwordField;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //binding UI
        progressBar = findViewById(R.id.login_progress);
        signIn = findViewById(R.id.sign_in_button);
        signUp = findViewById(R.id.register_button);
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        //firebase initialization
        mAuth = FirebaseAuth.getInstance();

        //onClickListeners
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    signIn(emailField.getText().toString(), passwordField.getText().toString());
                    signIn.setVisibility(Button.GONE);
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
    }

    void showProgressBar() {
        progressBar.isIndeterminate();
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
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
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser firebaseUser) {
        hideProgressBar();
        if (firebaseUser != null) {
            Toast.makeText(this, "Signed in as " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Signed Out!", Toast.LENGTH_SHORT).show();
        }
    }

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

        return valid;
    }
}
