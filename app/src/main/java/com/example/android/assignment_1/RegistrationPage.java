/*
* This is the Registration Activity.
* Allows User to register for MSU.
* After registration navigates to LandingScreen Activity.
* */
package com.example.android.assignment_1;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
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

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.widget.AdapterView.OnItemSelectedListener;


import com.example.android.assignment_1.utils.StudentContract.StudentEntry;
import com.example.android.assignment_1.utils.StudentDbHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class RegistrationPage extends AppCompatActivity implements OnItemSelectedListener {

    private StudentDbHelper dbHelper;
    UserSessionManagement session;
    ActivityTracker activityTracker;

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

        // UserSession Manager
        session = new UserSessionManagement(getApplicationContext());

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

    public boolean validateDoB(String dob) {

        Pattern pattern;
        Matcher matcher;
        final String dateOfBirthPattern = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
        pattern = Pattern.compile(dateOfBirthPattern);


        matcher = pattern.matcher(dob);

        if (matcher.matches()) {
            matcher.reset();

            if (matcher.find()) {
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&
                        (month.equals("4") || month.equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month.equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // method to update the selected date from calender on the field.
    private void updateDoB() {

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        regDoB.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void clearErrors() {
        regUsername.setError(null);
        regName.setError(null);
        regEmail.setError(null);
        regDoB.setError(null);
        regPassword1.setError(null);
        regPassword2.setError(null);
    }


    public boolean verifyData(String regUsernameStr, String regNameStr, String regEmailStr, String regDoBStr, String regPassword1Str, String regPassword2Str) {
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
        String emailPattern = "[a-zA-Z0-9._-]+@(montclair)\\.edu";
        if (TextUtils.isEmpty(regEmailStr) || !(regEmailStr.matches(emailPattern))) {
            regEmail.setError("Email ID must contain montclair.edu");
            isValid = false;
        }

        // DoB validation
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        if (TextUtils.isEmpty(regDoBStr) | !validateDoB(regDoBStr) | regDoBStr.compareTo((dateFormat.format(date)).toString()) >= 0) {

            regDoB.setError("Please select valid date of birth");
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

    public boolean verifyUserExist(String uName) {

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

    public long writeUserDetails(String regUsernameStr, String regNameStr, String regMajorStr, String regEmailStr, String regDoBStr, String regPassword1Str) {

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

        clearErrors();
        if (verifyData(regUsernameStr, regNameStr, regEmailStr, regDoBStr, regPassword1Str, regPassword2Str)) {

            if (view.getId() == R.id.Registration_button) {

                //after receiving the result of the query moves the cursor to first row of the result and checks the received data
                if (verifyUserExist(regUsernameStr)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage("Username already exits!");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();

                } else {

                    if (writeUserDetails(regUsernameStr, regNameStr, regMajorStr, regEmailStr, regDoBStr, regPassword1Str) == -1) {
                        // If the row ID is -1, then there was an error with insertion.
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setMessage("Error with saving student details");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                    } else {
                        session.createUserLoginSession(regUsernameStr);
                        activityTracker = new ActivityTracker(getApplicationContext(), regUsernameStr);
                        activityTracker.updateActivity(regUsernameStr + " registered!");
                        // Student Registration is successful after passing all the validations
                        Intent intent = new Intent(RegistrationPage.this, LandingScreen.class);
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
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Left" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
                this.finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

