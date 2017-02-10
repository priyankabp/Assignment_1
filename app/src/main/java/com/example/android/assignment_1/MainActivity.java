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

        findViewById(R.id.MA_forgotPassword_button).setOnClickListener(new MyLsnr());
        findViewById(R.id.MA_register_button).setOnClickListener(new MyLsnr());

    }

    class MyLsnr implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.MA_forgotPassword_button) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordPage.class);
                startActivity(intent);
            } else if (view.getId() == R.id.MA_register_button) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
            }
        }
    }

}
