package com.example.dell.kontento.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.GridView;

import com.example.dell.kontento.R;
import com.example.dell.kontento.db.MemoriesAdapter;
import com.example.dell.kontento.db.MemoryDbHelper;

public class MemoriesActivity extends AppCompatActivity {
    private MemoryDbHelper dbHelper;
    private GridView gridView;
    private SensorManager mSensorManager;

    private float acelVal; // CURRENT ACCELERATION VALUE AND GRAVITY
    private float acelLast; // LAST ACCELERATION VALUE AND GRAVITY
    private float shake; // ACCELERATION VALUE differ FROM GRAVITY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        this.gridView = (GridView) findViewById(R.id.activity_main_grid_view);
        this.dbHelper = new MemoryDbHelper(this);
        this.gridView.setAdapter(new MemoriesAdapter(this, this.dbHelper.readAllMemories(), false));
        this.gridView.setEmptyView(findViewById(R.id.activity_main_empty_view));

        // Shake Detector Initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            if(shake > 12){
                addNewRecordTaskShake(this);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        ((CursorAdapter)gridView.getAdapter()).swapCursor(this.dbHelper.readAllMemories());
    }

    public void addNewMemory(View view) {
        Intent intent = new Intent(this, NewMemoryActivity.class);
        startActivity(intent);
    }

    public void addNewRecordTaskShake (SensorEventListener view) {
        Intent recordIntent = new Intent(this, NewRecordTask.class);
        startActivity(recordIntent);
    }

    public void addNewRecordTask (View view) {
        Intent recordIntent = new Intent(this, NewRecordTask.class);
        startActivity(recordIntent);
    }

    public void viewCalendar (View view) {
        Intent calendarIntent = new Intent(this, Calendar.class);
        startActivity(calendarIntent);
    }

    public void viewPrivacyPolicy (View view) {
        Intent PrivacyIntent = new Intent(this, PrivacyPolicy.class);
        startActivity(PrivacyIntent);
    }


}
