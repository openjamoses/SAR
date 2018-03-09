package com.example.denisfaith.mustsar.core;

import android.content.Context;

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
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_PHOTO;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_SEMESTER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_YEAR;

/**
 * Created by john on 3/8/18.
 */

public class StudentDetails {
    private String fname, lname, gender, year, semester,photo,date,time;
    private int id,program_id;
    public StudentDetails(Context context){
        SessionManager sessionManager = new SessionManager(context);
        if(sessionManager.isLoggedIn_student()) {
            HashMap<String, String> user = sessionManager.getStudent();
            fname = user.get(STUDENT_FNAME);
            lname = user.get(STUDENT_LNAME);
            year = user.get(STUDENT_YEAR);
            gender = user.get(STUDENT_GENDER);
            semester = user.get(STUDENT_SEMESTER);
            photo = user.get(STUDENT_PHOTO);
            date = user.get(LOGIN_DATE);
            time = user.get(LOGIN_TIME);
            id = Integer.parseInt(user.get(STUDENT_ID));
            program_id = Integer.parseInt(user.get(PROGRAM_ID));
        }
    }
    public  String getFname(){
        return fname;
    }
    public  String getLname(){
        return lname;
    }
    public  String getPhoto(){
        return photo;
    }
    public  String getGender(){
        return gender;
    }
    public  String getSemester(){
        return semester;
    }
    public   String getYear(){
        return year;
    }
    public  int getid(){
        return id;
    }
    public  int getProgram_id(){
        return program_id;
    }
}
