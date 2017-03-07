/*
* This is the Forgot Password Activity.
* Displays the password to user on next activity.
* */
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
import android.widget.EditText;

import com.example.android.assignment_1.utils.StudentContract;
import com.example.android.assignment_1.utils.StudentDbHelper;
import com.example.android.assignment_1.utils.Utils;

import static com.example.android.assignment_1.R.id.forgotPassword_email;


public class ForgotPasswordPage extends AppCompatActivity {

    EditText forgotPasswordUsername;
    EditText forgotPasswordEmail;

    // onCreate method for Forgot Password Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        forgotPasswordUsername = (EditText) findViewById(R.id.forgotPassword_username);
        forgotPasswordEmail = (EditText) findViewById(forgotPassword_email);
    }

    //method validates of the entered username and email.
    public boolean verifyData(String forgotPasswordUsernameStr, String forgotPasswordEmailStr) {

        Boolean isValid = true;

        //validated username
        if (TextUtils.isEmpty(forgotPasswordUsernameStr)) {
            forgotPasswordEmail.setError("Please enter a username.");
            isValid = false;
        }

        //Validates Email
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(forgotPasswordEmailStr) || !(forgotPasswordEmailStr.matches(emailPattern))) {
            forgotPasswordEmail.setError("Invalid email ID. It should have montclair.edu");
            isValid = false;
        }
        return isValid;
    }

    //verifies if the user exits in the database
    public String verifyUserExist(String uName, String uEmail) {

        String passwordStr = "";
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        StudentDbHelper dbHelper = new StudentDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        /*Cursor query (boolean distinct,
        String table,
        String[] columns,
        String selection,
        String[] selectionArgs,
        String groupBy,
        String having,
        String orderBy,
        String limit)*/
        //builds query to match the entered password and the stored password in the database with unique username
        Cursor cursor = db.query(StudentContract.StudentEntry.TABLE_NAME,
                new String[]{StudentContract.StudentEntry.COLUMN_PASSWORD},
                " username = ? AND msu_email = ?",
                new String[]{uName, uEmail},
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            passwordStr = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return passwordStr;
    }


    // after the validation and verfying the username and email from the database displays the password
    public void onSubmitClick(View view) {

        String forgotPasswordUsernameStr = forgotPasswordUsername.getText().toString().trim();
        String forgotPasswordEmailStr = forgotPasswordEmail.getText().toString().trim();

        if (verifyData(forgotPasswordUsernameStr, forgotPasswordEmailStr)) {

            if (view.getId() == R.id.forgotPassword_submit_button) {

                String passwordStr = verifyUserExist(forgotPasswordUsernameStr, forgotPasswordEmailStr);
                if (passwordStr != "") {

                    // if the username and email match with the respective password
                    Intent intent = new Intent(ForgotPasswordPage.this, DisplayPassword.class);
                    intent.putExtra(Utils.MSG_KEY_INTENT, "Your password is : " + passwordStr);
                    startActivity(intent);


                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage("User not found");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();

                }
            }
        }
    }

}
