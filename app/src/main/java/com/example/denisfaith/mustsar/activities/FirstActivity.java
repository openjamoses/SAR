package com.example.denisfaith.mustsar.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.denisfaith.mustsar.R;
import com.example.denisfaith.mustsar.db_operations.DBController;

import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_CLASS;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_COURSES;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_PROGRAMS;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_ROOM;

public class FirstActivity extends AppCompatActivity  {
    private Button std, lect;
    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first1);

        std = (Button)findViewById(R.id.btn_std);
        lect = (Button)findViewById(R.id.btn_lect);
        std.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });
        lect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PasswordActivity.class));
            }
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String sql_class = "SELECT * FROM class_tb";

        DBController.fetch(context,sql_class,OPERATION_CLASS);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
