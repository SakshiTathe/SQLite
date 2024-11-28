package com.example.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //Class Definition and Constructor
    public static final String DBNAME="Login.db";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    //Called when the database is first created.
    @Override
    public void onCreate(SQLiteDatabase MyDb) {
        String createTable = "CREATE TABLE users(usernamee TEXT PRIMARY KEY,passwordd Text)";
        MyDb.execSQL(createTable);
    }
    //database version is incremented
    @Override
    public void onUpgrade(SQLiteDatabase MyDb, int i, int i1) {
        MyDb.execSQL("drop table if exists users");
        onCreate(MyDb);
    }

    //actual column names (usernamee and passwordd).
    public boolean insertdata(String name,String password){
        SQLiteDatabase mydbs= this.getWritableDatabase();
        ContentValues convalue= new ContentValues();
        convalue.put("usernamee",name);
        convalue.put("passwordd",password);
        long result=mydbs.insert("users",null,convalue);
        if(result==-1) return false;
        else return true;
    }
    public  boolean checkuser(String name){
        SQLiteDatabase mydbs= this.getWritableDatabase();
        Cursor cursor=mydbs.rawQuery("select * from users where usernamee=?",new String[]{name});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public boolean checkpass(String name,String password){
        SQLiteDatabase mydbs= this.getWritableDatabase();
        Cursor cursor=mydbs.rawQuery("select * from users where usernamee=? and passwordd=?",new String[]{name,password});
        if(cursor.getCount()>0) return true;
        else return false;
    }
}
