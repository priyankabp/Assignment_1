package com.example.android.assignment_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.assignment_1.utils.Utils;


public class LandingScreen extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        textView = (TextView) findViewById(R.id.landing_screen_textView);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setText(getIntent().getStringExtra(Utils.MSG_KEY_INTENT));
    }
}
