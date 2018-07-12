package com.example.dell.kontento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "kotento.db";
    public static final String TABLE_NAME = "Data";
    public static final String COL1 = "ID";
    public static final String COL2 = "Status";
    public static final String COL3 = "Task";
    public static final String COL4 = "Type";

    public SQLiteHelper(Context context){super(context,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "STATUS VARCHAR, "+ "TASK VARCHAR, "+ "TYPE VARCHAR )";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void updateTask(String id,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        String update = "UPDATE Data SET Status ='"+status+ "' WHERE ID="+id;
        db.execSQL(update);
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL1, id);
//        contentValues.put(COL2, status);
//
//        long result = db.update(TABLE_NAME, contentValues, COL1+"=?", new String[]{id});
//        if (result == 0){
//            return false;
//        }else{
//            return true;
//        }
    }

    public void deleteTask(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from Data  WHERE ID='"+id+"'");
//        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        db.close();
    }

    public boolean insertTask(String task,int status,String val){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,status);
        contentValues.put(COL3,task);
        contentValues.put(COL4, val);


        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == -1 ){
            return  false;
        }else {
            return true;
        }
    }

    public Cursor getTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }
}
