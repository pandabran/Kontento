package com.example.dell.kontento.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.dell.kontento.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Calendar extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    String date_n;
    SimpleDateFormat time_n;
    TextView date,time, today_is_text;

    @SuppressLint({"ResourceType", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mCalendarView = (CalendarView) findViewById(R.layout.activity_calendar);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        date_n =  new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        time_n = new SimpleDateFormat("hh:mm a");

        date = (TextView) findViewById(R.id.cdate);
        time = (TextView)findViewById(R.id.ctime);

        date.setText(date_n);
        time.setText(time_n.format(calendar.getTime()));
        today_is_text = (TextView)findViewById(R.id.today_is_text);
    }
}
