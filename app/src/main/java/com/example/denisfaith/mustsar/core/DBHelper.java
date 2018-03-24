package com.example.denisfaith.mustsar.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import com.example.denisfaith.mustsar.utils.Constants;

import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_CLASS;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_COURSE;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_DEPARTMENT;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_DEP_STAFF;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_FACULTY;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_LCOURSE;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_PCOURSE;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_PROGRAM;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_REGISTRATION;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_RESPONSIBILY;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_ROOM;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_STAFF;
import static com.example.denisfaith.mustsar.core.Create_Table.create.CREATE_STUDENT;

public class DBHelper extends SQLiteOpenHelper {

    private final Handler handler;
    private static DBHelper instance;

    public static synchronized DBHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new DBHelper(context);

        return instance;
    }

    public DBHelper(Context context) {
        super(context, Constants.config.DB_NAME, null, Constants.config.DB_VERSION);
        handler = new Handler(context.getMainLooper());
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO : Creating tables
        db.execSQL(CREATE_STAFF);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_REGISTRATION);
        db.execSQL(CREATE_FACULTY);
        db.execSQL(CREATE_DEPARTMENT);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_ROOM);
        db.execSQL(CREATE_DEP_STAFF);
        db.execSQL(CREATE_PCOURSE);
        db.execSQL(CREATE_LCOURSE);
        db.execSQL(CREATE_PROGRAM);
        db.execSQL(CREATE_CLASS);
        db.execSQL(CREATE_RESPONSIBILY);
        Log.e("DATABASE OPERATION",Constants.config.TOTAL_TABLES+" Tables  created / open successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Updating table here
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_REGISTRATION);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_DEPARTMENT);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_PCOURSE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_LCOURSE);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_RESPONSIBILITY);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_DEP_STAFF);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_PROGRAM);
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.config.TABLE_CLASS);
        onCreate(db);
        Log.e("DATABASE OPERATION", Constants.config.TOTAL_TABLES+" Table created / open successfully");

    }
    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }
    public   SQLiteDatabase getWritableDB(){
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

        return database;
    }
    public SQLiteDatabase getReadableDB(){
        SQLiteDatabase database = null;
        try {
            database = this.getReadableDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

        return database;
    }
    /************** Insertion ends here **********************/
}