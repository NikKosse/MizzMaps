package com.example.derek.teamb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.database.teamb.DbHelper;

public class MainActivity extends Activity {

    CustomView myAutoComplete;

    ClassList myClass;

    // adapter for auto-complete
    ArrayAdapter<MyObject> myAdapter;

    // for database operations
    DbHelper databaseH;

    // just to add some initial value
    String[] item = new String[] {"Please search..."};


int i=0;
    String [] classList= new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            // instantiate database handler
            databaseH = new DbHelper(MainActivity.this);


            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomView) findViewById(R.id.myautocomplete);

            myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                    RelativeLayout rl = (RelativeLayout) arg1;
                    TextView tv = (TextView) rl.getChildAt(0);
                    TextView testText = (TextView) findViewById(R.id.testText);

                    classList[i] = (tv.getText().toString());
                    i++;

                   // myClass.getClass(classList,i);


                    testText.setText(classList[i-1]);



                }

            });


            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new Listener(this));

            // ObjectItemData has no value at first
            MyObject[] ObjectItemData = new MyObject[0];

            // set our adapter
            myAdapter = new AutocompleteCustomArrayAdapter(this, R.layout.list_view_row, ObjectItemData);
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


}