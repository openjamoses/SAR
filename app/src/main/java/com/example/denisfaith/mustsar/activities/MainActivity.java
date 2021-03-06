package com.example.denisfaith.mustsar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denisfaith.mustsar.R;
import com.example.denisfaith.mustsar.db_operations.DBController;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_COURSES;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_PROGRAMS;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_ROOM;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //..........defining the View objects...........................................................
    private Button Scan_btn;
    private IntentIntegrator qRScan;
    private TextView ResultView;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //..........typecasting, finding the objects by their IDs.....................................
        Scan_btn = (Button)findViewById(R.id.scan_btn);
        //exit_btn =(Button)findViewById(R.id.cancel_btn);
        //.......now we initialize the scan object...................................................
        qRScan = new IntentIntegrator(this);
        //.............we make the scan & exit buttons  to Listen when clicked .....................
        Scan_btn.setOnClickListener(this);
       // exit_btn.setOnClickListener(this);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String sql_course = "SELECT * FROM course_tb";
        DBController.fetch(context,sql_course,OPERATION_COURSES);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    //.............getting the scanned results......................................................
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //...........test for the results by use of "if" statement...................................
        //............if the QR code thas been captured/scanned...............
        if(result!=null){

            //..............if nothing in the QR code............................
            if(result.getContents()==null){

                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else{
                //..............if something in the QR code............................
                try{
                      //......in this case you can display whatever data is available on the qrcode
                    Intent nextActivity = new Intent(this, ResultActivity.class);
                   // nextActivity.putExtra("Your Student ID information ", (Parcelable) ResultView);
                    nextActivity.putExtra("data",result.getContents().toString());
                    startActivity(nextActivity);
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }else {

            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    public void onClick(View Scan_btn) {
        qRScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qRScan.setPrompt(" Forcus the Camera on the on Your ID & Scan the QR-Code");
        qRScan.setCameraId(0);
        qRScan.setBeepEnabled(false);
        qRScan.setBarcodeImageEnabled(false);
        qRScan.setOrientationLocked(false);
        qRScan.initiateScan();


    }

}
