package com.example.jaikh.trubian2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends BaseActivity {

    CompactCalendarView calendarView;
    DatabaseReference databaseReference;
    ArrayList<Event> eventsList = new ArrayList<>();
    TextView month, date, event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        setTitle(getResources().getString(R.string.app_name) + " : Calendar");

        calendarView = findViewById(R.id.calendarView);
        calendarView.shouldSelectFirstDayOfMonthOnScroll(false);
        calendarView.setFirstDayOfWeek(1);
        calendarView.setUseThreeLetterAbbreviation(true);

        final SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

        databaseReference = FirebaseDatabase.getInstance().getReference("Events/" + Calendar.getInstance().get(Calendar.YEAR));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*dates.add(dataSnapshot.getKey());
                events.add(String.valueOf(dataSnapshot.getValue()));
                System.out.println(dates.get(0)+" : "+events.get(0));*/
                System.out.println("Data : " + dataSnapshot);
                eventsList.clear();
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    System.out.println("event data : " + eventSnapshot.child("date").getValue(String.class));

                    try {
                        Date d = f.parse(eventSnapshot.child("date").getValue(String.class));
                        eventsList.add(new Event(getResources().getColor(R.color.Black), d.getTime(), eventSnapshot.child("name").getValue(String.class)));
                        //long milliseconds = d.getTime();
                        loadData();
                        loadToday();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //dates.add(eventSnapshot.child("date").getValue(String.class));
                    //events.add(eventSnapshot.child("name").getValue(String.class));

                    //imagesDir.add(imageSnapshot.child("address").getValue(String.class));
                }
                //Log.i("MyTag", imagesDir.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CalendarActivity.this, "Something happened while fetching events!", Toast.LENGTH_SHORT).show();
            }
        });

        /*WebView webView = findViewById(R.id.web_cal);
        WebSettings webViewSettings = webView.getSettings();
        //CardView cv = findViewById(R.id.calendarcard);
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
        Log.i("URL", webView.getUrl());*/
        /*for (int i = 0; i < dates.size(); i++)
        {
            calendarView.setDate(dates.get(i));
        }*/

        month = findViewById(R.id.month);
        date = findViewById(R.id.date);
        event = findViewById(R.id.event);

        month.setText(calendarView.getFirstDayOfCurrentMonth().toString().substring(4, 7));
        date.setText(f.format(Calendar.getInstance().getTime()));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = calendarView.getEvents(dateClicked);
                Log.d("CalendarActivity : ", "Day was clicked: " + dateClicked + " with events : " + events);
                date.setText(f.format(dateClicked));
                event.setText("");
                if (!events.isEmpty()) {
                    for (int i = 0; i < events.size(); i++) {
                        //event.setText(events.get(i).getData().toString());
                        if (i + 1 < events.size())
                            event.append(events.get(i).getData().toString() + ", ");
                        else
                            event.append(events.get(i).getData().toString());
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d("CalendarActivity : ", "Month was scrolled to: " + firstDayOfNewMonth);
                month.setText(firstDayOfNewMonth.toString().substring(4, 7));
            }
        });
    }

    private void loadData() {
        calendarView.removeAllEvents();
        for (int i = 0; i < eventsList.size(); i++) {
            calendarView.addEvent(eventsList.get(i));
        }
    }

    void loadToday() {
        Calendar temp_c = Calendar.getInstance();
        temp_c.set(Calendar.HOUR_OF_DAY, 0);
        temp_c.set(Calendar.MINUTE, 0);
        temp_c.set(Calendar.SECOND, 0);
        List<Event> events = calendarView.getEvents(temp_c.getTime());
        System.out.println("default date : " + temp_c.getTime() + " with events : " + events.toString());
        event.setText("");
        if (!events.isEmpty()) {
            for (int i = 0; i < events.size(); i++) {
                //event.setText(events.get(i).getData().toString());
                if (i + 1 < events.size())
                    event.append(events.get(i).getData().toString() + ", ");
                else
                    event.append(events.get(i).getData().toString());
            }
        }
    }
}
