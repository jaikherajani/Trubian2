package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class SignUpActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.signup_progress);
        signUp = findViewById(R.id.sign_up_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.isIndeterminate();
                signUp.setVisibility(Button.GONE);
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }
        });
    }
}
