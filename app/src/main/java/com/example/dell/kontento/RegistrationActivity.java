package com.example.dell.kontento;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kontento.helper.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseHelper db;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", Pattern.CASE_INSENSITIVE);

    boolean hasError = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Opening the DB
        db = new DatabaseHelper(getApplicationContext());

        //UI Components
        final TextView headerText = (TextView)findViewById(R.id.headerText);
        final AutoCompleteTextView firstname = (AutoCompleteTextView)findViewById(R.id.user_firstname);
        final AutoCompleteTextView lastname = (AutoCompleteTextView)findViewById(R.id.user_lastname);
        final AutoCompleteTextView email = (AutoCompleteTextView)findViewById(R.id.user_email);
        final TextView email_error = (TextView)findViewById(R.id.email_error);
        final AutoCompleteTextView password = (AutoCompleteTextView)findViewById(R.id.user_password);
        final AutoCompleteTextView confirm_password = (AutoCompleteTextView)findViewById(R.id.user_confirm_password);
        final AutoCompleteTextView age = (AutoCompleteTextView)findViewById(R.id.age);
        final Spinner sex = (Spinner)findViewById(R.id.sex);
        final TextView pass_error = (TextView)findViewById(R.id.pass_error);
        final TextView confirm_error = (TextView)findViewById(R.id.confirm_error);
        Button signupButton = (Button)findViewById(R.id.signupButton);
        TextView loginButton = (TextView)findViewById(R.id.loginButton);


        // -- Redirect to Login

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginActivity = new Intent(RegistrationActivity.this, com.example.dell.kontento.LoginActivity.class);
                startActivity(LoginActivity);
                finish();
            }
        });

        // -- SPINNER -- //
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.sex);
        //create a list of items for the spinner.
        String[] items = new String[]{"Male", "Female"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        // -- Sign User Up

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView firstname = (AutoCompleteTextView)findViewById(R.id.user_firstname);
                AutoCompleteTextView lastname = (AutoCompleteTextView)findViewById(R.id.user_lastname);
                AutoCompleteTextView email = (AutoCompleteTextView)findViewById(R.id.user_email);
                AutoCompleteTextView password = (AutoCompleteTextView)findViewById(R.id.user_password);
                AutoCompleteTextView confirm = (AutoCompleteTextView)findViewById(R.id.user_confirm_password);
                AutoCompleteTextView age = (AutoCompleteTextView)findViewById(R.id.age);
                Spinner sex = (Spinner)findViewById(R.id.sex);

                if(!hasError){
                    db.createUser(firstname.getText().toString(), lastname.getText().toString(), email.getText().toString(), password.getText().toString(), Integer.parseInt(age.getText().toString()), sex.getSelectedItem().toString());
                    Toast.makeText(RegistrationActivity.this, "Account successfully created!", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent LoginActivity = new Intent(RegistrationActivity.this, com.example.dell.kontento.LoginActivity.class);
                            startActivity(LoginActivity);
                            finish();
                        }
                    }, 2000);
                }else{
                    Toast.makeText(RegistrationActivity.this, "Couldn't create account. Please recheck all fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // -- SPINNER -- //

        // -- TextWatcher for onKeyUp validation of email

        TextWatcher emailKeyChecker = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // put validation here
                if(!email.getText().toString().trim().equals("")){
                    if(!db.isEmailExisting(email.getText().toString())){
                        if(validateEmail(email.getText().toString())){
                            email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_icon, 0,R.drawable.okay_icon, 0);
                            email.setBackgroundResource(R.drawable.rounded_okay);
                            email_error.setText("");
                            hasError = false;
                        }else{
                            email.setBackgroundResource(R.drawable.rounded_error);
                            email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_icon, 0,R.drawable.error_icon, 0);
                            email_error.setText("invalid email format!");
                            hasError = true;
                        }
                    }else{
                        email.setBackgroundResource(R.drawable.rounded_error);
                        email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_icon, 0,R.drawable.error_icon, 0);
                        email_error.setText("email is already in use!");
                        hasError = true;
                    }
                }else{
                    email.setBackgroundResource(R.drawable.rounded_white);
                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user_icon, 0,0, 0);
                    email_error.setText("");
                    hasError = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        email.addTextChangedListener(emailKeyChecker);

        // -- TextWatcher for onKeyUp validation of pass

        TextWatcher passKeyChecker = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // put validation here
                if(!password.getText().toString().trim().equals("")){
                    if(!validatePass(password.getText().toString())){
                        password.setBackgroundResource(R.drawable.rounded_error);
                        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key_icon, 0,R.drawable.error_icon, 0);
                        pass_error.setText("password should be 8-20 characters long, should have at least 1 letter and 1 number");
                        hasError = true;
                    }else{
                        password.setBackgroundResource(R.drawable.rounded_okay);
                        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key_icon, 0,R.drawable.okay_icon, 0);
                        pass_error.setText("");
                        hasError = false;
                    }
                }else{
                    password.setBackgroundResource(R.drawable.rounded_white);
                    password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.key_icon, 0,0, 0);
                    pass_error.setText("");
                    hasError = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        password.addTextChangedListener(passKeyChecker);

        TextWatcher confirmKeyChecker = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // put validation here
                if(!confirm_password.getText().toString().trim().equals("")){
                    if(!confirm_password.getText().toString().equals(password.getText().toString())){
                        confirm_password.setBackgroundResource(R.drawable.rounded_error);
                        confirm_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.asterisk_icon, 0,R.drawable.error_icon, 0);
                        confirm_error.setText("passwords do not match!");
                        hasError = true;
                    }else{
                        confirm_password.setBackgroundResource(R.drawable.rounded_okay);
                        confirm_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.asterisk_icon, 0,R.drawable.okay_icon, 0);
                        confirm_error.setText("");
                        hasError = false;
                    }
                }else{
                    confirm_password.setBackgroundResource(R.drawable.rounded_white);
                    confirm_password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.asterisk_icon, 0,0, 0);
                    confirm_error.setText("");
                    hasError = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        confirm_password.addTextChangedListener(confirmKeyChecker);

        TextWatcher firstnameKeyChecker = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // put validation here
                if(!firstname.getText().toString().trim().equals("")){
                    firstname.setBackgroundResource(R.drawable.rounded_okay);
                    firstname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.f_icon, 0,R.drawable.okay_icon, 0);
                    hasError = false;
                }else{
                    firstname.setBackgroundResource(R.drawable.rounded_error);
                    firstname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.f_icon, 0,R.drawable.error_icon, 0);
                    hasError = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        firstname.addTextChangedListener(firstnameKeyChecker);

        TextWatcher lastnameKeyChecker = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // put validation here
                if(!lastname.getText().toString().trim().equals("")){
                    lastname.setBackgroundResource(R.drawable.rounded_okay);
                    lastname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.l_icon, 0,R.drawable.okay_icon, 0);
                    hasError = false;
                }else{
                    lastname.setBackgroundResource(R.drawable.rounded_error);
                    lastname.setCompoundDrawablesWithIntrinsicBounds(R.drawable.l_icon, 0,R.drawable.error_icon, 0);
                    hasError = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        lastname.addTextChangedListener(lastnameKeyChecker);

        TextWatcher ageKeyChecker = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // put validation here
                if(!age.getText().toString().trim().equals("")){
                    age.setBackgroundResource(R.drawable.rounded_okay);
                    age.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cake_icon, 0,R.drawable.okay_icon, 0);
                    hasError = false;
                }else{
                    age.setBackgroundResource(R.drawable.rounded_error);
                    age.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cake_icon, 0,R.drawable.error_icon, 0);
                    hasError = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        age.addTextChangedListener(ageKeyChecker);

        db.closeDB();
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePass(String passStr) {
        Matcher matcher = VALID_PASSWORD_REGEX .matcher(passStr);
        return matcher.find();
    }
}
