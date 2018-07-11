package com.example.dell.kontento;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kontento.activities.MemoriesActivity;
import com.example.dell.kontento.activities.NewRecordTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mlist;
    CheckBox check;
    TextView tasked;
    ArrayList<TaskModel> taskList;
    SQLiteHelper mydb;
    ImageView addButton;

    ImageView menuB;
    String date_n;
    SimpleDateFormat time_n;
    TextView date,time, today_is_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        mydb = new SQLiteHelper(this);
        taskList = new ArrayList<TaskModel>();
        date_n =  new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        time_n = new SimpleDateFormat("hh:mm a");

        mlist = (ListView) findViewById(R.id.listView1);

        tasked= (TextView) findViewById(R.id.text1);
        check = (CheckBox) findViewById(R.id.checking);

        date = (TextView) findViewById(R.id.cdate);
        time = (TextView)findViewById(R.id.ctime);
        addButton = (ImageView)findViewById(R.id.addButton);

        date.setText(date_n);
        time.setText(time_n.format(calendar.getTime()));
        today_is_text = (TextView)findViewById(R.id.today_is_text);

        menuB = (ImageView)findViewById(R.id.menuB);

        Cursor cursor = mydb.getTask();

        ///populating the Tasklist
        if(cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No tasks for today?", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                int tid = cursor.getInt(0);
                String stat = cursor.getString(1);
                String task = cursor.getString(2);
                String val = cursor.getString(3);
                taskList.add(new TaskModel(tid, stat, task,val));
            }
        }

        TaskAdapter adapter = new TaskAdapter(this, R.layout.row,taskList);
        mlist.setAdapter(adapter);

        mlist.invalidateViews();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hi = new Intent(MainActivity.this, AddTask.class);
                startActivity(hi);
            }
        });

        menuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent memories = new Intent(MainActivity.this,MemoriesActivity.class);
                startActivity(memories);
            }
        });
    }
}
