package com.thangnq.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DemoSQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_sqlite);

        getEmailInfo();
    }

    private void getEmailInfo(){
        String email =new PreferencesManager(DemoSQLiteActivity.this).getEmail();
        Toast.makeText(DemoSQLiteActivity.this, "Email login: " + email,
                Toast.LENGTH_LONG).show();
    }
}