package com.example.android.assignment_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import static com.example.android.assignment_1.UserSessionManagement.SHARED_PREF_FILENAME;

public class ActivityHistory extends AppCompatActivity {

    UserSessionManagement session;
    ActivityTracker activityTracker;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private TextView textView;
    private String logInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sharedPreferences=getSharedPreferences(SHARED_PREF_FILENAME+"_"+logInUser, Context.MODE_APPEND);
        editor=sharedPreferences.edit();



        SharedPreferences sharedPreferences=getApplication().getSharedPreferences(SHARED_PREF_FILENAME+"_"+logInUser,
                Context.MODE_PRIVATE);
        Map<String, ?> map=sharedPreferences.getAll();

        ArrayList<String> lst= new ArrayList<>();
        for(String str:map.keySet()){
            if(str.startsWith(logInUser))
                lst.add((String)map.get(str));
        }

        StringBuilder sb = new StringBuilder();
        for (String s : map.keySet())
        {
            Toast.makeText(getApplicationContext(), (String)map.get(s), Toast.LENGTH_LONG).show();
            lst.add((String)map.get(s));
            sb.append(s);
            sb.append("\t");
        }

        System.out.println(sb.toString());
        Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
        ActivityTracker.getListFromSP(getApplicationContext(),SHARED_PREF_FILENAME+"_"+logInUser,logInUser);
    }
}
