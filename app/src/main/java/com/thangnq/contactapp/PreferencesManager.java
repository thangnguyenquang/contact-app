package com.thangnq.contactapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    Context context;
    PreferencesManager(Context context){
        this.context = context;
    }

    //ghi dữ liệu lên file
    public void saveLogin(String email, String password) {
        //Tạo đối tượng shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        //lay ra doi tuong Editor de thuc hien ghi du lieu len file share Preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //thuc hien put du lieu len file
        editor.putString("Email", email);
        editor.putString("Password", password);
        //goi phuong thuc commit de hoan thanh viec tao va ghi file share preferences
        editor.commit();
    }

    //Đọc dữ liệu từ file shared preferences
    public String getEmail(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }
}
