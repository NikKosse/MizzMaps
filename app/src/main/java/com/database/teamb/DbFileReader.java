package com.database.teamb;

//file IO includes
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reads in CSV file and inserts the data into the database
 */
public class DbFileReader {

    public DbFileReader() {
    }

    String line;
    String csvSplitBy = ",";
    String[] insertList = new String[5000];
    String TAG = "DbFileReader";

    public void insertDataRooms(Context context, SQLiteDatabase db){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("Rooms.csv")));
            while ((line = reader.readLine()) != null) {
                String[] currentLine = line.split(csvSplitBy);
                ContentValues values = new ContentValues();
                values.put(DbHelper.Room_number, currentLine[0]);
                values.put(DbHelper.Room_type, currentLine[1]);
                values.put(DbHelper.Node_id, currentLine[2]);
                values.put(DbHelper.xCoord, currentLine[3]);
                values.put(DbHelper.yCoord, currentLine[4]);
                values.put(DbHelper.Room_floor, currentLine[5]);
                long room_id = db.insert(DbHelper.Table_Room, null, values);
                Log.i(TAG, "Room line inserted, id: " + room_id);
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Error reading file!");
            e.printStackTrace();
        }
    }


    public void insertDataNodes(Context context, SQLiteDatabase db){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("Nodes.csv")));
            while ((line = reader.readLine()) != null){
                String[] currentLine = line.split(csvSplitBy);
                ContentValues values = new ContentValues();
                values.put(DbHelper.Node_floor, currentLine[0]);
                values.put(DbHelper.Building_id, currentLine[1]);
                values.put(DbHelper.Reachable_nodes, currentLine[2]);
                values.put(DbHelper.Node_coordinates, currentLine[3]);
                long node_id = db.insert(DbHelper.Table_Node, null, values);
                Log.i(TAG, "Node line inserted, id: " + node_id);
            }
        }
        catch (FileNotFoundException e){
            Log.i(TAG, "File not found!");
            e.printStackTrace();
        }
        catch (IOException e){
            Log.i(TAG, "IO error!");
            e.printStackTrace();
        }
    }

    public void insertDataBuildings(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(DbHelper.Building_name, "Lafferre");
        db.insert(DbHelper.Table_Building, null, values);
        values.put(DbHelper.Building_name, "Engineering Building West");
        db.insert(DbHelper.Table_Building, null, values);
        Log.i(TAG, "Buildings inserted!");
    }
}


