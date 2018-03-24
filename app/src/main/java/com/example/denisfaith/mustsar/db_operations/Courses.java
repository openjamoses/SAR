package com.example.denisfaith.mustsar.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

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
import static com.example.denisfaith.mustsar.utils.Constants.config.COURSE_CODE;
import static com.example.denisfaith.mustsar.utils.Constants.config.COURSE_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.COURSE_NAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.HOST_URL;
import static com.example.denisfaith.mustsar.utils.Constants.config.LCOURSEID;
import static com.example.denisfaith.mustsar.utils.Constants.config.LCOURSE_STATUS;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.STUDENT_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.URL_SAVE_LCOURSE;

/**
 * Created by john on 10/18/17.
 */

public class Courses {

    Context context;
    private static final String TAG = "Course";
    public Courses(Context context){
        this.context = context;
    }

    public void insert(long start_time,JSONArray response){
        new InsertBackground(context,start_time).execute(response);
    }

    public class InsertBackground extends AsyncTask<JSONArray,Void,String> {

        Context context;
        long startTime;
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000000;

        InsertBackground(Context context, long start_time){
            this.context = context;
            this.startTime = start_time;
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
                db.execSQL("DELETE FROM " + Constants.config.TABLE_COURSE+" ");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(COURSE_NAME,jsonObject.getString(Constants.config.COURSE_NAME));
                    contentValues.put(COURSE_ID, jsonObject.getLong(Constants.config.COURSE_ID));
                    contentValues.put(COURSE_CODE,jsonObject.getString(Constants.config.COURSE_CODE));


                    db.insert(Constants.config.TABLE_COURSE, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , Course Table Updated successfully!";

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
            endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000000;

            Log.e("Fetch results",s);

        }
    }
    public String saveLCourse(int staff_id, int class_id, int course_id, int status) {
        SQLiteDatabase database = DBHelper.getHelper(context).getWritableDatabase();
        String message = null;
        try {
            //database.beginTransaction();
            ContentValues contentValues = new ContentValues();

            contentValues.put(CLASS_ID, class_id);
            contentValues.put(COURSE_ID, course_id);
            contentValues.put(STAFF_ID, staff_id);
            contentValues.put(LCOURSE_STATUS, status);
            String query = "SELECT *  FROM " + Constants.config.TABLE_LCOURSE + " f" +
                    " WHERE " + CLASS_ID + " = '" + class_id + "' AND " + Constants.config.COURSE_ID + " = '" + course_id + "' ORDER BY " + Constants.config.LCOURSEID + " ASC ";
            database = DBHelper.getHelper(context).getReadableDatabase();
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                message = "Course already exist!";
            } else {
                database = DBHelper.getHelper(context).getWritableDatabase();
                database.insert(Constants.config.TABLE_LCOURSE, null, contentValues);
                //database.setTransactionSuccessful();
                message = "Course Details saved!";
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


    //// TODO: 10/15/17  Syncing
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LCOURSE ;

        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(LCOURSEID))));

                map.put(CLASS_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(CLASS_ID))));
                map.put(COURSE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(COURSE_ID))));
                map.put(STAFF_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(STUDENT_ID))));

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
                map.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(LCOURSEID))));

                map.put(CLASS_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(CLASS_ID))));
                map.put(COURSE_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(COURSE_ID))));
                map.put(STAFF_ID, String.valueOf(cursor.getInt(cursor.getColumnIndex(STUDENT_ID))));

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
            String selectQuery = "SELECT  * FROM " + Constants.config.TABLE_LCOURSE + " WHERE " + LCOURSE_STATUS + " = '" + status + "' ";
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
            String updateQuery = "UPDATE " + Constants.config.TABLE_LCOURSE + " SET " + LCOURSE_STATUS + " = '" + status + "' where " + LCOURSEID + "='" + id + "'  ";
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

    public void sendlcourse(final  int staff_id, final int course_id, final int class_id){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_SAVE_LCOURSE,
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
                            String message = saveLCourse(staff_id,class_id,course_id,status);
                            if (message.equals("Course Details saved!")){

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
                params.put(STAFF_ID, String.valueOf(staff_id));
                params.put(COURSE_ID, String.valueOf(course_id));
                params.put(CLASS_ID, String.valueOf(class_id));

                //Adding parameters

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


}
