package com.example.android.assignment_1.utils;

import android.provider.BaseColumns;


public class StudentContract {

    // To prevent someone from accidentally instantiating the Student Contract class,
    // give it an empty constructor.
    private StudentContract() {
    }

    /**
     * Inner class that defines constant values for the students database table.
     * Each entry in the table represents a single student.
     */
    public static final class StudentEntry implements BaseColumns {

        /**
         * Name of database table for students
         */
        public final static String TABLE_NAME = "students";

        /**
         * Unique Username of the student.
         * Type: TEXT
         */
        public final static String COLUMN_USERNAME = "username";

        /**
         * Name of the student.
         * Type: TEXT
         */
        public final static String COLUMN_NAME = "name";

        /**
         * Major of the student.
         * Type: TEXT
         */
        public final static String COLUMN_MAJOR = "major";

        /**
         * MSU_Email of the student.
         * Type: INTEGER
         */
        public final static String COLUMN_MSU_EMAIL = "msu_email";

        /**
         * DateOfBirth of the student.
         * Type: TEXT
         */
        public final static String COLUMN_DATE_OF_BIRTH = "dob";

        /**
         * Password of the student.
         * Type: TEXT
         */
        public final static String COLUMN_PASSWORD = "password";

    }
}
