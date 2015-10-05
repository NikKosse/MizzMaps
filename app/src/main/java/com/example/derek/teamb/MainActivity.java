package com.example.derek.teamb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.util.List;

import android.widget.ArrayAdapter;

import com.database.teamb.DbHelper;

public class MainActivity extends AppCompatActivity {

    CustomView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;

    // for database operations
    DbHelper databaseH;

    // just to add some initial value
    String[] item = new String[] {"Please search..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            // instantiate database handler
            databaseH = new DbHelper(MainActivity.this);

            // put sample data to database
            insertSampleData();

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomView) findViewById(R.id.myautocomplete);

            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new Listener(this));

            // set our adapter
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Button btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Map.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // this function is used in Listener.java
    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        List<MyObject> products = databaseH.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (MyObject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

    public void insertSampleData(){

        // CREATE
        databaseH.create( new MyObject("January") );
        databaseH.create( new MyObject("February") );
        databaseH.create( new MyObject("March") );
        databaseH.create( new MyObject("April") );
        databaseH.create( new MyObject("May") );
        databaseH.create( new MyObject("June") );
        databaseH.create( new MyObject("July") );
        databaseH.create( new MyObject("August") );
        databaseH.create( new MyObject("September") );
        databaseH.create( new MyObject("October") );
        databaseH.create( new MyObject("November") );
        databaseH.create( new MyObject("December") );
        databaseH.create( new MyObject("New Caledonia") );
        databaseH.create( new MyObject("New Zealand") );
        databaseH.create( new MyObject("Papua New Guinea") );
        databaseH.create( new MyObject("COFFEE-1K") );
        databaseH.create( new MyObject("coffee raw") );
        databaseH.create( new MyObject("authentic COFFEE") );
        databaseH.create( new MyObject("k12-coffee") );
        databaseH.create( new MyObject("view coffee") );
        databaseH.create( new MyObject("Indian-coffee-two") );

    }
}
