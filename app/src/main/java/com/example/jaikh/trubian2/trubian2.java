package com.example.jaikh.trubian2;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jaikh on 05-09-2017.
 */

public class trubian2 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
