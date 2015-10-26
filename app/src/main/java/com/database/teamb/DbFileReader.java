package com.database.teamb;

//file IO includes
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public String[] generateSQLInsertRooms(Context context, SQLiteDatabase db){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("LafferreRooms.csv")));
            while ((line = reader.readLine()) != null){
                String[] currentLine = line.split(csvSplitBy);
                if(currentLine[0].equals(" ")){
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.Type, currentLine[1]);
                    values.put(DbHelper.Node_id, currentLine[2]);
                    db.insert(DbHelper.Table_Room, null, values);
                    Log.i(TAG, "Room with no room_number inserted.");
                }
                else if(currentLine[1].equals(" ")){
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.Room_number, currentLine[0]);
                    values.put(DbHelper.Node_id, currentLine[2]);
                    db.insert(DbHelper.Table_Room, null, values);
                    Log.i(TAG, "Room with no type inserted");
                }
                else if(currentLine[2].equals(" ")){
                    Log.wtf(TAG, "No node_id for a room!");
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(DbHelper.Room_number, currentLine[0]);
                    values.put(DbHelper.Type, currentLine[1]);
                    values.put(DbHelper.Node_id, currentLine[2]);
                    db.insert(DbHelper.Table_Room, null, values);
                    Log.i(TAG, "Room with all fields inserted");
                }
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
        return insertList;
    }


    public String[] generateSQLInsertNodes(Context context, SQLiteDatabase db){
        int index = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("LafferreNodes.csv")));
            while ((line = reader.readLine()) != null){
                String[] currentLine = line.split(csvSplitBy);
                insertList[index] = "INSERT INTO table Node(floor,building_id,reachable_nodes,coordinates) VALUES (" + currentLine[0] + ", " + currentLine[1] + ", " + currentLine[2] + ");";
                index++;
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
        return insertList;
    }
}


