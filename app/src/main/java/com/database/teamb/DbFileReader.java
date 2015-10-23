package com.database.teamb;

//database includes
import android.database.sqlite.SQLiteDatabase;

//file IO includes
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads in CSV file and inserts the data into the database
 */
public class DbFileReader {

    public DbFileReader() {
    }

    String line;
    String csvSplitBy = ",";
    String[] insertList;

    public String[] generateSQLInsertRooms(String csvFilePath){
        int index = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null){
                String[] currentLine = line.split(csvSplitBy);
                if(currentLine[0].equals("")){
                    insertList[index] = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[1] + ", " + currentLine[2] + ");";
                }
                else if(currentLine[1].equals("")){
                    insertList[index] = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[0] + ", "+ currentLine[2] + ");";
                }
                else if(currentLine[2].equals("")){
                    insertList[index] = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[0] + ", " + currentLine[1] + ");";
                }
                else {
                    insertList[index] = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[0] + ", " + currentLine[1] + ", " + currentLine[2] + ");";
                }
                index++;
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("IO error!");
            e.printStackTrace();
        }
        return insertList;
    }


    public String[] generateSQLInsertNodes(String csvFilePath){
        int index = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null){
                String[] currentLine = line.split(csvSplitBy);
                insertList[index] = "INSERT INTO table Node(floor,building_id,reachable_nodes,coordinates) VALUES (" + currentLine[0] + ", " + currentLine[1] + ", " + currentLine[2] + ");";
                index++;
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("IO error!");
            e.printStackTrace();
        }
        return insertList;
    }
}


