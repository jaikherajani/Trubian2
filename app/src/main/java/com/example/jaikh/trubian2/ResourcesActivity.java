package com.example.jaikh.trubian2;

import android.os.Bundle;

public class ResourcesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        setTitle(getResources().getString(R.string.app_name) + " : Resources");
    }
}
