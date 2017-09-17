package com.example.jaikh.trubian2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    Button signOut;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        signOut = findViewById(R.id.sign_out_button);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(AccountActivity.this, SignInActivity.class));
                Toast.makeText(AccountActivity.this, "User successfully signed-out!", Toast.LENGTH_SHORT).show();
                AccountActivity.this.finish();
            }
        });
    }
}
