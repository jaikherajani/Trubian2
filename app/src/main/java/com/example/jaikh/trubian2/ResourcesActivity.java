package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        setTitle(getResources().getString(R.string.app_name) + " : Resources");
    }
}
