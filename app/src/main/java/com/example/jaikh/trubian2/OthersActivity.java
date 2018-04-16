package com.example.jaikh.trubian2;

import android.os.Bundle;

public class OthersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        setTitle(getResources().getString(R.string.app_name) + " : Others");
    }
}
