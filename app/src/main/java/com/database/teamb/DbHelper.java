package com.database.teamb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.derek.teamb.GetObject;

import java.util.ArrayList;
import java.util.List;


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
    public static final String Room_type = "type";
    public static final String Node_id = "node_id";

    //Columns in database for Node Table
    public static final String Table_Node = "Node";
    public static final String Node_floor = "floor";
    public static final String Reachable_nodes = "reachable_nodes";
    public static final String Node_coordinates = "coordinates";

    //Columns in database for course table
    public static final String Table_Course = "Course";
    public static final String Course_id = "course_id";
    public static final String Course_name = "course_name";
    public static final String Course_room = "course_room";
    public static final String Course_building = "course_building_name";

    //Columns in database for storage table
    public static final String Table_Storage = "Storage";
    public static final String Storage_room = "storage_room";
    public static final String Storage_id = "storage_id";

    //Creating tables
    //should be:CREATE TABLE Building(Building_id integer PRIMARY KEY AUTOINCREMENT, building_Name text);
    private static final String SQL_CREATE_TABLE_Building = "CREATE TABLE " + Table_Building + "("
            + Building_id + " integer PRIMARY KEY AUTOINCREMENT, " + Building_name + " text);";

    //should be: CREATE TABLE Room(room_id integer PRIMARY KEY AUTOINCREMENT, room_number text, type test, Node_id integer, FOREIGN KEY (Node_id) REFERENCES Node(Node_id));
    private static final String SQL_CREATE_TABLE_Room = "CREATE TABLE " + Table_Room + "(" + Room_id + " integer PRIMARY KEY, " +
            Room_number + " text, " + Room_type + " text, " + Node_id + " integer, FOREIGN KEY (" + Node_id + ") REFERENCES " + Table_Node + "(" + Node_id + "));";

    //should be: CREATE TABLE Node(Node_id integer PRIMARY KEY AUTOINCREMENT, floor integer, building_id integer, reachable_nodes bloc, coordinates, FOREIGN KEY (Building_id) REFERENCES Building(Building_id));
    private static final String SQL_CREATE_TABLE_Node = "CREATE TABLE " + Table_Node + "("
            + Node_id + " integer PRIMARY KEY, " + Node_floor + " integer, " + Building_id + " integer, " + Reachable_nodes + " text, " + Node_coordinates + " text, FOREIGN KEY" +
            " (" + Building_id + ") REFERENCES " + Table_Building + "(" + Building_id + "));";

    //should be: CREATE TABLE Course(course_id integer PRIMARY KEY AUTOINCREMENT, course_name text, course_room text, course_building_id integer);
    private static final String SQL_CREATE_TABLE_Schedule = "CREATE TABLE " + Table_Course + "(" + Course_id + "integer PRIMARY KEY, " + Course_name + " text, "
            + Course_room + " text, " + Course_building + " text);";


    private static final String SQL_CREATE_TABLE_Storage = "CREATE TABLE " + Table_Storage + "(" + Storage_id + "integer PRIMARY KEY, "
            + Storage_room + " text);";

    public static final int DATABASE_VERSION = 8;
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
        db.execSQL(SQL_CREATE_TABLE_Schedule);
        db.execSQL(SQL_CREATE_TABLE_Storage);
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
        db.execSQL("DROP TABLE IF EXISTS " + Table_Course);
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

    // Read records related to the search term
    public GetObject[] read(String searchTerm) {

        List<GetObject> recordsList = new ArrayList<GetObject>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + Table_Room;
        sql += " WHERE " + Room_number + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + Room_id + " DESC";
        sql += " LIMIT 0,5";

        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();

        GetObject[] ObjectItemData = new GetObject[recCount];
        int x = 0;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String objectName = cursor.getString(cursor.getColumnIndex(Room_number));
                Log.e(TAG, "objectName: " + objectName);

                GetObject getObject = new GetObject(objectName);

                ObjectItemData[x] = getObject;

                x++;

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ObjectItemData;

    }

    // create new record
    // @param myObj contains details to be added as single row.
    public boolean create(GetObject myObj) {

        boolean createSuccessful = false;

        if(!checkIfExists(myObj.objectName)){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Course_name, myObj.objectName);
            createSuccessful = db.insert(Table_Course, null, values) > 0;

            db.close();

            if(createSuccessful){
                Log.e(TAG, myObj.objectName + " created.");
            }
        }

        return createSuccessful;
    }

    // check if a record exists so it won't insert the next time you run this code
    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + Course_name + " FROM " + Table_Course + " WHERE " + Course_name + " = '" + objectName + "'", null);

        if(cursor!=null) {

            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

    public List<String> getRooms(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_Course;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public void deleteRoomData()
    {
        SQLiteDatabase sdb= this.getWritableDatabase();
        sdb.delete(Table_Course, null, null);

    }

    public void deleteStorageData()
    {
        SQLiteDatabase sdb= this.getWritableDatabase();
        sdb.delete(Table_Storage, null, null);

    }

    public boolean store(GetObject myObj) {

        boolean createSuccessful = false;

        if(!checkIfExists(myObj.objectName)){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Course_name, myObj.objectName);
            createSuccessful = db.insert(Table_Storage, null, values) > 0;

            db.close();

            if(createSuccessful){
                Log.e(TAG, myObj.objectName + " created.");
            }
        }

        return createSuccessful;
    }

    public List<String> getStorage(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_Storage;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }



}