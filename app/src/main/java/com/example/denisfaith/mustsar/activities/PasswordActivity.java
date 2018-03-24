package com.example.denisfaith.mustsar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.denisfaith.mustsar.R;
import com.example.denisfaith.mustsar.db_operations.DBController;

import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_PROGRAMS;
import static com.example.denisfaith.mustsar.utils.Constants.config.OPERATION_ROOM;

/**
 * Created by john on 3/7/18.
 */

public class PasswordActivity extends AppCompatActivity {
    private Button btn_signin;
    private EditText input_username, input_password;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_password);


        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_signin = (Button) findViewById(R.id.btn_signin);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input_username.getText().toString().equals("") && !input_password.getText().toString().trim().equals("")){
                    startActivity(new Intent(context,Intiate_scanActivity.class));
                    finish();
                }
            }
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String sql_room = "SELECT * FROM room_tb";
        String sql_program = "SELECT * FROM program_tb";
        DBController.fetch(context,sql_room,OPERATION_ROOM);
        DBController.fetch(context,sql_program,OPERATION_PROGRAMS);

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
