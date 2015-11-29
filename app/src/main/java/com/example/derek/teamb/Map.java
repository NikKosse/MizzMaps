package com.example.derek.teamb;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.database.teamb.DbHelper;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class Map extends Activity {

    Spinner spinner, spinnerFloor;

    CustomView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<GetObject> myAdapter;

    // for database operations
    DbHelper databaseH;



    final String[] locatation = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final PinView imageView = (PinView) findViewById(R.id.imageView);



        final Dialog dialog = new Dialog(Map.this);

        dialog.setContentView(R.layout.position_dialog);

        dialog.show();

        final Button insideButton = (Button) dialog.findViewById(R.id.btnInside);
        final Button changeButton = (Button) findViewById(R.id.btnChange);

        insideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.setContentView(R.layout.inside_location_dialog);
                dialog.show();
                try {

                    // instantiate database handler
                    databaseH = new DbHelper(Map.this);


                    // autocompletetextview is in activity_main.xml
                    myAutoComplete = (CustomView) dialog.findViewById(R.id.myautocomplete2);

                    myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                            RelativeLayout rl = (RelativeLayout) arg1;
                            TextView tv = (TextView) rl.getChildAt(0);
                            locatation[0] = (tv.getText().toString());
                            dialog.cancel();
                            Log.i("SELECTED TEXT WAS------->", locatation[0]);


                        }

                    });


                    // add the listener so it will tries to suggest while the user types
                    myAutoComplete.addTextChangedListener(new ListenerForMap(Map.this));

                    // ObjectItemData has no value at first
                    GetObject[] ObjectItemData = new GetObject[0];

                    // set our adapter
                        myAdapter = new CustomMapArrayAdapter(Map.this, R.layout.list_view_row, ObjectItemData);
                myAutoComplete.setAdapter(myAdapter);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.inside_location_dialog);
                dialog.show();
                try {

                    // instantiate database handler
                    databaseH = new DbHelper(Map.this);


                    // autocompletetextview is in activity_main.xml
                    myAutoComplete = (CustomView) dialog.findViewById(R.id.myautocomplete2);

                    myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                            RelativeLayout rl = (RelativeLayout) arg1;
                            TextView tv = (TextView) rl.getChildAt(0);
                            locatation[0] = (tv.getText().toString());
                            dialog.cancel();
                            Log.i("SELECTED TEXT WAS------->", locatation[0]);


                        }

                    });


                    // add the listener so it will tries to suggest while the user types
                    myAutoComplete.addTextChangedListener(new ListenerForMap(Map.this));

                    // ObjectItemData has no value at first
                    GetObject[] ObjectItemData = new GetObject[0];

                    // set our adapter
                    myAdapter = new CustomMapArrayAdapter(Map.this, R.layout.list_view_row, ObjectItemData);
                    myAutoComplete.setAdapter(myAdapter);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        String[] items = new String[]{"Ground", "First", "Second", "Third"};
        spinner = (Spinner) findViewById(R.id.spinnerRooms);
        spinnerFloor = (Spinner) findViewById(R.id.spinnerFloor);
        spinnerFloor.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        // Loading spinner data from database
        loadSpinnerData();

        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        imageView.setImage(ImageSource.asset("lvl0.png"));
                        break;
                    case 1:
                        imageView.setImage(ImageSource.asset("lvl1.png"));
                        imageView.setPin(new PointF(1718f, 581f));
                        break;
                    case 2:
                        imageView.setImage(ImageSource.asset("lvl2.png"));
                        break;
                    case 3:
                        imageView.setImage(ImageSource.asset("lvl3.png"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void loadSpinnerData() {
        // database handler
        DbHelper db = new DbHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> roooms = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roooms);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


}
