package com.example.android.assignment_1;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;


public class ActivityTracker {

    // Shared Preferences reference
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static Integer count = 0;

    // Sharedpref file name
    private static final String SHARED_ACTIVITY_FILENAME = "UserActivity";

    // Constructor
    public ActivityTracker(Context context, String userName) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_ACTIVITY_FILENAME + "_" + userName, Context.MODE_APPEND);
        editor = sharedPreferences.edit();

    }

    public void updateActivity(String activity) {
        //Using Shared Preference
        String currentDateTimeStr = DateFormat.getDateTimeInstance().format(new Date());
        editor.putString("Activity_" + ActivityTracker.count++, currentDateTimeStr + ": " + activity);
        editor.commit();
    }

    /*
     * Get stored session data
     */
    public static String getListFromSP(Context context, String userName, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_ACTIVITY_FILENAME + "_" + userName,
                Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        ArrayList<String> lst = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (String str : map.keySet()) {
            if (str.startsWith(key))
                lst.add((String) map.get(str));
        }

        Collections.sort(lst, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        for (String str : lst) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(str);
        }

        return builder.toString();
    }
}
