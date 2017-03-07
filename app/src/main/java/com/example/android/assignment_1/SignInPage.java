package com.example.android.assignment_1;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.assignment_1.utils.StudentContract.*;
import com.example.android.assignment_1.utils.StudentDbHelper;


public class SignInPage extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManagement session;
    // User Activity Tracker Class
    ActivityTracker activityTracker;

    private EditText signInUsername;
    private EditText signInPassword;
    private String signInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        // UserSession Manager
        session = new UserSessionManagement(getApplicationContext());

        signInUsername = (EditText) findViewById(R.id.SignIn_editText_userName);
        signInPassword = (EditText) findViewById(R.id.SignIn_editText_password);

    }

    public boolean verifyUserExist(String uName) {

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        StudentDbHelper dbHelper = new StudentDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //builds query to match the entered password and the stored password in the database with unique username
        Cursor cursor = db.query(StudentEntry.TABLE_NAME,
                new String[]{StudentEntry.COLUMN_PASSWORD},
                " username = ?",
                new String[]{uName},
                null,
                null,
                null,
                null);

        //after receiving the result of the query moves the cursor to first row of the result and checks that received data
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            if (signInPassword.equals(cursor.getString(0))) {

                session.createUserLoginSession(uName);

                activityTracker = new ActivityTracker(getApplicationContext(), uName);
                activityTracker.updateActivity(uName + " signed in!");

            }

            cursor.close();
            return true;
        }

        db.close();
        return false;

    }

    public boolean verifyData(String signInUsernameStr, String signInPasswordStr) {

        Boolean isValid = true;
        //Validates Username
        if (TextUtils.isEmpty(signInUsernameStr)) {
            signInUsername.setError("Please enter a username.");
            isValid = false;
        }

        //Validates Password
        String passwordPattern = "(?=.*[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";

        if (TextUtils.isEmpty(signInUsernameStr) || !(signInPasswordStr.matches(passwordPattern))) {

            signInPassword.setError("Incorrect Password");
            isValid = false;
        }

        return isValid;
    }

    public void onLogInClick(View view) {

        String signInUsernameStr = signInUsername.getText().toString().trim();
        String signInPasswordStr = signInPassword.getText().toString().trim();

        // validate username and password
        if (verifyData(signInUsernameStr, signInPasswordStr)) {

            if (view.getId() == R.id.SignIn_button_logIn) {

                // verify if the user exits
                if (verifyUserExist(signInUsernameStr)) {

                    Intent intent = new Intent(SignInPage.this, LandingScreen.class);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    //creates session for the logged in user
                    signInUser = session.createUserLoginSession(signInUsernameStr);

                    //updating the activity tracking file
                    activityTracker = new ActivityTracker(getApplicationContext(), signInUser);
                    activityTracker.updateActivity(signInUser + " signed in!");

                    startActivity(intent);

                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage("User Name or Password does not match");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();

                }
            }

        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("User Name does not exist");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            alertDialog.show();

        }
    }

    public void onForgotPasswordClick(View view) {

        Button linkToForgotPassword = (Button) findViewById(R.id.SignIn_button_linkToForgotPassword);
        // Link to Register Screen
        linkToForgotPassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        ForgotPasswordPage.class);
                startActivity(intent);
            }
        });

    }
}
