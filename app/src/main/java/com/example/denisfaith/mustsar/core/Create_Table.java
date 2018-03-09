package com.example.denisfaith.mustsar.core;

import com.example.denisfaith.mustsar.utils.Constants;

/**
 * Created by john on 8/12/17.
 */

public class Create_Table {
    public abstract class create{
        //todo: queries to create the table
        public static final String CREATE_STAFF =
                "CREATE TABLE "+ Constants.config.TABLE_STAFF +" ("+ Constants.config.STAFF_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STAFF_FNAME+" TEXT,"+Constants.config.STAFF_LNAME+" TEXT, "+Constants.config.STAFF_ROLE+" TEXT, " +
                        " "+Constants.config.STAFF_USERNAME+" TEXT,"+Constants.config.STAFF_PASSWORD+" TEXT,"+Constants.config.STAFF_GENDER+" TEXT," +
                        " "+Constants.config.STAFF_STATUS+" INTEGER , "+Constants.config.IMEI+" TEXT,"+Constants.config.STAFF_PHONE+" TEXT );";

        public static final String CREATE_REGISTRATION =
                "CREATE TABLE "+ Constants.config.TABLE_REGISTRATION +" ("+ Constants.config.REGISTRATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.REGISTRATION_DATETIME+" TEXT,"+Constants.config.STUDENT_USERNAME+" TEXT, "+Constants.config.STUDENT_PASSWORD+" TEXT," +
                        " "+Constants.config.STUDENT_ID+" INTEGER, "+Constants.config.RESPONSIBILITY_STATUS+" INTEGER );";

        public static final String CREATE_STUDENT =
                "CREATE TABLE "+ Constants.config.TABLE_STUDENT +" ("+ Constants.config.STUDENT_FNAME+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STUDENT_LNAME+" TEXT,"+Constants.config.STUDENT_PHOTO+" TEXT, "+Constants.config.STUDENT_YEAR+" TEXT, " +
                        " "+Constants.config.STUDENT_SEMESTER+" TEXT,"+Constants.config.STUDENT_GENDER+" TEXT,"+Constants.config.PROGRAM_ID+" INTEGER," +
                        " "+Constants.config.STUDENT_STATUS+" INTEGER);";

        public static final String CREATE_COURSE =
                "CREATE TABLE "+ Constants.config.TABLE_COURSE +" ("+ Constants.config.COURSE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.COURSE_NAME+" TEXT,"+Constants.config.COURSE_CODE+" TEXT,"+Constants.config.COURSE_STATUS+" INTEGER);";


        public static final String CREATE_DEPARTMENT =
                "CREATE TABLE "+ Constants.config.TABLE_DEPARTMENT +" ("+ Constants.config.DEPARTMENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.DEPARTMENT_NAME+" TEXT,"+Constants.config.DEPARTMENT_STATUS+" INTEGER,"+Constants.config.FACULTY_ID+" INTEGER );";

        public static final String CREATE_FACULTY =
                "CREATE TABLE "+ Constants.config.TABLE_FACULTY +" ("+ Constants.config.FACULTY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.FACULTY_NAME+" TEXT, "+Constants.config.FACULTY_STATUS+" INTEGER );";


        public static final String CREATE_RESPONSIBILY =
                "CREATE TABLE "+ Constants.config.TABLE_RESPONSIBILITY +" ("+ Constants.config.RESPONSIBILITY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.RESPONSIBILITY_NAME+" TEXT, "+Constants.config.RESPONSIBILITY_STATUS+" INTEGER );";

        public static final String CREATE_ROOM =
                "CREATE TABLE "+ Constants.config.TABLE_ROOM +" ("+ Constants.config.ROOM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.ROOM_NAME+" TEXT, "+Constants.config.BUILDING_NAME+" TEXT,"+Constants.config.ROOM_STATUS+" INTEGER);";


        public static final String CREATE_PROGRAM =
                "CREATE TABLE "+ Constants.config.TABLE_PROGRAM +" ("+ Constants.config.PROGRAM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.PROGRAM_NAME+" TEXT, "+Constants.config.PROGRAM_DURATION+" INTEGER,"+Constants.config.PROGRAM_STATUS+" INTEGER);";

        public static final String CREATE_DEP_STAFF =
                "CREATE TABLE "+ Constants.config.TABLE_DEP_STAFF +" ("+ Constants.config.DEP_STAFF_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.STAFF_ID+" INTEGER,"+Constants.config.DEPARTMENT_ID+" INTEGER, "+Constants.config.DEP_STATUS+" INTEGER);";

        public static final String CREATE_PCOURSE =
                "CREATE TABLE "+ Constants.config.TABLE_PCOURSE +" ("+ Constants.config.PCOURSE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " "+Constants.config.COURSE_ID+" TEXT, "+Constants.config.PROGRAM_ID+" INTEGER,"+Constants.config.PCOURSE_STATUS+" INTEGER);";

        public abstract void start(String name);
    }
}
