/*
* This class displays the password of the user.
* */
package com.example.android.assignment_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.assignment_1.utils.Utils;

public class DisplayPassword extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_password);

        //Displays the stored password to user.
        textView = (TextView) findViewById(R.id.displayPassword_textView);
        textView.setText(getIntent().getStringExtra(Utils.MSG_KEY_INTENT));
    }
}
