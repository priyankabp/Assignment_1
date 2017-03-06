package com.example.android.assignment_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static android.R.attr.key;
import static com.example.android.assignment_1.UserSessionManagement.USERNAME;

/**
 * Created by Bhushan on 3/5/2017.
 */

public class ActivityTracker {

    // Shared Preferences reference
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static Integer count=0;

    // Sharedpref file name
    private static final String SHARED_PREF_FILENAME = "UserActivity";

    // Constructor
    public ActivityTracker(Context context, String userName){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILENAME+"_"+userName, Context.MODE_APPEND);
        editor = sharedPreferences.edit();

    }

    public void updateActivity(String activity){
        //Using Shared Preference
        //editor.putString(KEY_TITLE,USERNAME);
        String currentDateTimeStr = DateFormat.getDateTimeInstance().format(new Date());
        editor.putString("Activity_" + activity + "at" + ActivityTracker.count++,currentDateTimeStr);
        editor.commit();
    }

    /*
     * Get stored session data
     */
    public static String[] getListFromSP(Context context, String fileName, String key){

        SharedPreferences sharedPreferences=context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Map<String, ?> map=sharedPreferences.getAll();
        ArrayList<String> lst= new ArrayList<>();
        for(String str:map.keySet()){
            if(str.startsWith(key))
                lst.add((String)map.get(str));
        }
        return lst.toArray(new String[lst.size()]);
    }
}
