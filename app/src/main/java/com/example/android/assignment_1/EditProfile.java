package com.example.android.assignment_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.assignment_1.utils.StudentDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private StudentDbHelper dbHelper;
    Calendar calendar = Calendar.getInstance();
    //private TextView editProfile_textView;
    private String logInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent in = getIntent();
        Bundle bu = in.getExtras();
        logInUser = bu.getString("signInUsername");
        Toast.makeText(this, logInUser, Toast.LENGTH_LONG).show();

        //Sets the registered username.
        TextView editProfile_textView = (TextView) findViewById(R.id.EditProfile_userName);
        editProfile_textView.setText(logInUser);

        // Spinner element in the registration activity.
        Spinner spinner = (Spinner) findViewById(R.id.EditProfile_spinner);

        // Spinner click listener for selected item.
        spinner.setOnItemSelectedListener(EditProfile.this);


        // Creating arrayAdapter with predefined values using string array "major_array"
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // when double-clicked on editText of DoB the calender pop-up comes.
        EditText dateText = (EditText) findViewById(R.id.EditProfile_DoB);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProfile.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
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
        EditText dateText = (EditText) findViewById(R.id.EditProfile_DoB);
        dateText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
