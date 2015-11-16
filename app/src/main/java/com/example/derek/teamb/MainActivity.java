package com.example.derek.teamb;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.database.teamb.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ArrayAdapter<String> adapter;
    ListView list;
    ArrayList<String> arrayList;

    CustomView myAutoComplete;

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

        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();

        try {

            // instantiate database handler
            databaseH = new DbHelper(MainActivity.this);


            adapter = new ArrayAdapter<String>(this, R.layout.list_black_text, R.id.list_content, arrayList);
            list.setAdapter(adapter);

            // autocompletetextview is in activity_main.xml
            myAutoComplete = (CustomView) findViewById(R.id.myautocomplete);

            myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                    RelativeLayout rl = (RelativeLayout) arg1;
                    TextView tv = (TextView) rl.getChildAt(0);

                    if (i != 5) {
                        classList[i] = (tv.getText().toString());
                        arrayList.add(classList[i]);
                        adapter.notifyDataSetChanged();
                        i++;

                    } else {
                        //TODO
                    }
                }

            });


            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new Listener(this));

            // ObjectItemData has no value at first
            MyObject[] ObjectItemData = new MyObject[0];

            // set our adapter
            myAdapter = new CustomArrayAdapter(this, R.layout.list_view_row, ObjectItemData);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Button btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (arrayList.size() != 0) {
                    databaseH.deleteAllData();
                    for (int i = 0; i < arrayList.size(); i++) {
                        databaseH.create(new MyObject(arrayList.get(i)));
                    }
                    Intent intent = new Intent(v.getContext(), Map.class);
                    startActivityForResult(intent, 0);

                } else {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.no_class_dialog);
                    dialog.show();

                    final Button cancelButton = (Button)dialog.findViewById(R.id.btnOk);

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            }
        });



    }

    public void showDialog(){

        MyAlert myAlert = new MyAlert();
        myAlert.show(getFragmentManager(), "My Alert");




    }






}