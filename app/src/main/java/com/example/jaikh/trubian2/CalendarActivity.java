package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setTitle(getResources().getString(R.string.app_name) + " : Calendar");

        WebView webView = findViewById(R.id.web_cal);
        WebSettings webViewSettings = webView.getSettings();
        CardView cv = findViewById(R.id.calendarcard);
        webViewSettings.setJavaScriptEnabled(true);
        int height = this.getResources().getDisplayMetrics().heightPixels;
        int width = this.getResources().getDisplayMetrics().widthPixels;
        width = width / 3;
        width = width - 20;
        height = height / 4;
        height = height - 20;
        Log.i("Height", "is " + height);
        Log.i("width", "is " + width);
        webView.loadData("<iframe src=\"https://calendar.google.com/calendar/embed?src=n9h2rd7k61aor8s11kpgvbo0fg%40group.calendar.google.com&ctz=Asia/Calcutta\" style=\"border: 0\" width=\'" + width + "\' height=\'" + height + "\' frameborder=\"0\" scrolling=\"no\"></iframe>", "text/html", "utf-8");
        Log.i("URL", webView.getUrl());
    }

}
