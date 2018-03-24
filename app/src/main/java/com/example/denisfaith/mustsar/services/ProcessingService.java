package com.example.denisfaith.mustsar.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by john on 9/6/17.
 */

public class ProcessingService extends Service {
    private Timer timer = new Timer();
    private static final String TAG = "ProcessingService";

    private Context context = this;
    private static final Uri STATUS_URI = Uri.parse("content://sms");
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG,"Service ProcessingService started successfull!");
        //// TODO: 10/15/17  clear memory usage....

        super.onCreate();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sendRequest();
                }
            }, 0, 10000);//1 minutes

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //shutdownService();
    }

    public void  sendRequest(){
        //// TODO: 11/20/17  after every 1 second...!!!!
        Log.e(TAG, "1 minutes has elapsed!");
        try {

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}