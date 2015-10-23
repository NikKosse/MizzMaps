package com.database.teamb;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.derek.teamb.Room;


public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String TAG = "DBHelper";

    //Columns in database for Building Table
    public static final String Table_Building = "Building";
    public static final String Building_name = "building_Name";
    public static final String Building_id = "_ID";

    //Columns in database for Room Table
    public static final String Table_Room = "Room";
    public static final String Room_ID = "_ID";
    public static final String Room_Number = "room_number";
    public static final String Type = "type";
    public static final String Node_Id = "node_id";

    //Columns in database for Node Table
    public static final String Table_Node = "Node";
    public static final String Node_ID = "_ID";
    public static final String Floor = "floor";
    public static final String Building_ID = Building_id;
    public static final String Reachable_Nodes = "reachable_nodes";
    public static final String Coordinates = "coordinates";

    //Creating tables
    private static final String SQL_CREATE_Table_Building = "CREATE TABLE " + Table_Building + "("
            + Building_id + " integer PRIMARY KEY AUTOINCREMENT, " + Building_name + " text";

    private static final String SQL_CREATE_TABLE_Room = "CREATE TABLE " + Table_Room + "(" + Room_ID + " integer PRIMARY KEY AUTOINCREMENT, " +
            Room_Number + " text, " + Type + " text, " + Node_Id + "integer, FOREIGN KEY (" + Node_ID + ") REFERENCES " + Table_Node + "(" + Node_ID + "));";

    private static final String SQL_CREATE_TABLE_Node = "CREATE TABLE " + Table_Node + "("
            + Node_ID + " integer PRIMARY KEY AUTOINCREMENT, " + Floor + " integer, " + Building_ID + " integer, " + Reachable_Nodes + " blob, " + Coordinates + " blob, FOREIGN KEY" +
            " (" + Building_ID + ") REFERENCES " + Table_Building + "(" + Building_id + "));";

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "MizzMaps.db";
    public String[] insertList;
    public String path = "";

    //constructor
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        path = context.getDatabasePath(DATABASE_NAME).getPath();
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "**********************Creating Database*********************");
        db.execSQL(SQL_CREATE_Table_Building);
        db.execSQL(SQL_CREATE_TABLE_Room);
        db.execSQL(SQL_CREATE_TABLE_Node);

        String[] files = {"LafferreRooms.csv", "LafferreNodes.csv"};
        DbFileReader sqlGenerate = new DbFileReader();
        insertList = sqlGenerate.generateSQLInsertRooms(files[0]);
        executeInsert(db);
        insertList = sqlGenerate.generateSQLInsertNodes(files[1]);
        executeInsert(db);

    }

    //To Upgrade tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.w(TAG, "*******************Uprgrading From Version " + oldVersion + " to " + newVersion);
        db.execSQL("Drop table if exists " + Table_Building);
        db.execSQL("Drop Table if exists " + Table_Room);
        db.execSQL("Drop Table if exists " + Table_Node);
        onCreate(db);
    }

    //To Downgrade tables
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String testDatabase(String query){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        String result = cursor.getString(cursor.getColumnIndex("type"));
        return path;
    }

    public void executeInsert(SQLiteDatabase db) {
        //for every s of type string in insertList
        for (String s: insertList) {
            db.execSQL(s);
        }
    }

}
