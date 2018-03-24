package com.example.denisfaith.mustsar.core;

/**
 * Created by john on 7/8/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.denisfaith.mustsar.utils.DateTime;

import java.util.HashMap;

import static com.example.denisfaith.mustsar.utils.Constants.config.LOGIN_DATE;
import static com.example.denisfaith.mustsar.utils.Constants.config.LOGIN_TIME;
import static com.example.denisfaith.mustsar.utils.Constants.config.PROGRAM_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_FNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_GENDER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_LNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_PASSWORD;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_PHONE;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_ROLE;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_USERNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_FNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_GENDER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_LNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_PASSWORD;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_PHOTO;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_REGNUMBER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_SEMESTER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_YEAR;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "HBB_USER_Pref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN_STUDENT = "IsLoggedIn_student";
    private static final String IS_LOGIN_STAFF = "IsLoggedIn_staff";

    public static final String KEY_SESSION_TYPE = "user";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn_staff()){
            // user is not logged in redirect him to Login Activity
            //Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            //_context.startActivity(i);
        }
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn_staff(){
        return pref.getBoolean(IS_LOGIN_STAFF, false);
    }
    public boolean isLoggedIn_student(){
        return pref.getBoolean(IS_LOGIN_STUDENT, false);
    }

    public void loginStaff(int useID, String fname, String lname, String username, String password,String phone, String gender, String role, String salary) {
        // Storing login value as TRUE
        if(isLoggedIn_staff() || isLoggedIn_student()){
            logoutUser();
        }
        editor.putString(STAFF_FNAME, fname);
        editor.putString(STAFF_LNAME, lname);
        editor.putString(STAFF_USERNAME, username);
        editor.putString(STAFF_PASSWORD, password);
        editor.putString(STAFF_ID, String.valueOf(useID));
        editor.putString(STAFF_PHONE, phone);
        editor.putString(STAFF_ROLE, role);
        editor.putString(STAFF_GENDER, gender);
        editor.putString(LOGIN_DATE, DateTime.getCurrentDate());
        editor.putString(LOGIN_TIME, DateTime.getCurrentTime());
        editor.putBoolean(IS_LOGIN_STAFF, true);
         // commit changes
        editor.commit();
    }
    public void loginStudent(int useID,int program_id, String fname, String lname, String photo,String year, String regno, String gender) {
        // Storing login value as TRUE
        if(isLoggedIn_staff() || isLoggedIn_student()){
            logoutUser();
        }
        editor.putString(STUDENT_FNAME, fname);
        editor.putString(STUDENT_LNAME, lname);
        editor.putString(STUDENT_PHOTO, photo);
        editor.putString(STUDENT_YEAR, year);
        editor.putString(STUDENT_ID, String.valueOf(useID));
        editor.putString(PROGRAM_ID, String.valueOf(program_id));
        //editor.putString(STUDENT_SEMESTER, semester);
        editor.putString(STUDENT_GENDER, gender);
        //editor.putString(STUDENT_REGNUMBER, semester);
        editor.putString(STUDENT_PASSWORD, gender);
        editor.putString(LOGIN_DATE, DateTime.getCurrentDate());
        editor.putString(LOGIN_TIME, DateTime.getCurrentTime());
        editor.putBoolean(IS_LOGIN_STUDENT, true);
        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getStaff(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(STAFF_FNAME, pref.getString(STAFF_FNAME, null));
        user.put(STAFF_LNAME, pref.getString(STAFF_LNAME, null));
        user.put(STAFF_USERNAME, pref.getString(STAFF_USERNAME, null));
        user.put(STAFF_PASSWORD, pref.getString(STAFF_PASSWORD, null));
        user.put(STAFF_GENDER, pref.getString(STAFF_GENDER, null));
        user.put(STAFF_PHONE, pref.getString(STAFF_PHONE, null));
        user.put(STAFF_ROLE, pref.getString(STAFF_ROLE, null));
        user.put(STAFF_ID, pref.getString(STAFF_ID, null));
        user.put(LOGIN_DATE, pref.getString(LOGIN_DATE, null));
        user.put(LOGIN_TIME, pref.getString(LOGIN_TIME, null));
        // return user
        return user;
    }

    public HashMap<String, String> getStudent(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(STUDENT_FNAME, pref.getString(STUDENT_FNAME, null));
        user.put(STUDENT_LNAME, pref.getString(STUDENT_LNAME, null));
        user.put(STUDENT_PHOTO, pref.getString(STUDENT_PHOTO, null));
        user.put(STUDENT_YEAR, pref.getString(STUDENT_YEAR, null));
        user.put(STUDENT_ID, pref.getString(STUDENT_ID, null));
        user.put(PROGRAM_ID, pref.getString(PROGRAM_ID, null));
        user.put(STUDENT_SEMESTER, pref.getString(STUDENT_SEMESTER, null));
        user.put(STUDENT_GENDER, pref.getString(STUDENT_GENDER, null));

       // user.put(STUDENT_USERNAME, pref.getString(STUDENT_USERNAME, null));
        user.put(STUDENT_PASSWORD, pref.getString(STUDENT_PASSWORD, null));
        user.put(LOGIN_DATE, pref.getString(LOGIN_DATE, null));
        user.put(LOGIN_TIME, pref.getString(LOGIN_TIME, null));
        // return user
        return user;
    }
}
