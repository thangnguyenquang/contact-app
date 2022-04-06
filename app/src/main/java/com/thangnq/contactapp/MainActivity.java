package com.thangnq.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextPhoneNumber;
    String keySearch="";

    Button buttonSave;
    Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        Intent receiveIntent = getIntent();
        String action = receiveIntent.getAction();
        if(action=="update"){
            Contact contact = (Contact) receiveIntent.getExtras().get("contact");
            keySearch = contact.getName();
            editTextName.setText(contact.getName());
            editTextPhoneNumber.setText(contact.getPhoneNumber());
        }
        else {
            String msg = receiveIntent.getStringExtra("message");
            Toast.makeText(MainActivity.this, msg , Toast.LENGTH_LONG).show();
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                intentResult.putExtra("name", editTextName.getText().toString());
                intentResult.putExtra("phone", editTextPhoneNumber.getText().toString());
                /*if(editTextName.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên liên lạc", Toast.LENGTH_LONG).show();
                else if (editTextPhoneNumber.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số liên lạc", Toast.LENGTH_LONG).show();*/
                if(action=="update") {
                    intentResult.putExtra("key", keySearch);
                    setResult(1001, intentResult);
                    finish();
                }
                    setResult(1000, intentResult);
                    finish();
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getViews(){
        editTextName = findViewById(R.id.etName);
        editTextPhoneNumber = findViewById(R.id.etPhoneNumber);
        buttonClose = findViewById(R.id.btnClose);
        buttonSave = findViewById(R.id.btnSave);
    }
}