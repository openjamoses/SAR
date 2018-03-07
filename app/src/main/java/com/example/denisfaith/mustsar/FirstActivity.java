package com.example.denisfaith.mustsar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {
    Button std, lect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        std = (Button)findViewById(R.id.btn_std);
        lect = (Button)findViewById(R.id.btn_lect);
    }
    public void onClick(View v){
        if (v.getId() == R.id.btn_std){
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(v.getId() == R.id.btn_lect){
            startActivity(new Intent(this, PasswordFragment.class));
        }
    }
}
