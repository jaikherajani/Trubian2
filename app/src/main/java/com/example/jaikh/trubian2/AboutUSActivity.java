package com.example.jaikh.trubian2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class AboutUSActivity extends BaseActivity {

    TextView bottom;
    CardView cv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);

        setTitle(getResources().getString(R.string.app_name) + " : About Us");

        bottom = findViewById(R.id.bottom_line);
        bottom.setText("Dedicated to all Trubians. " + getEmojiByUnicode(0x1F60D));
        cv1 = findViewById(R.id.github);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jaikherajani/trubian2")));
            }
        });
    }

    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
