package com.example.dell.kontento.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;

import com.example.dell.kontento.helper.*;
import com.example.dell.kontento.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    //LOGCAT TAG
    private static final String LOG = "DatabaseHelper";

    //DATABASE NAME
    private static final String DATABASE_NAME = "kontento.db";

    //DATABASE VERSION
    private static final int DATABASE_VERSION = 1;

    //TABLE NAMES
    private static final String USER_TABLE = "user";
    private static final String TASK_TABLE = "task";

    // USER_TABLE COLUMN NAMES
    private static final String USER_COL1 = "user_id";
    private static final String USER_COL2 = "firstname";
    private static final String USER_COL3 = "lastname";
    private static final String USER_COL4 = "email";
    private static final String USER_COL5 = "password";
    private static final String USER_COL6 = "age";
    private static final String USER_COL7 = "sex";

    //TABLE CREATE STATEMENTS
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE + "("
            + USER_COL1 + " INTEGER PRIMARY KEY, " + USER_COL2 + " TEXT, " + USER_COL3 +
            " TEXT, " + USER_COL4 + " TEXT, " + USER_COL5 + " TEXT, " + USER_COL6 +
            " INTEGER, " + USER_COL7 + " TEXT " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

        onCreate(db);
    }

    // -- USER TABLE CRUD

    //REGISTER - create
    public long createUser(String firstname, String lastname, String email,
                           String password, int age, String sex){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_COL2, firstname);
        values.put(USER_COL3, lastname);
        values.put(USER_COL4, email);
        values.put(USER_COL5, password);
        values.put(USER_COL6, age);
        values.put(USER_COL7, sex);

        long user_id = db.insert(USER_TABLE, null, values);

        return user_id;
    }

    //LOGIN - reading
    public int isLoginCorrect(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * from " + USER_TABLE + " WHERE " + USER_COL4 + " = '" + email
                + "' AND password = '" + password + "'";
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            return c.getInt(c.getColumnIndex("user_id"));
        }else{
            return -1;
        }
    }

    //READING - gets the number of accounts
    public int getNumAccounts(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            return c.getCount();
        }else{
            return 0;
        }
    }

    //CLOSING - severing the DB Connection
    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    //READING - gets the list of tasks
    public Cursor getTaskList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TASK_TABLE,null);
        return data;
    }

    //DELETE - deletes a given task
    public void deleteTask(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from Data WHERE ID='"+id+"'"); // double check the db schema to confirm which attributes correspond to which
//        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});

        db.close();
    }

    //READ - checks if email exists
    public boolean isEmailExisting(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE email = '" + email + "'";
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

}
