package com.example.denisfaith.mustsar.db_operations;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static com.example.denisfaith.mustsar.utils.Constants.config.HOST_URL;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_CLASS;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_COURSES;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_PROGRAMS;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_REGISTRATION;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_ROOM;
import static com.example.denisfaith.mustsar.utils.Constants.config.SQL_QUERY;
import static com.example.denisfaith.mustsar.utils.Constants.config.URL_FETCH_JSON;

/**
 * Created by john on 10/14/17.
 */
public class DBController {
    private static final String TAG = "DBController";
    static Handler mainHandler = new Handler(Looper.getMainLooper());
    public static void syncCalls(final String url, final String operations, final String show, final Context context){
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "******************************** " + url);
                Log.e(TAG, "Syncing started for: " + operations);
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    int db_count = 0;
                    ArrayList<HashMap<String, String>> userList = new ArrayList<HashMap<String, String>>();
                    String json_data = "";
                    if (operations.equals(OPERATION_COURSES)) {
                        userList = new Courses(context).getAllUsers();
                        db_count = new Courses(context).dbSyncCount();
                        json_data = new Courses(context).composeJSONfromSQLite();
                    }
                    if (userList.size() != 0) {
                        if (db_count != 0) {
                            if (!show.equals("")) {
                                //prgDialog.show();
                            }
                            params.put("dataJSON", json_data);
                            client.post(HOST_URL + url, params, new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                    try {
                                        String response = new String(responseBody, "UTF-8");
                                        //prgDialog.hide();
                                        try {
                                            Log.e(TAG, response);
                                            JSONArray arr = new JSONArray(response);
                                            System.out.println(arr.length());
                                            for (int i = 0; i < arr.length(); i++) {
                                                JSONObject obj = (JSONObject) arr.get(i);
//                                                System.out.println(obj.get("id"));

                                                if (operations.equals(OPERATION_COURSES)) {
                                                    new Courses(context).updateSyncStatus(Integer.parseInt(obj.get("id").toString()), Integer.parseInt(obj.get("status").toString()));
                                                }
                                            }
                                            //Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            //Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    if (statusCode == 404) {
                                        Log.e("Error ", "Error code " + statusCode + " \t " + url);
                                        //Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                                    } else if (statusCode == 500) {
                                        Log.e("Error ", "Error code " + statusCode + " \t" + url);
                                        //Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.e("Error ", "Error code " + statusCode + " \t " + url);
                                    }
                                }
                            });
                        } else {
                            Log.e("No Sync data", "Empty data to be sync " + url);
                        }
                    } else {
                        Log.e("Empty", "Empty data to be sync " + url);
                    }

                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }
            }
        };
        mainHandler.post(myRunnable);
    }

    public static void fetch(final Context context, final String sql, final String operation){
        final long start_time = System.nanoTime();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST_URL+URL_FETCH_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            if(operation.equals(OPERATION_COURSES)){
                                new Courses(context).insert(start_time,jsonArray);
                            }else if(operation.equals(OPERATION_CLASS)){
                                new Classes(context).insert(start_time,jsonArray);
                            }else if(operation.equals(OPERATION_ROOM)){
                                new Rooms(context).insert(start_time,jsonArray);
                            }else if(operation.equals(OPERATION_PROGRAMS)){
                                new Programmes(context).insert(start_time,jsonArray);
                            }else if(operation.equals(OPERATION_REGISTRATION)){
                                new Courses(context).insert(start_time,jsonArray);
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
                        //Log.e(TAG, volleyError.getMessage());
                        try {

                            //Log.e(TAG, volleyError.getMessage());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               // String imei = Phone.getIMEI(context);
                Map<String, String> params = new Hashtable<String, String>();
                params.put(SQL_QUERY, sql);
                //Adding parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
