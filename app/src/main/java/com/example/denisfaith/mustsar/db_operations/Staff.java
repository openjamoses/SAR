package com.example.denisfaith.mustsar.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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

import static com.example.denisfaith.mustsar.utils.Constants.config.CLASS_NAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.HOST_URL;
import static com.example.denisfaith.mustsar.utils.Constants.config.PROGRAM_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.SQL_QUERY;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_FNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_GENDER;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_LNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_PASSWORD;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_PHONE;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_ROLE;
import static com.example.denisfaith.mustsar.utils.Constants.config.STAFF_USERNAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.URL_FETCH_JSON;

/**
 * Created by john on 10/17/17.
 */
public class Staff {
    Context context;
    private static final String TAG = "Staff";
    public Staff(Context context){
        this.context = context;
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
                db.execSQL("DELETE FROM " + Constants.config.TABLE_STAFF+" ");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(STAFF_FNAME,jsonObject.getString(Constants.config.STAFF_FNAME));
                    contentValues.put(STAFF_LNAME, jsonObject.getString(Constants.config.STAFF_LNAME));
                    contentValues.put(STAFF_ROLE,jsonObject.getString(Constants.config.STAFF_ROLE));
                    contentValues.put(STAFF_USERNAME,jsonObject.getString(Constants.config.STAFF_USERNAME));
                    contentValues.put(STAFF_PASSWORD,jsonObject.getString(Constants.config.STAFF_PASSWORD));
                    contentValues.put(STAFF_GENDER, jsonObject.getLong(Constants.config.STAFF_GENDER));
                    contentValues.put(STAFF_ID,jsonObject.getLong(Constants.config.STAFF_ID));
                    contentValues.put(STAFF_PHONE, jsonObject.getString(Constants.config.STAFF_PHONE));
                    db.insert(Constants.config.TABLE_STAFF, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records , Class Table Updated successfully!";

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



    public String selectPassword(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        String password = "";
        try{
            db.beginTransaction();
            String query = "SELECT "+Constants.config.STAFF_PASSWORD+" FROM" +
                    " "+ Constants.config.TABLE_STAFF+" " +
                    " ORDER BY "+ STAFF_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    password = cursor.getString(cursor.getColumnIndex(Constants.config.STAFF_PASSWORD));
                }while (cursor.moveToNext());
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  password;
    }

    public Cursor login(String username, String password) {
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        String message = "";
        try{
            db.beginTransaction();
            String query = "SELECT * FROM" +
                    " "+ Constants.config.TABLE_STAFF+" WHERE "+STAFF_USERNAME+" = '"+username+"' AND "+STAFF_PASSWORD+" = '"+password+"' " +
                    " ORDER BY "+ STAFF_ID+" DESC LIMIT 1 ";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }

    public Cursor getAll(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT * FROM" +
                    " "+ Constants.config.TABLE_STAFF+" ORDER BY "+STAFF_ID+" DESC LIMIT 1";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
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
