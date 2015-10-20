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

//    String csvFilePath = "C:\\Users\\nikol\\Documents\\LafferreRooms.csv";
    String line;
    String csvSplitBy = ",";
    String sqlInsert;
    String[] insertList;
    int index = 0;

    public String[] generateSQLInsert(String csvFilePath){

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null){

                String[] currentLine = line.split(csvSplitBy);
                if(currentLine[0].equals("")){
                    sqlInsert = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[1] + ", " + currentLine[2] + ");";
                    insertList[index] = sqlInsert;
                }
                else if(currentLine[1].equals("")){
                    sqlInsert = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[0] + ", "+ currentLine[2] + ");";
                    insertList[index] = sqlInsert;
                }
                else if(currentLine[2].equals("")){
                    sqlInsert = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[0] + ", " + currentLine[1] + ");";
                    insertList[index] = sqlInsert;
                }
                else {
                    sqlInsert = "INSERT INTO table Room(room_number,type,node_id) VALUES (" + currentLine[0] + ", " + currentLine[1] + ", " + currentLine[2] + ");";
                    insertList[index] = sqlInsert;
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
}


