package com.example.jaikh.trubian2;

import android.os.Bundle;

public class ContactsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setTitle(getResources().getString(R.string.app_name) + " : Contacts");
    }
}
