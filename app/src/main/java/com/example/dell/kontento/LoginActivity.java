package com.example.dell.kontento;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.kontento.*;
import com.example.dell.kontento.helper.DatabaseHelper;

import org.w3c.dom.Text;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    //Database Helper
    DatabaseHelper db;
    boolean isLoginWrong = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Use the DB
        db = new DatabaseHelper(getApplicationContext());

        //Login Components
        Button loginButton = (Button)findViewById(R.id.loginButton);
        final TextView headerText = (TextView)findViewById(R.id.headerText);
        final AutoCompleteTextView email = (AutoCompleteTextView)findViewById(R.id.user_email);
        final AutoCompleteTextView password = (AutoCompleteTextView)findViewById(R.id.user_password);
        TextView signupButton = (TextView)findViewById(R.id.signupButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // change code to validators
                int user_id = db.isLoginCorrect(email.getText().toString(), password.getText().toString());
                if(user_id != -1){
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    headerText.setTextColor(Color.GREEN);
                    headerText.setText("Successfully logged in!");
                    email.setBackgroundResource(R.drawable.rounded_okay);
                    password.setBackgroundResource(R.drawable.rounded_okay);

                    final int sentUserId = user_id;
                    new Handler().postDelayed(new Runnable() {
                        int user_id;

                        @Override
                        public void run() {
                            Intent LoginActivity = new Intent(LoginActivity.this, com.example.dell.kontento.MainActivity.class);
                            startActivity(LoginActivity);
                            finish();
                        }
                    }, 750);
                }else{
                    Drawable img = getDrawable(R.drawable.user_icon);
                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_icon, 0, 0, 0);
                    email.setBackgroundResource(R.drawable.rounded_error);
                    password.setBackgroundResource(R.drawable.rounded_error);
                    headerText.setTextColor(Color.RED);
                    headerText.setText("Invalid Username and/or Password");
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegistrationActivity = new Intent(LoginActivity.this, com.example.dell.kontento.RegistrationActivity.class);
                startActivity(RegistrationActivity);
                finish();
            }
        });
    }
}
