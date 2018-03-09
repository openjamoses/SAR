package com.example.denisfaith.mustsar.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.denisfaith.mustsar.R;

public class ResultActivity extends AppCompatActivity {

    private ImageButton image;
    private Button Submit_btn;
    private TextView Qrcode;
    private EditText courseCode;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 10;
    String name,gender,regno,info;
    private ListView listview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        info=getIntent().getStringExtra("data");
        int firstindexofcoma=info.indexOf(",");
        name=info.substring(0,firstindexofcoma);
        gender=info.substring(firstindexofcoma,info.lastIndexOf(","));
        regno=info.substring(info.lastIndexOf(""));
        String[] information={name,gender,regno};

        listview=findViewById(R.id.list);
        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,information));

        image=findViewById(R.id.image_b);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserPermissionsForCamera();
            }
        });

    }
    private void checkUserPermissionsForCamera(){
        String [] permissions={Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this,permissions,CAMERA_PERMISSION_REQUEST_CODE);

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if (grantResults.length>0){
                openCamera();
            }else {
                ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
            }
        }
    }
    //......this is called when the permissions are granted................
    private void openCamera() {
        //Log.d(TAG, "openCamera: Oppenning the camera");
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,CAMERA_REQUEST_CODE);
    }

    private void onClick() {


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            //Log.d(TAG, "onActivityResult: Getting results from the camera");
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                if (bitmap != null) {
                    //Log.d(TAG, "onActivityResult: We have a pictre");
                    image.setImageBitmap(bitmap);

                }
            }
        }
    }
}
