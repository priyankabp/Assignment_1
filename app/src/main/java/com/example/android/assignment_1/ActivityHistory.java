/*
* This class is used to display the activity history of the user.
*
* Whenever the user visits any activity the visited activity log
* is updated in the shared pref file.
*/

package com.example.android.assignment_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import android.widget.TextView;


import static com.example.android.assignment_1.UserSessionManagement.SHARED_PREF_FILENAME;

public class ActivityHistory extends AppCompatActivity {

    //User session management class
    UserSessionManagement session;

    //Activty tracker class
    ActivityTracker activityTracker;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private TextView textView;
    private String logInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sharedPreferences = getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_APPEND);
        editor = sharedPreferences.edit();

        // Session class instance
        session = new UserSessionManagement(getApplicationContext());
        logInUser = session.getUserDetails();

        textView = (TextView) findViewById(R.id.activity_history_textView);

        //activity tracker to update the visited activity in the shared pref file
        activityTracker = new ActivityTracker(getApplicationContext(), logInUser);
        activityTracker.updateActivity(logInUser + " moved to Browsing History page!");

        //method to display the activity hsitory
        loadActivityHistory();
    }

    private void loadActivityHistory() {
        // String "activity" stores the activity history
        String activity = ActivityTracker.getListFromSP(getApplicationContext(), logInUser, "Activity_");
        activity += "\n";
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(activity);


    }


}
