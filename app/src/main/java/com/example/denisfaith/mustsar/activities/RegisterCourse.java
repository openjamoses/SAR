package com.example.denisfaith.mustsar.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.denisfaith.mustsar.R;
import com.example.denisfaith.mustsar.db_operations.Classes;
import com.example.denisfaith.mustsar.db_operations.Courses;
import com.example.denisfaith.mustsar.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 3/13/18.
 */

public class RegisterCourse extends AppCompatActivity {
    private Spinner course_spinnner, class_spinner;
    private Button submit_btn;
    private ListView listView;
    private Context context = this;
    private static final String TAG = "RegisterCourse";
    private List<String> course_list = new ArrayList<>();
    private List<Integer> course_id = new ArrayList<>();
    private List<String> class_list = new ArrayList<>();
    private List<Integer> class_id = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_courses);
        course_spinnner = (Spinner) findViewById(R.id.course_spinner);
        class_spinner = (Spinner) findViewById(R.id.class_spinner);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        listView = (ListView) findViewById(R.id.listView);


    }

    private void setSpinner(){
        try{
            Cursor cursor = new Courses(context).selectAll();
            if (cursor.moveToFirst()){
                do {
                    course_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.COURSE_NAME))+" - ("+cursor.getString(cursor.getColumnIndex(Constants.config.COURSE_CODE))+")");
                    course_id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.COURSE_ID)));
                }while (cursor.moveToNext());
            }

            //TODO:: ... Setting the spinner....!!!!..
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, course_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            course_spinnner.setAdapter(dataAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }


        try{
            Cursor cursor = new Classes(context).getClasses();
            if (cursor.moveToFirst()){
                do {
                    class_list.add(cursor.getString(cursor.getColumnIndex(Constants.config.CLASS_NAME)));
                    class_id.add(cursor.getInt(cursor.getColumnIndex(Constants.config.CLASS_ID)));
                }while (cursor.moveToNext());
            }

            //TODO:: ... Setting the spinner....!!!!..
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, class_list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            class_spinner.setAdapter(dataAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
