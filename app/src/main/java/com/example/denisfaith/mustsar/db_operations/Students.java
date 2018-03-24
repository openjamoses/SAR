package com.example.denisfaith.mustsar.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.denisfaith.mustsar.core.DBHelper;
import com.example.denisfaith.mustsar.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.example.denisfaith.mustsar.utils.Constants.config.CLASS_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.COURSE_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.HOST_URL;
import static com.example.denisfaith.mustsar.utils.Constants.config.LCOURSE_STATUS;
import static com.example.denisfaith.mustsar.utils.Constants.config.REGISTRATIONID;
import static com.example.denisfaith.mustsar.utils.Constants.config.REGISTRATION_DATETIME;
import static com.example.denisfaith.mustsar.utils.Constants.config.REGISTRATION_STATUS;
import static com.example.denisfaith.mustsar.utils.Constants.config.ROOM_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.SQL_QUERY;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_FNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_GENDER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_LNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_PASSWORD;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_PHOTO;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_REGNUMBER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_SEMESTER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_YEAR;
import static com.example.denisfaith.mustsar.utils.Constants.config.TABLE_STUDENT;
import static com.example.denisfaith.mustsar.utils.Constants.config.URL_FETCH_JSON;
import static com.example.denisfaith.mustsar.utils.Constants.config.URL_SAVE_REGISTRATION;

/**
 * Created by john on 3/13/18.
 */

public class Students {
    Context context;
    private static final String TAG = "Student";
    public Students(Context context){
        this.context = context;
    }
    public String saveRegistration(String date, int student_id, int course_id, int room_id, int status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try {
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(REGISTRATION_DATETIME, date);
            contentValues.put(STUDENT_ID, student_id);
            contentValues.put(COURSE_ID, course_id);
            contentValues.put(ROOM_ID, room_id);
            contentValues.put(REGISTRATION_STATUS, status);
            String query = "SELECT *  FROM " + Constants.config.TABLE_REGISTRATION + " f" +
                    " WHERE " + REGISTRATION_DATETIME + " = '" + date + "' ORDER BY " + Constants.config.REGISTRATION_DATETIME + " ASC ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                message = "Registration details already exist!";
            } else {
                database = DBHelper.getHelper(context).getWritableDatabase();
                database.insert(Constants.config.TABLE_LCOURSE, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Registered Details saved!";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    public Cursor selectAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT *  FROM" +
                    " "+ Constants.config.TABLE_COURSE+" ORDER BY "+Constants.config.COURSE_NAME+"  ASC ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public Cursor select(int id){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "" +
                    " ORDER BY "+Constants.config.DEPARTMENT_NAME+" ASC";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }


    public void insert(JSONArray response){
        new InsertBackground(context).execute(response);
    }

    public class InsertBackground extends AsyncTask<JSONArray,Void,String> {

        Context context;

        InsertBackground(Context context){
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // startTime = System.nanoTime();
            // progressDialog.setTitle("Now Saving ! ...");
        }

        @Override
        protected String doInBackground(JSONArray... jsonArrays) {
            String message = null;
            int status = 1;
            SQLiteDatabase db = DBHelper.getHelper(context).getWritableDB();
            try{

                db.beginTransaction();
                JSONArray jsonArray = jsonArrays[0];
                db.execSQL("DELETE FROM " + Constants.config.TABLE_STUDENT+" ");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(STUDENT_FNAME,jsonObject.getString(Constants.config.STUDENT_FNAME));
                    contentValues.put(STUDENT_LNAME, jsonObject.getString(Constants.config.STUDENT_LNAME));
                    contentValues.put(STUDENT_YEAR,jsonObject.getString(Constants.config.STUDENT_YEAR));
                    contentValues.put(STUDENT_SEMESTER,jsonObject.getString(Constants.config.STUDENT_SEMESTER));
                    contentValues.put(STUDENT_PASSWORD,jsonObject.getString(Constants.config.STUDENT_PASSWORD));
                    contentValues.put(STUDENT_REGNUMBER, jsonObject.getLong(Constants.config.STUDENT_REGNUMBER));
                    contentValues.put(STUDENT_GENDER, jsonObject.getString(Constants.config.STUDENT_GENDER));
                    contentValues.put(STUDENT_ID,jsonObject.getLong(Constants.config.STUDENT_ID));
                    contentValues.put(CLASS_ID, jsonObject.getLong(Constants.config.CLASS_ID));
                    db.insert(Constants.config.TABLE_STUDENT, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , "+TABLE_STUDENT+" Table Updated successfully!";

            }catch (Exception e){
                e.printStackTrace();
                message = "Error: "+e;
                Log.e("Error: ",e.toString());
            }finally {
                db.endTransaction();
            }
            return  message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("Fetch results",s);

        }
    }
    public void fetch(final  String sql_query){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_FETCH_JSON,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);

                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length()>0){
                                insert(jsonArray);
                            }else {
                                Toast.makeText(context,"Incorrect Password...!",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e(TAG, ""+volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int status = 1;
                Map<String, String> params = new Hashtable<String, String>();
                params.put(SQL_QUERY,sql_query);
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public void sendRegistration(final String date, final  int student_id, final int course_id, final int room_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_REGISTRATION,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "Results: " + response);
                            String[] splits= response.split("/");
                            int status = 0;
                            if (splits[0].equals("Success")){
                                status = 1;
                            }
                            String message = saveRegistration(date,student_id,course_id,course_id,status);
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            if (message.equals("Registered Details saved!")){

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.e(TAG,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        try {
                            Log.e(TAG, ""+volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                ////int status = 1;
                Map<String, String> params = new Hashtable<String, String>();
                params.put(STUDENT_ID, String.valueOf(student_id));
                params.put(COURSE_ID, String.valueOf(course_id));
                params.put(ROOM_ID, String.valueOf(room_id));
                params.put(REGISTRATION_DATETIME, date);
                params.put(STUDENT_PHOTO, "");
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }



    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_REGISTRATION ;

        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(REGISTRATIONID))));

                map.put(REGISTRATION_DATETIME, cursor.getString(cursor.getColumnIndex(REGISTRATION_DATETIME)));
                map.put(COURSE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(COURSE_ID))));
                map.put(STUDENT_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(STUDENT_ID))));
                map.put(ROOM_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(ROOM_ID))));

                wordList.add(map);
            } while (cursor.moveToNext());
        }
        //database.close();
        return wordList;
    }
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        int status = 0;
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LCOURSE + " WHERE " + LCOURSE_STATUS + " = '" + status + "' ";
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(REGISTRATIONID))));

                map.put(REGISTRATION_DATETIME, cursor.getString(cursor.getColumnIndex(REGISTRATION_DATETIME)));
                map.put(COURSE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(COURSE_ID))));
                map.put(STUDENT_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(STUDENT_ID))));
                map.put(ROOM_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(ROOM_ID))));

                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }
    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }
    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        SQLiteDatabase database = null;
        try {
            int status = 0;
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_REGISTRATION + " WHERE " + REGISTRATION_STATUS + " = '" + status + "' ";
            database = new DBHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            count = cursor.getCount();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                database.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return count;
    }
    /***
     *
     * @param id
     * @param status
     */
    public void updateSyncStatus(int id, int status){
        SQLiteDatabase database = null;
        try {
            database = new DBHelper(context).getWritableDatabase();
            String updateQuery = "UPDATE " + Constants.config.TABLE_REGISTRATION + " SET " + REGISTRATION_STATUS + " = '" + status + "' where " + REGISTRATIONID + "='" + id + "'  ";
            Log.d("query", updateQuery);
            database.execSQL(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                database.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String regQuery(String reg_no){
        String query = "SELECT * FROM  student_tb WHERE "+STUDENT_REGNUMBER+" = '"+reg_no+"' ";
        return query;
    }
}
