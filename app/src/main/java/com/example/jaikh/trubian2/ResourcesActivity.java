package com.example.jaikh.trubian2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ResourcesActivity extends BaseActivity {

    ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        setTitle(getResources().getString(R.string.app_name) + " : Resources");

        //must add http:// before the url or else the app will crash
        urls.add("https://www.tutorialspoint.com/index.htm");
        urls.add("https://guides.codepath.com/android");
        urls.add("https://codelabs.developers.google.com/");
        urls.add("https://developers.google.com/machine-learning/crash-course/ml-intro");
        urls.add("https://www.hackerrank.com/");
        urls.add("https://www.hackerearth.com/");
        urls.add("https://www.careercup.com/");
        urls.add("https://leetcode.com/");
        urls.add("https://docs.oracle.com/javase/8/docs/");
        urls.add("https://docs.python.org/3/tutorial/");
        urls.add("https://www.codecademy.com/");
        urls.add("https://www.khanacademy.org/");
        urls.add("https://www.freecodecamp.org/");
        urls.add("https://mva.microsoft.com/");
        urls.add("https://www.udacity.com/");
        urls.add("https://www.udemy.com/");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, urls);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(position))));
            }
        });


    }
}
