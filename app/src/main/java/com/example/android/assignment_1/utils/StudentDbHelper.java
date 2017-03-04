package com.example.android.assignment_1.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.assignment_1.utils.StudentContract.StudentEntry;

import static android.R.attr.id;



public class StudentDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Registration.db";

    public static final int DATABASE_VERSION = 1;

    public StudentDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the students table
        String SQL_CREATE_STUDENTS_TABLE = "CREATE TABLE " + StudentEntry.TABLE_NAME + " ("
                + StudentEntry.COLUMN_USERNAME + " TEXT PRIMARY KEY NOT NULL, "
                + StudentEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + StudentEntry.COLUMN_MAJOR + " TEXT NOT NULL, "
                + StudentEntry.COLUMN_MSU_EMAIL + " TEXT NOT NULL, "
                + StudentEntry.COLUMN_DATE_OF_BIRTH + " TEXT NOT NULL, "
                + StudentEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_STUDENTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

}
