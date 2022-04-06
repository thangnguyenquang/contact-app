package com.thangnq.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "contact.db";
    public static final int DB_VERSION = 1;
    public static final String TB_NAME = "contacts";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public Context context;
    public DatabaseHelper(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TB_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + PHONE + " TEXT" + ")" ;
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TB_NAME;
    }

    public void addContact(Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, contact.getName());
        contentValues.put(PHONE, contact.getPhoneNumber());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long result = sqLiteDatabase.insert(TB_NAME, null, contentValues);
        /*if(result != -1) {
            Toast.makeText(context, "Thêm contact thành công", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Thêm contact không thành công", Toast.LENGTH_LONG).show();*/
        sqLiteDatabase.close();
    }

    public void updateContact(Contact contact, String name){
        ContentValues contentUpdate = new ContentValues();
        contentUpdate.put(NAME, contact.getName());
        contentUpdate.put(PHONE, contact.getPhoneNumber());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int resutl = sqLiteDatabase.update(TB_NAME,contentUpdate,
                NAME +" like '%" + name +"%'",null );
        /*if(resutl > 0)
            Toast.makeText(context, "Cập nhập thành công !", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Cập nhập không thành công !", Toast.LENGTH_LONG).show();*/
        sqLiteDatabase.close();
    }

    public void deleteContact(String name){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int result = sqLiteDatabase.delete(TB_NAME,NAME +" = ?" ,new String[]{name});
        /*if(result >0)
            Toast.makeText(context, "Xóa thành công !", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Xóa không thành công !", Toast.LENGTH_LONG).show();*/
        sqLiteDatabase.close();
    }

    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> result = new ArrayList<>();
        //lay ra sqliteDatabase thuc hien doc du lieu
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TB_NAME, null);
        while (cursor.moveToNext()){
            Contact contact = new Contact();
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
            result.add(contact);
        }
        return result;
    }

    public ArrayList<Contact> searchContact(String name){
        ArrayList<Contact> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + TB_NAME +
                    " where " + NAME + " like ? ", new String[]{"%" + name + "%"});
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Contact>();
                do {
                    Contact contact = new Contact();
                    //thuc hien gan du lieu cho doi tuong contact
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));
                    contacts.add(contact);
                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            contacts = null;
        }

        return contacts;
    }
}
