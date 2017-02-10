package com.example.android.assignment_1;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegistrationPage extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        EditText dateText = (EditText) findViewById(R.id.Registration_DoB);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegistrationPage.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        EditText dateText = (EditText) findViewById(R.id.Registration_DoB);
        dateText.setText(simpleDateFormat.format(calendar.getTime()));
    }


    public void onRegisterClick(View view) {

        EditText regUsername = (EditText) findViewById(R.id.Registration_UserName);
        if (TextUtils.isEmpty(regUsername.getText().toString())) {
            regUsername.setError("Please enter a username.");
        }

        EditText regName = (EditText) findViewById(R.id.Registration_SName);
        if (TextUtils.isEmpty(regName.getText().toString())) {
            regName.setError("Please enter your name.");
        }

        EditText regMajor = (EditText) findViewById(R.id.Registration_SMajor);
        if (TextUtils.isEmpty(regMajor.getText().toString())) {
            regMajor.setError("Please enter your major.");
        }

        EditText regEmail = (EditText) findViewById(R.id.Registration_Email);
        String email = regEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(regEmail.getText().toString()) || !(email.matches(emailPattern))) {
            regEmail.setError("Invalid email ID. It should have montclair.edu");
        }

        EditText regDoB = (EditText) findViewById(R.id.Registration_DoB);
        if (TextUtils.isEmpty(regDoB.getText().toString())) {
            regDoB.setError("Please select your date of birth.");
        }


        EditText passwordEditText1 = (EditText) findViewById(R.id.Registration_password1);
        String password1 = passwordEditText1.getText().toString();
        if (TextUtils.isEmpty(password1) || password1.length() <= 6) {
            passwordEditText1.setError("You must have minimum 6 characters in your password");
            return;

        }

        EditText passwordEditText2 = (EditText) findViewById(R.id.Registration_password2);
        String password2 = passwordEditText2.getText().toString();
        if (!(password2.equals(password1))) {
            passwordEditText2.setError("Password do not match");
            return;
        }
        if (view.getId() == R.id.Registration_button) {
            Intent intent = new Intent(RegistrationPage.this, LandingScreen.class);
            startActivity(intent);
        }
    }
}
