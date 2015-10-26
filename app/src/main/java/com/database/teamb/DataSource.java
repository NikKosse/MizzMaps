package com.database.teamb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;

/**
 * Class for interfacing with the database
 */
public class DataSource {

    public static final String TAG = "DataSource";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    public String path = "";


    public DataSource(Context context) {
        dbhelper = new DbHelper(context);
    }

    public void open(){
        Log.i(TAG, "database opened.");
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        Log.i(TAG, "database closed.");
        database.close();
    }
}
