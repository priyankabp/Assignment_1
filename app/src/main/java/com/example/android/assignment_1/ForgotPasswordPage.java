/*
* This is the Forgot Password Activity.
* Sends email to user with password.
* */
package com.example.android.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordPage extends AppCompatActivity {

    // onCreate method for Forgot Password Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);
    }


    //
    public void onSubmitClick(View view) {

        //Validates Username
        EditText forgotPassword_Username = (EditText) findViewById(R.id.forgotPassword_username);
        if (TextUtils.isEmpty(forgotPassword_Username.getText().toString())) {
            forgotPassword_Username.setError("Please enter a username.");
            return;
        }

        //Validates Password
        EditText forgotPassword_email = (EditText) findViewById(R.id.forgotPassword_email);
        String email = forgotPassword_email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(forgotPassword_email.getText().toString()) || !(email.matches(emailPattern))) {
            forgotPassword_email.setError("Invalid email ID. It should have montclair.edu");
            return;
        }

        //After validating the username and email shows notification that password is sent to email.
        if (view.getId() == R.id.forgotPassword_submit_button) {
            Intent intent = new Intent(ForgotPasswordPage.this, MainActivity.class);
            Toast.makeText(getApplicationContext(), "Password sent to your email.", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }
}
