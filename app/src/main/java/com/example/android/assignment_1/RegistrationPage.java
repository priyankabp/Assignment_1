/*
* This is the Registration Activity.
* Allows User to register for MSU.
* After registration navigates to LandingScreen Activity.
* */
package com.example.android.assignment_1;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


import com.example.android.assignment_1.utils.StudentContract.StudentEntry;
import com.example.android.assignment_1.utils.StudentDbHelper;
import com.example.android.assignment_1.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class RegistrationPage extends AppCompatActivity implements OnItemSelectedListener {

    private StudentDbHelper dbHelper;
    private EditText regUsername;
    private EditText regName;
    private EditText regEmail;
    private EditText regDoB;
    private EditText regPassword1;
    private Spinner spinner;
    private EditText regPassword2;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create database helper
        dbHelper = new StudentDbHelper(this);

        setContentView(R.layout.activity_registration_page);

        regUsername = (EditText) findViewById(R.id.Registration_userName);
        regName = (EditText) findViewById(R.id.Registration_name);
        regEmail = (EditText) findViewById(R.id.Registration_email);
        regDoB = (EditText) findViewById(R.id.Registration_DoB);
        regPassword1 = (EditText) findViewById(R.id.Registration_password1);
        regPassword2 = (EditText) findViewById(R.id.Registration_password2);
        spinner = (Spinner) findViewById(R.id.Registration_spinner);

        // Spinner click listener for selected item.
        spinner.setOnItemSelectedListener(RegistrationPage.this);


        // Creating arrayAdapter with predefined values using string array "major_array"
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // when double-clicked on editText of DoB the calender pop-up comes.
        regDoB.setOnClickListener(new View.OnClickListener() {
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
        regDoB.setText(simpleDateFormat.format(calendar.getTime()));
    }


    public boolean verifyData(String regUsernameStr, String regNameStr, String regEmailStr, String regDoBStr, String regPassword1Str, String regPassword2Str){
        Boolean isValid = true;
        // Username validation
        if (TextUtils.isEmpty(regUsernameStr)) {
            regUsername.setError("Please enter a username");
            isValid = false;
        }

        // Name validation
        if (TextUtils.isEmpty(regNameStr)) {
            regName.setError("Please enter your name");
            isValid = false;
        }

        // Email validation specific for montclair.edu
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(regEmailStr) || !(regEmailStr.matches(emailPattern))) {
            regEmail.setError("Email ID must contain montclair.edu");
            isValid = false;
        }

        // DoB validation
        if (TextUtils.isEmpty(regDoBStr)) {
            regDoB.setError("Please select your date of birth");
            isValid = false;
        }

        // Password validation, minimum 6 characters required
        String passwordPattern = "(?=.*[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";
        if (TextUtils.isEmpty(regPassword1Str) || !(regPassword1Str.matches(passwordPattern))) {
            regPassword1.setError("Password should include numbers and characters with min length 6");
            isValid = false;

        }

        // Confirm Password validation,compare with first password
        if (!(regPassword2Str.equals(regPassword1Str))) {
            regPassword2.setError("Passwords do not match");
            isValid = false;
        }
        return isValid;
    }

    public boolean verifyUserExist(String uName){

        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //builds query to match the entered password and the stored password in the database with unique username
        Cursor cursor = db.query(StudentEntry.TABLE_NAME,
                new String[]{StudentEntry.COLUMN_USERNAME},
                " username = ?",
                new String[]{uName},
                null,
                null,
                null,
                null);

        //after receiving the result of the query moves the cursor to first row of the result and checks the received data
        if (cursor != null && cursor.getCount() > 0)
            return true;
        cursor.close();
        db.close();
        return false;

    }

    public long writeUserDetails(String regUsernameStr, String regNameStr, String regMajorStr, String regEmailStr, String regDoBStr, String regPassword1Str){

        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and students attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_USERNAME, regUsernameStr);
        values.put(StudentEntry.COLUMN_NAME, regNameStr);
        values.put(StudentEntry.COLUMN_MAJOR, regMajorStr);
        values.put(StudentEntry.COLUMN_MSU_EMAIL, regEmailStr);
        values.put(StudentEntry.COLUMN_DATE_OF_BIRTH, regDoBStr);
        values.put(StudentEntry.COLUMN_PASSWORD, regPassword1Str);

        // Insert a new row for student in the database, returning the ID of that new row.
        long newRowId = db.insert(StudentEntry.TABLE_NAME, null, values);

        db.close();
        return newRowId;
    }

    // Checks the validation of all the fields present in the registration page and allows the user to register.
    public void onRegisterClick(View view) {
        String regUsernameStr = regUsername.getText().toString().trim();
        String regNameStr = regName.getText().toString().trim();
        String regMajorStr = spinner.getSelectedItem().toString();
        String regEmailStr = regEmail.getText().toString().trim();
        String regDoBStr = regDoB.getText().toString().trim();
        String regPassword1Str = regPassword1.getText().toString().trim();
        String regPassword2Str = regPassword2.getText().toString().trim();

        if (verifyData(regUsernameStr, regNameStr, regEmailStr, regDoBStr, regPassword1Str, regPassword2Str)) {

            if (view.getId() == R.id.Registration_button) {

                //after receiving the result of the query moves the cursor to first row of the result and checks the received data
                if (verifyUserExist(regUsernameStr)) {
                        Toast.makeText(RegistrationPage.this, "Username already exits", Toast.LENGTH_LONG).show();
                        // Always close the cursor when you're done reading from it. This releases all its
                        // resources and makes it invalid.

                } else {

                    // Show a toast message depending on whether or not the insertion was successful
                    if (writeUserDetails(regUsernameStr, regNameStr, regMajorStr, regEmailStr, regDoBStr, regPassword1Str) == -1) {
                        // If the row ID is -1, then there was an error with insertion.
                        Toast.makeText(this, "Error with saving student details", Toast.LENGTH_SHORT).show();
                    } else {
                        // Student Registration is successful after passing all the validations
                        Intent intent = new Intent(RegistrationPage.this, LandingScreen.class);
                        //Toast.makeText(this, "Registered Successfully" + newRowId, Toast.LENGTH_SHORT).show();
                        //intent.putExtra(Utils.MSG_KEY_INTENT, "This account is for " + regUsername.getText() + " !");
                        intent.putExtra("signInUsername",regUsernameStr);
                        startActivity(intent);
                    }
                }
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_registration_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Do nothing for now
                return true;
            // Respond to a click on the "Save" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
                this.finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

