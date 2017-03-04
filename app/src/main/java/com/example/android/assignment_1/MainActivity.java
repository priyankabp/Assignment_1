/*
* This is the Main Activity.
* Allows User to login.
* Provides navigation to Sign In Activity.
* Provides navigation to Registration Activity.
* */
package com.example.android.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finds ID of signIn button for on click event.
        findViewById(R.id.MA_signIn_button).setOnClickListener(new MyLsnr());

        //finds ID of register button for on click event.
        findViewById(R.id.MA_register_button).setOnClickListener(new MyLsnr());

    }

    /**
     * MyLsnr controls the onClick event of SignIn button and Register button.
     */
    class MyLsnr implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            //when Forgot Password is clicked,navigation from MainActivity to SignIn Activity.
            if (view.getId() == R.id.MA_signIn_button) {
                Intent intent = new Intent(MainActivity.this, SignInPage.class);
                startActivity(intent);
            }
            //when Register is clicked,navigation from MainActivity to Registration Activity.
            else if (view.getId() == R.id.MA_register_button) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
            }
        }
    }

}
