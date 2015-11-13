package com.database.teamb;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Models.Node;

import java.sql.SQLException;
import java.util.ArrayList;
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
            DbHelper.Node_coordinates
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

    public List<Node> getPathfinderNodes(long building_id, HashMap<Long, Integer> idMap) {
        //TODO: only query for nodes in the proper floors of this building (and make necessary changes in Pathfinder (check for missing nodes and retrieve proper floors before query))

        List<Node> nodes = new ArrayList<>();
        String whereClause = DbHelper.Building_id + "=?";
        String[] whereArgs = { String.valueOf(building_id)};
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
                nodes.add(node);
                idMap.put(node.getNode_id(), index++);
                Log.i(TAG, "Retrieved row with id:" + node.getNode_id());
            }
        }
        Log.i(TAG, "Size of results is: " + nodes.size());
        cursor.close();
        return nodes;
    }
}
