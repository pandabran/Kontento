package com.example.dell.kontento;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kontento.activities.NewRecordTask;

public class AddTask extends AppCompatActivity {

    SQLiteHelper mydb;
    Button btnAdd;
    EditText mtask;
    Spinner drop;
    String val;
    TextView test;

    private SensorManager mSensorManager;

    private float acelVal; // CURRENT ACCELERATION VALUE AND GRAVITY
    private float acelLast; // LAST ACCELERATION VALUE AND GRAVITY
    private float shake; // ACCELERATION VALUE differ FROM GRAVITY

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

    public void addNewRecordTaskShake (SensorEventListener view) {
        Intent recordIntent = new Intent(this, NewRecordTask.class);
        startActivity(recordIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mydb = new SQLiteHelper(this);

        //bunding
        test = (TextView)findViewById(R.id.headerText);
        drop = findViewById(R.id.type);
        mtask = findViewById(R.id.task);
        btnAdd = findViewById(R.id.btnAdd);

        //Bundle
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            String message = bundle.getString("textRecorded");
            mtask.setText(message);
        }

        // change this
        String type [] =  getResources().getStringArray(R.array.type);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTask.this,R.layout.spinner,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drop.setAdapter(adapter);
        // up to here

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String tasking = mtask.getText().toString();
            String type = drop.getSelectedItem().toString();

            switch (type){
                case "General":
                    val = "1";
                    break;
                case "School":
                    val = "2";
                    break;
                case "Work":
                    val = "3";
                    break;
                case "Home":
                    val = "4";
                    break;
                case "Others":
                    val = "5";
                    break;
                default:
                    val = "1";
            }
            if(tasking.length() != 0 ){
                InsertTask(tasking);
                mtask.setText("");
                drop.setSelection(0);
                Intent my = new Intent(AddTask.this,MainActivity.class);
                startActivity(my);
            }else{
                Toast.makeText(AddTask.this,"Please input task",Toast.LENGTH_LONG).show();
            }
            }
        });

        // Shake Detector Initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }

    public void InsertTask(String tasking) {
        int status = 0;
        mydb = new SQLiteHelper(this);
        boolean valid = mydb.insertTask(tasking,status,val);

        try {
            if (valid == true) {
                Toast.makeText(AddTask.this, "Task Is added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddTask.this, "Task not added", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

