package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ContactsActivity extends BaseActivity {

    RecyclerView recyclerView;
    RecyclerAdapter2 mAdapter;
    ArrayList<String> namesList = new ArrayList<>();
    ArrayList<Integer> imageList = new ArrayList<>();
    ArrayList<String> designationList = new ArrayList<>();
    ArrayList<String> phoneList = new ArrayList<>();
    ArrayList<String> emailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setTitle(getResources().getString(R.string.app_name) + " : Contacts");

        Collections.addAll(namesList, "Amit Saxena", "Jai Kherajani");
        Collections.addAll(designationList, "HOD, CSE", "Student");
        Collections.addAll(phoneList, "9229221068", "9630678955");
        Collections.addAll(emailList, "amitsaxena@trubainstitute.ac.in", "jai.kherajani19@gmail.com");
        //Collections.addAll(imageList, R.drawable.news_feed, R.drawable.time_table, R.drawable.calendar, R.drawable.resources, R.drawable.contacts, R.drawable.about, R.drawable.maps, R.drawable.courses, R.drawable.mis, R.drawable.others);

        mAdapter = new RecyclerAdapter2(ContactsActivity.this, namesList, imageList, designationList, phoneList, emailList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        System.out.println("ContactsActivity: " + mAdapter.getItemCount());
        recyclerView.setAdapter(mAdapter);
    }
}
