package com.database.teamb;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Models.Node;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NodesDAO {
    public static final String TAG = "NodesDAO";

    private SQLiteDatabase database;
    private DbHelper nodesDbHelper;
    private Context nodesContext;
    private final String[] allColumns = {
            DbHelper.Node_id,
            DbHelper.Node_floor,
            DbHelper.Building_id,
            DbHelper.Reachable_nodes,
            DbHelper.Node_coordinates,
            DbHelper.xNodeCoord,
            DbHelper.yNodeCoord
    };

    public NodesDAO(Context context){
        this.nodesContext = context;
        nodesDbHelper = new DbHelper(context);
        //opens database if unopenable throws error message
        try{
            open();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQL Database could not be opened " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void open() throws SQLException
    {
        database = nodesDbHelper.getWritableDatabase();
    }
    public void close(){
        nodesDbHelper.close();
    }

    public List<Node> getPathfinderNodes(long building_id, HashMap<Long, Integer> idMap, List<Integer> startAndEndFloor) {
        List<Node> nodes = new ArrayList<>();
        String whereClause = DbHelper.Building_id + "=? AND " + DbHelper.Node_floor + " BETWEEN ? AND ?";
        String[] whereArgs = { String.valueOf(building_id), String.valueOf(startAndEndFloor.get(0)), String.valueOf(startAndEndFloor.get(1))};
        int index = 0;

        Cursor cursor = database.query(DbHelper.Table_Node, allColumns, whereClause, whereArgs, null, null, null);

        Log.i(TAG, "Returned " + cursor.getColumnCount() + " rows");
        if (cursor.getColumnCount() > 0) {
            while (cursor.moveToNext()) {
                Node node = new Node();
                node.setNode_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Node_id)));
                node.setFloor(cursor.getInt(cursor.getColumnIndex(DbHelper.Node_floor)));
                node.setBuildingId(cursor.getInt(cursor.getColumnIndex(DbHelper.Building_id)));
                node.setAdjacencies(cursor.getString(cursor.getColumnIndex(DbHelper.Reachable_nodes)));
                node.setCoordinates(cursor.getString(cursor.getColumnIndex(DbHelper.Node_coordinates)));
                node.setxNodeCoord(cursor.getFloat(cursor.getColumnIndex(DbHelper.xNodeCoord)));
                node.setyNodeCoord(cursor.getFloat(cursor.getColumnIndex(DbHelper.yNodeCoord)));
                nodes.add(node);
                idMap.put(node.getNode_id(), index++);
                Log.i(TAG, "Retrieved row with id: " + node.getNode_id());
            }
        }
        Log.i(TAG, "Size of results is: " + nodes.size());
        cursor.close();
        return nodes;
    }

    public long getBuildingAndFloors(long startId, long goalId, List<Integer> startAndEndFloor) {
        String whereClause = DbHelper.Node_id + " IN (?,?)";
        String[] whereArgs = { String.valueOf(startId), String.valueOf(goalId)};
        String[] columns = {DbHelper.Node_floor, DbHelper.Building_id};

        List<Node> returnedNodes = new ArrayList<>();
        Cursor cursor = database.query(DbHelper.Table_Node, columns , whereClause, whereArgs, null, null, null);

        Log.i(TAG, "Returned " + cursor.getColumnCount() + " rows");
        if (cursor.getColumnCount() > 0 && cursor.getCount() == 2) {
            while (cursor.moveToNext()) {
                Node node = new Node();
                //node.setNode_id(cursor.getLong(cursor.getColumnIndex(DbHelper.Node_id)));
                node.setFloor(cursor.getInt(cursor.getColumnIndex(DbHelper.Node_floor)));
                node.setBuildingId(cursor.getInt(cursor.getColumnIndex(DbHelper.Building_id)));
                //node.setAdjacencies(cursor.getString(cursor.getColumnIndex(DbHelper.Reachable_nodes)));
                //node.setCoordinates(cursor.getString(cursor.getColumnIndex(DbHelper.Node_coordinates)));
                returnedNodes.add(node);
                //Log.i(TAG, "Retrieved row with id: " + node.getNode_id());
            }
        }
        cursor.close();

        if (returnedNodes.size() != 2) {
            Log.i(TAG, "Error retrieving start and goal nodes: " + startId + " and " + goalId);
            return -1;
        }
        if (returnedNodes.get(0).getBuildingId() != returnedNodes.get(1).getBuildingId()){
            Log.i(TAG, "The following nodes are in different buildings: " + startId + " and " + goalId);
            //TODO: route them to google maps stuff if this happens?
            return -1;
        }

        //set return values
        startAndEndFloor.add(returnedNodes.get(0).getFloor());
        startAndEndFloor.add(returnedNodes.get(1).getFloor());
        Collections.sort(startAndEndFloor);
        return returnedNodes.get(0).getBuildingId();
    }
}
