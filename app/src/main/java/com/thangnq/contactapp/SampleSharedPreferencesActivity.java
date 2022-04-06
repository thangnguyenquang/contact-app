package com.thangnq.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SampleSharedPreferencesActivity extends AppCompatActivity {

    EditText etEmail, etPwd;
    CheckBox chkRemember;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_shared_preferences);
        getViews();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiem tra viec login, neu chkRemember is check thi se luu lai thong tin email, pwd vao file sharePreference
                String email = etEmail.getText().toString();
                String pwd = etPwd.getText().toString();
                if (chkRemember.isChecked()) {
                    //thuc hien luu lai thong tin
                    //khoi tao doi tuong cua lop PrefManager va goi phuong thuc luu thong tin

                    new PreferencesManager(SampleSharedPreferencesActivity.this).saveLogin(email, pwd);
                }
                //goi lai startActivity de thuc hien login thanh cong
                if (email !="" || email!=null){
                    Intent intent = new Intent(SampleSharedPreferencesActivity.this, ContactManageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void getViews(){
        etEmail = findViewById(R.id.etEmailLogin);
        etPwd = findViewById(R.id.etPwdLogin);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLoginform);
    }

    private void saveLoginInfo(String email, String pwd){
        //luu vao file sharepreference
    }
}