package com.database.teamb;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Drew on 9/22/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String TAG = "DBHelper";

    //Columns in database for Building Table
    public static final String Table_Building = "Building";
    public static final String Building_name= "buildingName";
    public static final String Building_id = "ID";

    //Columns in database for Rooms Table
    public static final String Table_Rooms = "Rooms";
    public static final String Room_Number = "roomNumber";
    public static final String Node_ID = "node";
    public static final String Type  = "type";

    //Columns in database for Nodes Table
    public static final String Table_Nodes = "Nodes";
    public static final String Node_Adjacency  = "adjacency";
    public static final String Distance = "distance";
    public static final String Node__floor = "floor";
    public static final String Node_Building_ID = Building_id;

    //Creating tables
    private static final String SQL_CREATE_Table_Building = "CREATE TABLE " + Table_Building + "("
            + Building_name + " char(20), " + Building_id + " int UNIQUE)";

    private static final String SQL_CREATE_TABLE_Rooms = "CREATE TABLE "+ Table_Rooms +"(" + Room_Number + " varchar(20) PRIMARY KEY, "+
            Node_ID + " int REFERENCES Nodes.Node_ID, "+ Type + " varchar(10), ";

    private static final String SQL_CREATE_TABLE_Nodes = "CREATE TABLE " + Table_Nodes + "("
            + Node_ID + " int, " + Node_Adjacency + " varchar(10), " + Node__floor + " int, " + Node_Building_ID + " int REFERENCES Building.Building_name, " + Distance + " varchar(10))";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    //Executing Table
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_Table_Building);
        db.execSQL(SQL_CREATE_TABLE_Rooms);
        db.execSQL(SQL_CREATE_TABLE_Nodes);
    }
    //To Upgrade tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.w(TAG, "Uprgrading From Version " + oldVersion + " to " + newVersion);
        db.execSQL("Drop table if exists " + Table_Building);
        db.execSQL("Drop Table if exists " + Table_Rooms);
        db.execSQL("Drop Table if exists " + Table_Nodes);
        onCreate(db);
    }
    //To Downgrade tables
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
