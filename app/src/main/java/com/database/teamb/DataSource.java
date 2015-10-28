package com.database.teamb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.Models.Building;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for interfacing with the database
 */
public class DataSource {

    public static final String TAG = "DataSource";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    private static final String[] allColumns = {
            DbHelper.Building_id,
            DbHelper.Building_name
    };

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

    public List<Building> getAllBuildings(){
        List<Building> buildings = new ArrayList<Building>();

        Cursor cursor = database.query(DbHelper.Table_Building, allColumns, null, null, null, null, null);

        Log.i(TAG, "Returned" + cursor.getColumnCount() + "rows");
        if(cursor.getColumnCount() >0){
            while (cursor.moveToNext()){
                Building building = new Building();
                building.setBuilding_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Building_id)));
                building.setBuilding_name(cursor.getString(cursor.getColumnIndex(DbHelper.Building_name)));
            }
        }
        return buildings;
    }
}
