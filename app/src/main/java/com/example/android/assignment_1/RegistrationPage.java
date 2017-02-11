/*
* This is the Registration Activity.
* Allows User to register for MSU.
* After registration navigates to LandingScreen Activity.
* */
package com.example.android.assignment_1;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import com.example.android.assignment_1.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


import static com.example.android.assignment_1.R.id.Registration_userName;

public class RegistrationPage extends AppCompatActivity implements OnItemSelectedListener {

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);


        // Spinner element in the registration activity.
        Spinner spinner = (Spinner) findViewById(R.id.Registration_spinner);

        // Spinner click listener for selected item.
        spinner.setOnItemSelectedListener(RegistrationPage.this);


        // Creating arrayAdapter with predefined values using string array "major_array"
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // when double-clicked on editText of DoB the calender pop-up comes.
        EditText dateText = (EditText) findViewById(R.id.Registration_DoB);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegistrationPage.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    //Allows to select the DoB to user from calender and update it on the field.
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        //select the DoB from calender
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            //update the selected DoB on the field.
            updateDoB();
        }

    };

    // method to update the selected date from calender on the field.
    private void updateDoB() {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        EditText dateText = (EditText) findViewById(R.id.Registration_DoB);
        dateText.setText(simpleDateFormat.format(calendar.getTime()));
    }


    // Checks the validation of all the fields present in the registration page and allows the user to register.
    public void onRegisterClick(View view) {

        // Username validation
        EditText regUsername = (EditText) findViewById(Registration_userName);
        if (TextUtils.isEmpty(regUsername.getText().toString())) {
            regUsername.setError("Please enter a username");
        }

        // Name validation
        EditText regName = (EditText) findViewById(R.id.Registration_name);
        if (TextUtils.isEmpty(regName.getText().toString())) {
            regName.setError("Please enter your name");
        }

        // Email validation specific for montclair.edu
        EditText regEmail = (EditText) findViewById(R.id.Registration_email);
        String email = regEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(regEmail.getText().toString()) || !(email.matches(emailPattern))) {
            regEmail.setError("Email ID must contain montclair.edu");
        }

        // DoB validation
        EditText regDoB = (EditText) findViewById(R.id.Registration_DoB);
        if (TextUtils.isEmpty(regDoB.getText().toString())) {
            regDoB.setError("Please select your date of birth");
        }


        // Password validation, minimum 6 characters required
        EditText passwordEditText1 = (EditText) findViewById(R.id.Registration_password1);
        String password1 = passwordEditText1.getText().toString();
        if (TextUtils.isEmpty(password1) || password1.length() <= 6) {
            passwordEditText1.setError("Password must contain minimum 6 characters");
            return;

        }

        // Confirm Password validation,compare with first password
        EditText passwordEditText2 = (EditText) findViewById(R.id.Registration_password2);
        EditText regUserName = (EditText) findViewById(Registration_userName);
        String password2 = passwordEditText2.getText().toString();
        if (!(password2.equals(password1))) {
            passwordEditText2.setError("Passwords do not match");
            return;
        }

        /*
         * After all the fields are correct as per requirement ,registration is successful.
         * User is registered for MSU.
         * After registration navigates to LandingScreen Activity.
         * */
        if (view.getId() == R.id.Registration_button) {
            Intent intent = new Intent(RegistrationPage.this, LandingScreen.class);
            Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_LONG).show();
            intent.putExtra(Utils.MSG_KEY_INTENT, "This account is for " + regUserName.getText() + " !");
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //when item is selected,we can retrieve the selected item and notify
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //another callback
    }

}

