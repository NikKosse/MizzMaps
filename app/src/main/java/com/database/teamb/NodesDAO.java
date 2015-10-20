package com.database.teamb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.sql.SQLException;

public class NodesDAO {
    public static final String TAG = "NodesDAO";

    private SQLiteDatabase database;
    private DbHelper nodesDbHelper;
    private Context nodesContext;
    private String[] allColumns = { DbHelper.Node_ID, DbHelper.Reachable_Nodes, DbHelper.Floor, DbHelper.Building_ID, DbHelper.Coordinates };

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
}
