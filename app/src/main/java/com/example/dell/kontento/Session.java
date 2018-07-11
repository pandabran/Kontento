package com.example.dell.kontento;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;
    int user_id = -1;

    public Session(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedIn(boolean loggedin, int user_id){
        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();
    }

    public int getUserId(){ return this.user_id; }

    public boolean loggedIn(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
