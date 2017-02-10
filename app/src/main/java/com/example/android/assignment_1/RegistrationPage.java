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
    private String[] myMajors;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.Registration_spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(RegistrationPage.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select your major");
        categories.add("Accounting Major");
        categories.add("Chemistry Major");
        categories.add("Computer Science Major");
        categories.add("Information Technology Major");
        categories.add("Information Systems Major");
        categories.add("Electronics Major");
        categories.add("Marine Biology and Coastal Sciences Major");
        categories.add("Mathematics Major");
        categories.add("Physics Major ");
        categories.add("Sustainability Science Major");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


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

        EditText regUsername = (EditText) findViewById(Registration_userName);
        if (TextUtils.isEmpty(regUsername.getText().toString())) {
            regUsername.setError("Please enter a username.");
        }

        EditText regName = (EditText) findViewById(R.id.Registration_name);
        if (TextUtils.isEmpty(regName.getText().toString())) {
            regName.setError("Please enter your name.");
        }

        Spinner regMajor = (Spinner) findViewById(R.id.Registration_spinner);
        if (TextUtils.isEmpty(regMajor.getSelectedItem().toString()) || TextUtils.equals("Select your major", regMajor.getSelectedItem().toString())) {
            regMajor.setPrompt("Please select your major.");
        }
        /*EditText regMajor = (EditText) findViewById(R.id.Registration_major);
        if (TextUtils.isEmpty(regMajor.getText().toString())) {
            regMajor.setError("Please enter your major.");
        }*/

        EditText regEmail = (EditText) findViewById(R.id.Registration_email);
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
        EditText regUserName = (EditText) findViewById(Registration_userName);
        String password2 = passwordEditText2.getText().toString();
        if (!(password2.equals(password1))) {
            passwordEditText2.setError("Password do not match");
            return;
        }
        if (view.getId() == R.id.Registration_button) {
            Intent intent = new Intent(RegistrationPage.this, LandingScreen.class);
            intent.putExtra(Utils.MSG_KEY_INTENT, "This account is for " + regUserName.getText() + " !");
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        if (adapterView.getContext().equals("Select your major")) {
            Toast.makeText(adapterView.getContext(), "Please select your major", Toast.LENGTH_LONG).show();
        }

    }

}

