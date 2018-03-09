package com.example.denisfaith.mustsar.utils;
/**
 * Created by john on 7/8/17.
 */
public class Constants {
    public abstract class config{
        public static final String DB_NAME = "mustsar_db";
        public static final int DB_VERSION = 1;

        public static final int TOTAL_TABLES = 11;
        /****** URL DECLARATION ******************************/
        public static final String URL_PHONE = "http://192.168.43.18/";
        public static final String URL_CAMTECH = "http://192.168.1.119/";
        public static final String URL_SERVER = "http://173.255.219.164/";
        public static final String HOST_URL = URL_PHONE+ "MUST_SAR/";

        public static final String IMEI = "IMEI";
        public static final String APP_VERSION = "app_version";

        ///DB CONNECTIONS

        //// TODO: 10/12/17   STAFF
        public static final String STAFF_ID = "staff_id";
        public static final String TABLE_STAFF = "staff_tb";
        public static final String STAFF_FNAME = "staff_fname";
        public static final String STAFF_LNAME = "staff_lname";
        public static final String STAFF_ROLE = "staff_role";
        public static final String STAFF_USERNAME = "staff_username";
        public static final String STAFF_PASSWORD = "staff_password";
        public static final String STAFF_GENDER = "staff_gender";
        public static final String STAFF_PHONE = "staff_phone";
        public static final String STAFF_STATUS = "staff_status";


        //// TODO: 10/12/17   STUDENT
        public static final String STUDENT_ID = "student_id";
        public static final String TABLE_STUDENT = "student_tb";
        public static final String STUDENT_FNAME = "student_fname";
        public static final String STUDENT_LNAME = "student_lname";
        public static final String STUDENT_YEAR = "student_year";
        public static final String STUDENT_SEMESTER = "student_semester";
        public static final String STUDENT_PHOTO = "student_photo";
        public static final String STUDENT_GENDER = "student_gender";
        public static final String STUDENT_STATUS = "student_status";

        public static final String LOGIN_TIME = "login_time";
        public static final String LOGIN_DATE = "login_date";
        //// TODO: 10/12/17   COURSES
        public static final String COURSE_ID = "course_id";
        public static final String TABLE_COURSE = "course_tb";
        public static final String COURSE_NAME = "course_name";
        public static final String COURSE_CODE = "course_code";
        public static final String COURSE_STATUS = "course_status";


        //// TODO: 10/13/17 ROOMS
        public static final String ROOM_ID = "room_id";
        public static final String TABLE_ROOM = "room_tb";
        public static final String ROOM_NAME = "room_name";
        public static final String BUILDING_NAME = "building_name";
        public static final String ROOM_STATUS = "room_status";

        //// TODO: 10/13/17 DEPARTMENT
        public static final String DEPARTMENT_ID = "department_id";
        public static final String TABLE_DEPARTMENT = "department_tb";
        public static final String DEPARTMENT_NAME = "department_name";
        public static final String DEPARTMENT_STATUS = "department_status";
        //// TODO: 10/13/17 FACULTY
        public static final String FACULTY_ID = "faculty_id";
        public static final String TABLE_FACULTY = "faculty_tb";
        public static final String FACULTY_NAME = "faculty_name";
        public static final String FACULTY_STATUS = "faculty_status";
        ///TODO::: REGISTRATION
        public static final String REGISTRATION_ID = "registration_id";
        public static final String TABLE_REGISTRATION = "registration_tb";
        public static final String REGISTRATION_DATETIME = "registration_date";
        public static final String STUDENT_USERNAME = "student_username";
        public static final String STUDENT_PASSWORD = "student_password";
        //TODO::: PROGRAMS
        public static final String TABLE_PROGRAM = "program_tb";
        public static final String PROGRAM_ID = "program_id";
        public static final String PROGRAM_NAME = "program_name";
        public static final String PROGRAM_DURATION = "program_duration";
        public static final String PROGRAM_STATUS = "program_status";
        //// TODO: 11/10/17  PROGRAMCOURSE
        public static final String TABLE_PCOURSE = "pcourse_tb";
        public static final String PCOURSE_ID = "pcourse_id";
        public static final String PCOURSE_STATUS= "pcourse_status";
        //// TODO: 11/10/17  PROGRAMCOURSE
        public static final String TABLE_SCOURSE = "scourse_tb";
        public static final String SCOURSE_ID = "scourse_id";
        public static final String SCOURSE_STATUS= "scourse_status";
        //// TODO: 11/10/17  PROGRAMCOURSE
        public static final String TABLE_LCOURSE = "lcourse_tb";
        public static final String LCOURSE_ID = "lcourse_id";
        public static final String LCOURSE_STATUS= "lcourse_status";
        //// TODO: 11/10/17  PROGRAMCOURSE
        //// TODO: 11/10/17  RESPONSIBILITY
        public static final String TABLE_RESPONSIBILITY = "responsibility_tb";
        public static final String RESPONSIBILITY_ID = "responsibility_id";
        public static final String RESPONSIBILITY_NAME = "responsibility_name";
        public static final String RESPONSIBILITY_STATUS = "responsibility_status";

        //// TODO: 11/10/17  APPROVAL
        public static final String TABLE_DEP_STAFF = "dep_staff_tb";
        public static final String DEP_STAFF_ID = "dep_staff_id";
        public static final String DEP_STATUS = "dep_status";




        ///// TODO: 10/15/17    NUMBER OF FREE DEMO DAYS

        ///*********************** End of Operations ******************************/
        ///
        public static final String OPERATION_CASES = "cases";
        public static final String OPERATION_ACTIVATION = "activation";
        public static final String OPERATION_INSTALATION = "instalation";
        public static final String OPERATION_MANAGEMENT = "management";
        public static final String OPERATION_SUBSCRIPTION = "subscription";
        public static final String OPERATION_FEES = "fees";
        public static final String OPERATION_PAYMENTS = "payments";
        public static final String OPERATION_PAYPAL = "paypal";

        public static final String ACTION_ASSESSEMENT = "assessement";
        public static final String ACTION_HISTORY = "history";
        public static final String ACTION_SEARCH = "search";
        public static final String ACTION_ACTIVATE = "activate";

    }
}
