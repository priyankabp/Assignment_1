/*
* This is the Landing Screen Activity.
* Welcome page for the registered user.
* */
package com.example.android.assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.assignment_1.utils.Utils;


public class LandingScreen extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManagement session;
    ActivityTracker activityTracker;

    private TextView textView;
    private String logInUser;

    //onCreate method for Landing screen Activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        // Session class instance
        session = new UserSessionManagement(getApplicationContext());

        /*Intent in = getIntent();
        Bundle bu = in.getExtras();
        logInUser = bu.getString("signInUsername");*/

        logInUser = session.getUserDetails();

        //Sets the registered username.
        textView = (TextView) findViewById(R.id.landing_screen_textView);
        textView.setText("Logged in user: "+logInUser);

        activityTracker = new ActivityTracker(getApplicationContext(), logInUser);
        activityTracker.updateActivity(logInUser+" moved to Welcome page!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_landing_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Do nothing for now
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddNoteClick(MenuItem item) {
        Intent intent = new Intent(LandingScreen.this, AddNote.class);
        startActivity(intent);
    }

    public void onEditProfileClick(MenuItem item) {
        //Toast.makeText(this, logInUser, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LandingScreen.this, EditProfile.class);
        //intent.putExtra(Utils.MSG_KEY_INTENT, "Update profile for " + logInUser + " !");
        //intent.putExtra("signInUsername",logInUser);
        startActivity(intent);
    }

    public void onLogOutClick(View view) {
        activityTracker.updateActivity(logInUser+" signed out!");
        session.logoutUser();
    }
}
