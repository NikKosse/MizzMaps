package com.database.teamb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.Models.Building;
import com.Models.Node;
import com.Models.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interfacing with the database
 */
public class DataSource {

    public static final String TAG = "DataSource";

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;
    private static final String[] allBuildingColumns = {
            DbHelper.Building_id,
            DbHelper.Building_name
    };
    private static final String[] allRoomColumns = {
            DbHelper.Room_id,
            DbHelper.Room_number,
            DbHelper.Type,
            DbHelper.Node_id
    };
    private static final String[] allNodeColumns = {
            DbHelper.Node_id,
            DbHelper.Floor,
            DbHelper.Building_id,
            DbHelper.Reachable_Nodes,
            DbHelper.Coordinates
    };

    public DataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open(){
        Log.i(TAG, "database opened.");
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        Log.i(TAG, "database closed.");
        database.close();
    }

    public List<Building> getAllBuildings(){
        List<Building> buildings = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.Table_Building, allBuildingColumns, null, null, null, null, null);

        Log.i(TAG, "Returned " + cursor.getColumnCount() + " rows");
        if(cursor.getColumnCount() >0){
            while (cursor.moveToNext()){
                Building building = new Building();
                building.setBuilding_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Building_id)));
                building.setBuilding_name(cursor.getString(cursor.getColumnIndex(DbHelper.Building_name)));
                buildings.add(building);
                Log.i(TAG, "Retrieved row with id:" + building.getBuilding_id());
            }
        }
        Log.i(TAG, "Size of results is: " + buildings.size());
        cursor.close();
        return buildings;
    }

    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.Table_Node, allNodeColumns, null, null, null, null, null);

        Log.i(TAG, "Returned " + cursor.getColumnCount() + " rows");
        if (cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Node node = new Node();
                node.setNode_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Node_id)));
                node.setFloor(cursor.getInt(cursor.getColumnIndex(DbHelper.Floor)));
                node.setBuildingId(cursor.getInt(cursor.getColumnIndex(DbHelper.Building_id)));
//                node.setReachable_nodes(cursor.getBlob(cursor.getColumnIndex(DbHelper.Reachable_Nodes)));//TODO figure out how to deal with the blob
                nodes.add(node);
                Log.i(TAG, "Retrieved row with id:" + node.getNode_id());
            }
        }
        Log.i(TAG, "Size of results is: " + nodes.size());
        cursor.close();
        return nodes;
    }

        public List<Room> getAllRooms() {
            List<Room> rooms = new ArrayList<>();

            Cursor cursor = database.query(DbHelper.Table_Room, allRoomColumns, null, null, null, null, null);

            Log.i(TAG, "Returned " + cursor.getColumnCount() + " rows");
            if (cursor.getColumnCount() > 0) {
                while (cursor.moveToNext()) {
                    Room room = new Room();
                    room.setRoom_number(cursor.getString(cursor.getColumnIndex(DbHelper.Room_number)));
                    room.setType(cursor.getString(cursor.getColumnIndex(DbHelper.Type)));
                    room.setNode_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Node_id)));
                    room.setRoom_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Room_id)));
                    rooms.add(room);
                    Log.i(TAG, "Retrieved row with id:" + room.getRoom_id());
                }
            }
            Log.i(TAG, "Size of results is: " + rooms.size());
            cursor.close();
            return rooms;
        }
}
