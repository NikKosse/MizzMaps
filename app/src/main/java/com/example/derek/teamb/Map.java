package com.example.derek.teamb;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteException;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.database.teamb.DbHelper;
import com.davemorrissey.labs.subscaleview.ImageSource;

public class Map extends Activity {

    Spinner spinner, spinnerFloor;

    CustomView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<GetObject> myAdapter;

    // for database operations
    DbHelper databaseH;

    final String[] locatation = {""};
    final String[] selectedRoom = {""};
    String currentMap = "lvl0";
    Float[] blueCoords = {0f, 0f, -1f};
    Float[] redCoords = {0f, 0f, -1f};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final PinView imageView = (PinView) findViewById(R.id.imageView);


        final Dialog dialog = new Dialog(Map.this);

        dialog.setContentView(R.layout.position_dialog);

        dialog.show();

        final Button insideButton = (Button) dialog.findViewById(R.id.btnInside);
        final Button outsideButton = (Button) dialog.findViewById(R.id.btnOutside);
        final Button changeButton = (Button) findViewById(R.id.btnChange);
        final TextView locationText = (TextView) findViewById(R.id.txtLocation);

        outsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        insideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.setContentView(R.layout.inside_location_dialog);
                dialog.show();
                final Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);


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
                            locationText.setText(locatation[0]);
                            blueCoords = databaseH.getXY(locatation[0]);
                            switch (Math.round(blueCoords[2])) {
                                case 0:
                                    imageView.setImage(ImageSource.asset("lvl0.png"));
                                    currentMap = "1v10";
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==1){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }
                                    spinnerFloor.setSelection(0);
                                    break;
                                case 1:
                                    imageView.setImage(ImageSource.asset("lvl1.png"));
                                    currentMap = "1v11";
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==1){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }
                                    spinnerFloor.setSelection(1);
                                    break;
                                case 2:
                                    imageView.setImage(ImageSource.asset("lvl2.png"));
                                    currentMap = "1v12";
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==1){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }
                                    spinnerFloor.setSelection(2);
                                    break;
                                case 3:
                                    imageView.setImage(ImageSource.asset("lvl3.png"));
                                    currentMap = "1v13";
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==1){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }
                                    spinnerFloor.setSelection(3);
                                    break;
                            }
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

                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });


        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.inside_location_dialog);
                dialog.show();
                final Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
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
                            locationText.setText(locatation[0]);
                            blueCoords = databaseH.getXY(locatation[0]);
                            switch (Math.round(blueCoords[2])) {
                                case 0:
                                    imageView.setImage(ImageSource.asset("lvl0.png"));
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==0){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }else{
                                        imageView.setRedPin(null);
                                    }
                                    spinnerFloor.setSelection(0);
                                    break;
                                case 1:
                                    imageView.setImage(ImageSource.asset("lvl1.png"));
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==1){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }else{
                                        imageView.setRedPin(null);
                                    }
                                    spinnerFloor.setSelection(1);
                                    break;
                                case 2:
                                    imageView.setImage(ImageSource.asset("lvl2.png"));
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==2){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }else{
                                        imageView.setRedPin(null);
                                    }
                                    spinnerFloor.setSelection(2);
                                    break;
                                case 3:
                                    imageView.setImage(ImageSource.asset("lvl3.png"));
                                    imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                                    if(Math.round(redCoords[2])==3){
                                        imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                                    }else{
                                        imageView.setRedPin(null);
                                    }
                                    spinnerFloor.setSelection(3);
                                    break;
                            }
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

                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });


        String[] items = new String[]{"Ground", "First", "Second", "Third"};
        spinner = (Spinner) findViewById(R.id.spinnerRooms);
        spinnerFloor = (Spinner) findViewById(R.id.spinnerFloor);
        spinnerFloor.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        // Loading spinner data from database
        final DbHelper db = new DbHelper(getApplicationContext());
        int check = 0;
        try {
            // Spinner Drop down elements
            List<String> rooms = db.getRooms();

            if(rooms.size()!=0) {

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, rooms);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
                        selectedRoom[0] = parent.getItemAtPosition(position).toString();
                        Log.i("SELECTED Room WAS------->", selectedRoom[0]);
                        redCoords = db.getXY(selectedRoom[0]);
                        imageView.setRedPin(new PointF(-1f,-1f));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        String getRed = parent.getItemAtPosition(0).toString();
                        redCoords = db.getXY(getRed);
                    }
                });


                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);
                check = 1;
            }
        } catch (SQLiteException e) {

        }
        if (check == 0) {
            // Spinner Drop down elements
            List<String> roooms = db.getStorage();

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, roooms);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
                    selectedRoom[0] = parent.getItemAtPosition(position).toString();
                    Log.i("SELECTED Room WAS------->", selectedRoom[0]);
                    redCoords = db.getXY(selectedRoom[0]);
                    switch (Math.round(redCoords[2])) {
                        case 0:
                            if (currentMap == "1v10") {
                                imageView.setImage(ImageSource.asset("lvl0.png"));
                                imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));

                                break;
                            }
                        case 1:
                            if (currentMap == "1v11") {
                                imageView.setImage(ImageSource.asset("lvl1.png"));
                                imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));

                                break;
                            }
                        case 2:
                            if (currentMap == "1v12") {
                                imageView.setImage(ImageSource.asset("lvl2.png"));
                                imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));

                                break;
                            }
                        case 3:
                            if (currentMap == "1v13") {
                                imageView.setImage(ImageSource.asset("lvl3.png"));
                                imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));

                                break;
                            }

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    String getRed = parent.getItemAtPosition(0).toString();
                    redCoords = db.getXY(getRed);
                }
            });

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);

        }

        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        currentMap = "1v10";
                        imageView.setImage(ImageSource.asset("lvl0.png"));
                        if (Math.round(blueCoords[2]) != 0){
                            imageView.setPinBlue(null);
                        }else{
                            imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                        }
                        if (Math.round(redCoords[2]) != 0){
                            imageView.setRedPin(null);
                        }else{
                            imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                        }
                        break;
                    case 1:
                        currentMap = "1v11";
                        imageView.setImage(ImageSource.asset("lvl1.png"));
                        if (Math.round(blueCoords[2]) != 1){
                            imageView.setPinBlue(null);
                        }else{
                            imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));
                        }
                        if (Math.round(redCoords[2]) != 1){
                            imageView.setRedPin(null);
                        }else{
                            imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                        }

                        break;
                    case 2:
                        currentMap = "1v12";
                        imageView.setImage(ImageSource.asset("lvl2.png"));
                        if (Math.round(blueCoords[2]) != 2){
                            imageView.setPinBlue(null);
                        }else{
                            imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));

                        }
                        if (Math.round(redCoords[2]) != 2){
                            imageView.setRedPin(null);
                        }else{
                            imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                        }
                        break;
                    case 3:
                        currentMap = "1v13";
                        imageView.setImage(ImageSource.asset("lvl3.png"));
                        if (Math.round(blueCoords[2]) != 3){
                            imageView.setPinBlue(null);
                        }else{
                            imageView.setPinBlue(new PointF(blueCoords[0], blueCoords[1]));

                        }
                        if (Math.round(redCoords[2]) != 3){
                            imageView.setRedPin(null);
                        }else{
                            imageView.setRedPin(new PointF(redCoords[0], redCoords[1]));
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }




}
