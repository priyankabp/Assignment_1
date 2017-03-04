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

        dbHelper = new StudentDbHelper(this);
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

        Boolean isValid = true;
        // Username validation
        EditText regUsername = (EditText) findViewById(R.id.Registration_userName);
        if (TextUtils.isEmpty(regUsername.getText().toString())) {
            regUsername.setError("Please enter a username");
            isValid = false;
        }
        regUsername.getText().toString().trim();

        // Name validation
        EditText regName = (EditText) findViewById(R.id.Registration_name);
        if (TextUtils.isEmpty(regName.getText().toString())) {
            regName.setError("Please enter your name");
            isValid = false;
        }
        regName.getText().toString().trim();

        // Major
        Spinner spinner = (Spinner) findViewById(R.id.Registration_spinner);
        String regMajor = spinner.getSelectedItem().toString();
        regMajor.trim();

        // Email validation specific for montclair.edu
        EditText regEmail = (EditText) findViewById(R.id.Registration_email);
        String email = regEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(regEmail.getText().toString()) || !(email.matches(emailPattern))) {
            regEmail.setError("Email ID must contain montclair.edu");
            isValid = false;
        }
        regEmail.getText().toString().trim();

        // DoB validation
        EditText regDoB = (EditText) findViewById(R.id.Registration_DoB);
        if (TextUtils.isEmpty(regDoB.getText().toString())) {
            regDoB.setError("Please select your date of birth");
            isValid = false;
        }
        regDoB.getText().toString().trim();


        // Password validation, minimum 6 characters required
        EditText regPassword1 = (EditText) findViewById(R.id.Registration_password1);
        String password1 = regPassword1.getText().toString();
        String passwordPattern = "(?=.*[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";
        if (TextUtils.isEmpty(password1) || !(password1.matches(passwordPattern))) {
            regPassword1.setError("Password should include numbers and characters with min length 6");
            isValid = false;

        }
        regPassword1.getText().toString().trim();

        // Confirm Password validation,compare with first password
        EditText regPassword2 = (EditText) findViewById(R.id.Registration_password2);
        EditText regUserName = (EditText) findViewById(R.id.Registration_userName);
        String password2 = regPassword2.getText().toString();
        if (!(password2.equals(password1))) {
            regPassword2.setError("Passwords do not match");
            isValid = false;
        }

        if (isValid) {

        /*
         * After all the fields are validated correct as per required data,
         * The current inserted data is stored in the Sqlite database.
         * User is registered for MSU.
         * After registration navigates to LandingScreen Activity.
         * */
            if (view.getId() == R.id.Registration_button) {


                String regUsername__for_db = regUsername.getText().toString();

                // Create database helper
                StudentDbHelper dbHelper = new StudentDbHelper(this);

                // Gets the database in write mode
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //builds query to match the entered password and the stored password in the database with unique username
                Cursor cursor = db.query(StudentEntry.TABLE_NAME,
                        new String[]{StudentEntry.COLUMN_USERNAME},
                        " username = ?",
                        new String[]{regUsername__for_db},
                        null,
                        null,
                        null,
                        null);

                //after receiving the result of the query moves the cursor to first row of the result and checks the received data
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    // if the signInUsername and the storedUsername are same the login fails,as the username already exists
                    if (regUsername__for_db.equals(cursor.getString(0))) {
                        Toast.makeText(RegistrationPage.this, "Username already exits", Toast.LENGTH_LONG).show();
                        // Always close the cursor when you're done reading from it. This releases all its
                        // resources and makes it invalid.
                        cursor.close();
                    }
                } else {


                    // Create a ContentValues object where column names are the keys,
                    // and students attributes from the editor are the values.
                    ContentValues values = new ContentValues();
                    values.put(StudentEntry.COLUMN_USERNAME, regUsername.getText().toString());
                    values.put(StudentEntry.COLUMN_NAME, regName.getText().toString());
                    values.put(StudentEntry.COLUMN_MAJOR, regMajor);
                    values.put(StudentEntry.COLUMN_MSU_EMAIL, regEmail.getText().toString());
                    values.put(StudentEntry.COLUMN_DATE_OF_BIRTH, regDoB.getText().toString());
                    values.put(StudentEntry.COLUMN_PASSWORD, regPassword1.getText().toString());

                    // Insert a new row for student in the database, returning the ID of that new row.
                    long newRowId = db.insert(StudentEntry.TABLE_NAME, null, values);

                    // Show a toast message depending on whether or not the insertion was successful
                    if (newRowId == -1) {
                        // If the row ID is -1, then there was an error with insertion.
                        Toast.makeText(this, "Error with saving student details", Toast.LENGTH_SHORT).show();
                    } else {
                        // Student Registration is successful after passing all the validations
                        Intent intent = new Intent(RegistrationPage.this, LandingScreen.class);
                        Toast.makeText(this, "Registered Successfully" + newRowId, Toast.LENGTH_SHORT).show();
                        intent.putExtra(Utils.MSG_KEY_INTENT, "This account is for " + regUserName.getText() + " !");
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

