/*
* This is the Forgot Password Activity.
* Sends email to user with password.
* */
package com.example.android.assignment_1;

import android.content.Context;
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
import android.widget.Toast;

import com.example.android.assignment_1.utils.StudentContract;
import com.example.android.assignment_1.utils.StudentDbHelper;
import com.example.android.assignment_1.utils.Utils;

import static com.example.android.assignment_1.R.id.forgotPassword_email;

public class ForgotPasswordPage extends AppCompatActivity {

    // onCreate method for Forgot Password Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);
    }


    //
    public void onSubmitClick(View view) {

        Boolean isValid = true;

        //Validates Username
        EditText forgotPassword_editText_Username = (EditText) findViewById(R.id.forgotPassword_username);
        EditText forgotPassword_editText_email = (EditText) findViewById(R.id.forgotPassword_email);

        String forgotPassword_Username = forgotPassword_editText_Username.getText().toString();
        String forgotPassword_email = forgotPassword_editText_email.getText().toString();


        if (TextUtils.isEmpty(forgotPassword_Username)) {
            forgotPassword_editText_Username.setError("Please enter a username.");
            isValid = false;
        }

        //Validates Password

        String email = forgotPassword_email.trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[m,o,n,t,c,l,a,i,r]+\\.+[e,d,u]+";
        if (TextUtils.isEmpty(forgotPassword_email) || !(email.matches(emailPattern))) {
            forgotPassword_editText_email.setError("Invalid email ID. It should have montclair.edu");
            isValid = false;
        }

        if (isValid) {

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
                    " username = ? AND msu_email = ?" ,
                    new String[]{forgotPassword_Username,forgotPassword_email},
                    null,
                    null,
                    null,
                    null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                // if the signInPassword and the storedPassword are same the login is successful
                Intent intent = new Intent(ForgotPasswordPage.this, DisplayPassword.class);
                intent.putExtra(Utils.MSG_KEY_INTENT, "Your password is : " + cursor.getString(0));
                startActivity(intent);

                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("User not found");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Ok",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog , int which){
                    }
                });
                alertDialog.show();

                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }
        }


    }
}
