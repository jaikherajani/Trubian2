package com.example.jaikh.trubian2;

import android.os.Bundle;

public class CoursesOfferedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_offered);

        //Set title of this activity
        setTitle(getResources().getString(R.string.app_name) + " : Courses Offered");
    }
}
