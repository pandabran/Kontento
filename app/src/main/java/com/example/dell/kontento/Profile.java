package com.example.dell.kontento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Profile extends AppCompatActivity {

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new SQLiteHelper(this);

        //insert supposed query

    }
}
