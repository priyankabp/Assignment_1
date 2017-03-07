package com.example.android.assignment_1;

import java.security.SecureRandom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManagement {

    // Shared Preferences reference
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String SHARED_PREF_FILENAME = "UserRegister";

    // Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Username
    public static final String USERNAME = "username";

    //session id
    public static Integer sessionId;

    // Constructor
    public UserSessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILENAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public Integer generateSessionId() {
        SecureRandom random = new SecureRandom();
        Integer number = 10000 + random.nextInt(100000);
        return number;
    }

    //Create login session
    public String createUserLoginSession(String name) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(USERNAME, name);

        // commit changes
        editor.commit();

        sessionId = generateSessionId();
        return name;
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to SignInPage Activity
            Intent i = new Intent(context, SignInPage.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring SignInPage Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }

    /**
     * Get stored session data
     */
    public String getUserDetails() {

        // return user
        return sharedPreferences.getString(USERNAME, null);
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity Activity
        Intent i = new Intent(context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring MainActivity Activity
        context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN, false);
    }
}
