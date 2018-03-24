package com.example.denisfaith.mustsar.db_operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.denisfaith.mustsar.core.DBHelper;
import com.example.denisfaith.mustsar.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.denisfaith.mustsar.utils.Constants.config.PROGRAM_DURATION;
import static com.example.denisfaith.mustsar.utils.Constants.config.PROGRAM_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.PROGRAM_NAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.ROOM_ID;
import static com.example.denisfaith.mustsar.utils.Constants.config.ROOM_NAME;
import static com.example.denisfaith.mustsar.utils.Constants.config.TABLE_PROGRAM;
import static com.example.denisfaith.mustsar.utils.Constants.config.TABLE_ROOM;

/**
 * Created by john on 3/24/18.
 */

public class Programmes {

    Context context;
    public Programmes(Context context){
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
                db.execSQL("DELETE FROM " + Constants.config.TABLE_PROGRAM+" ");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    ContentValues contentValues = new ContentValues();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    contentValues.put(PROGRAM_ID,jsonObject.getLong(Constants.config.PROGRAM_ID));
                    contentValues.put(PROGRAM_NAME, jsonObject.getString(Constants.config.PROGRAM_NAME));
                    contentValues.put(PROGRAM_DURATION, jsonObject.getString(Constants.config.PROGRAM_DURATION));

                    db.insert(Constants.config.TABLE_PROGRAM, null, contentValues);
                    total ++;
                }
                db.setTransactionSuccessful();
                message = total+" records ,"+TABLE_PROGRAM+" Table Updated successfully!";

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


    public Cursor getProgramm(){
        SQLiteDatabase db = DBHelper.getHelper(context).getReadableDB();
        Cursor cursor = null;
        try{
            db.beginTransaction();
            String query = "SELECT * FROM "+ Constants.config.TABLE_PROGRAM+" ORDER BY "+Constants.config.PROGRAM_NAME+" ASC";
            cursor = db.rawQuery(query,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return  cursor;
    }


}
