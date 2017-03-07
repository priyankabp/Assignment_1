/*
* This class is used to edit and update the user profile.
* */
package com.example.android.assignment_1;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.assignment_1.utils.StudentContract.*;
import com.example.android.assignment_1.utils.StudentDbHelper;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private StudentDbHelper dbHelper;
    // User Session Manager Class
    UserSessionManagement session;
    ActivityTracker activityTracker;

    Calendar calendar = Calendar.getInstance();
    private TextView editProfile_textView;
    private String logInUser;
    private Spinner spinner;
    private EditText dateText;
    private EditText editName;
    private EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        dbHelper = new StudentDbHelper(this);


        // Session class instance
        session = new UserSessionManagement(getApplicationContext());
        logInUser = session.getUserDetails();

        activityTracker = new ActivityTracker(getApplicationContext(), logInUser);
        activityTracker.updateActivity(logInUser + " moved to Edit Profile Page!");

        editProfile_textView = (TextView) findViewById(R.id.EditProfile_userName);
        editName = (EditText) findViewById(R.id.EditProfile_name);
        editEmail = (EditText) findViewById(R.id.EditProfile_email);

        // Spinner element in the registration activity.
        spinner = (Spinner) findViewById(R.id.EditProfile_spinner);

        // Spinner click listener for selected item.
        spinner.setOnItemSelectedListener(EditProfile.this);


        // Creating arrayAdapter with predefined values using string array "major_array"
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        // when double-clicked on editText of DoB the calender pop-up comes.
        dateText = (EditText) findViewById(R.id.EditProfile_DoB);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProfile.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        displayUserDetails(logInUser);

    }

    private void displayUserDetails(String logInUser) {

        //Sets the registered username.

        editProfile_textView.setText("Username: " + logInUser);

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //builds query to match the entered password and the stored password in the database with unique username
        Cursor cursor = db.query(StudentEntry.TABLE_NAME,
                new String[]{StudentEntry.COLUMN_NAME,
                        StudentEntry.COLUMN_MSU_EMAIL,
                        StudentEntry.COLUMN_MAJOR,
                        StudentEntry.COLUMN_DATE_OF_BIRTH},
                " username = ?",
                new String[]{logInUser},
                null,
                null,
                null,
                null);

        //after receiving the result of the query moves the cursor to first row of the result and checks that received data
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            editName.setText(cursor.getString(0));
            editEmail.setText(cursor.getString(1));

            String[] majorArray = getResources().getStringArray(R.array.major_array);
            Integer position = Arrays.asList(majorArray).indexOf(cursor.getString(2));
            spinner.setSelection(position);

            dateText.setText(cursor.getString(3));
        }
        cursor.close();
        db.close();
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
        dateText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean verifyData(String nameStr, String emailStr, String doBStr) {
        Boolean isValid = true;

        // Name validation
        if (TextUtils.isEmpty(nameStr)) {
            editName.setError("Please enter your name");
            isValid = false;
        }

        // Email validation specific for montclair.edu
        String emailPattern = "[a-zA-Z0-9._-]+@(montclair)\\.edu";
        if (TextUtils.isEmpty(emailStr) || !(emailStr.matches(emailPattern))) {
            editEmail.setError("Email ID must contain montclair.edu");
            isValid = false;
        }

        // DoB validation
        if (TextUtils.isEmpty(doBStr)) {
            dateText.setError("Please select your date of birth");
            isValid = false;
        }

        return isValid;
    }

    public void updateUserDetails(String userNameStr, String nameStr, String majorStr, String emailStr, String dobStr) {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_NAME, nameStr);
        values.put(StudentEntry.COLUMN_MAJOR, majorStr);
        values.put(StudentEntry.COLUMN_MSU_EMAIL, emailStr);
        values.put(StudentEntry.COLUMN_DATE_OF_BIRTH, dobStr);

        db.update(StudentEntry.TABLE_NAME, values, "username=?", new String[]{userNameStr});

        db.close();
    }

    public void onUpdateClick(View view) {
        String userNameStr = logInUser;
        String nameStr = editName.getText().toString().trim();

        String majorStr = spinner.getSelectedItem().toString();
        String emailStr = editEmail.getText().toString().trim();
        String dobStr = dateText.getText().toString().trim();

        if (verifyData(nameStr, emailStr, dobStr)) {
            updateUserDetails(userNameStr, nameStr, majorStr, emailStr, dobStr);

            Toast.makeText(this, "Values updated", Toast.LENGTH_SHORT).show();

            activityTracker.updateActivity(logInUser + " updated profile!");
            Intent intent = new Intent(this, LandingScreen.class);
            startActivity(intent);
        }

    }
}
