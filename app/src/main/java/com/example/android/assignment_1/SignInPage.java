package com.example.android.assignment_1;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.assignment_1.utils.StudentContract.*;
import com.example.android.assignment_1.utils.StudentDbHelper;
import com.example.android.assignment_1.utils.Utils;


public class SignInPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

    }


    public void onLogInClick(View view) {

        Boolean isValid = true;

        // get the References of views
        EditText signIn_editText_Username = (EditText) findViewById(R.id.SignIn_editText_userName);
        EditText signIn_editText_Password = (EditText) findViewById(R.id.SignIn_editText_password);

        String signInUsername = signIn_editText_Username.getText().toString();
        String signInPassword = signIn_editText_Password.getText().toString();

        //Validates Username
        if (TextUtils.isEmpty(signInUsername)) {
            signIn_editText_Username.setError("Please enter a username.");
            isValid = false;
        }

        //Validates Password
        String password = signInPassword.trim();
        String passwordPattern = "(?=.*[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";
        if (TextUtils.isEmpty(signInPassword) || !(password.matches(passwordPattern))) {
            signIn_editText_Password.setError("Incorrect Password");
            isValid = false;
        }

        if (isValid) {
            // To access our database, we instantiate our subclass of SQLiteOpenHelper
            // and pass the context, which is the current activity.
            StudentDbHelper dbHelper = new StudentDbHelper(this);

            // Create and/or open a database to read from it
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            //builds query to match the entered password and the stored password in the database with unique username
            Cursor cursor = db.query(StudentEntry.TABLE_NAME,
                    new String[]{StudentEntry.COLUMN_PASSWORD},
                    " username = ?",
                    new String[]{signInUsername},
                    null,
                    null,
                    null,
                    null);

            //after receiving the result of the query moves the cursor to first row of the result and checks that received data
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                // if the signInPassword and the storedPassword are same the login is successful
                if (signInPassword.equals(cursor.getString(0))) {
                    Intent intent = new Intent(SignInPage.this, LandingScreen.class);
                    Toast.makeText(SignInPage.this, "Congrats: Login Successful", Toast.LENGTH_SHORT).show();
                    intent.putExtra(Utils.MSG_KEY_INTENT, "This account is for " + signInUsername + " !");
                    startActivity(intent);
                    // Always close the cursor when you're done reading from it. This releases all its
                    // resources and makes it invalid.
                    cursor.close();
                } else {
                    Toast.makeText(SignInPage.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                    // Always close the cursor when you're done reading from it. This releases all its
                    // resources and makes it invalid.
                    cursor.close();
                }
            } else {
                Toast.makeText(SignInPage.this, "User Name does not exist", Toast.LENGTH_LONG).show();
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }


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
