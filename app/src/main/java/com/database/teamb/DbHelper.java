package com.database.teamb;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String TAG = "DBHelper";

    //Columns in database for Building Table
    public static final String Table_Building = "Building";
    public static final String Building_name = "building_name";
    public static final String Building_id = "building_id";

    //Columns in database for Room Table
    public static final String Table_Room = "Room";
    public static final String Room_id = "room_id";
    public static final String Room_number = "room_number";
    public static final String Type = "type";
    public static final String Node_id = "node_id";

    //Columns in database for Node Table
    public static final String Table_Node = "Node";
    public static final String Floor = "floor";
    public static final String Reachable_Nodes = "reachable_nodes";
    public static final String Coordinates = "coordinates";

    //Creating tables
    //should be:CREATE TABLE Building(Building_id integer PRIMARY KEY AUTOINCREMENT, building_Name text);
    private static final String SQL_CREATE_TABLE_Building = "CREATE TABLE " + Table_Building + "("
            + Building_id + " integer PRIMARY KEY AUTOINCREMENT, " + Building_name + " text);";

    //should be: CREATE TABLE Room(room_id integer PRIMARY KEY AUTOINCREMENT, room_number text, type test, Node_id integer, FOREIGN KEY (Node_id) REFERENCES Node(Node_id);
    private static final String SQL_CREATE_TABLE_Room = "CREATE TABLE " + Table_Room + "(" + Room_id + " integer PRIMARY KEY AUTOINCREMENT, " +
            Room_number + " text, " + Type + " text, " + Node_id + " integer, FOREIGN KEY (" + Node_id + ") REFERENCES " + Table_Node + "(" + Node_id + "));";

    //should be: CREATE TABLE Node(Node_id integer PRIMARY KEY AUTOINCREMENT, floor integer, Building_id integer, reachable_nodes bloc, coordinates, FOREIGN KEY (Building_id) REFERENCES Building(Building_id);
    private static final String SQL_CREATE_TABLE_Node = "CREATE TABLE " + Table_Node + "("
            + Node_id + " integer PRIMARY KEY AUTOINCREMENT, " + Floor + " integer, " + Building_id + " integer, " + Reachable_Nodes + " blob, " + Coordinates + " blob, FOREIGN KEY" +
            " (" + Building_id + ") REFERENCES " + Table_Building + "(" + Building_id + "));";

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "MizzMaps.db";

    Context context;

    //constructor
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "creating database");
        db.execSQL(SQL_CREATE_TABLE_Building);
        db.execSQL(SQL_CREATE_TABLE_Node);
        db.execSQL(SQL_CREATE_TABLE_Room);
        Log.i(TAG, "Database created");
        fillDatabase(db);
    }

    //To Upgrade tables
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.i(TAG, "Upgrading From Version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Building);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Room);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Node);
        onCreate(db);
    }

    //To Downgrade tables
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void fillDatabase(SQLiteDatabase db){

        DbFileReader dbFileReader = new DbFileReader();
        dbFileReader.insertDataRooms(context, db);
        dbFileReader.insertDataNodes(context, db);
        dbFileReader.insertDataBuildings(db);
    }

}
