package com.example.derek.teamb;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.database.teamb.DbHelper;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayAdapter<String> adapter;
    ListView list;
    ArrayList<String> arrayList;

    CustomView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<GetObject> myAdapter;

    // for database operations
    DbHelper databaseH;

    // just to add some initial value
    String[] item = new String[]{"Please search..."};

    int i = 0;
    String[] classList = new String[5];
    private final String TAG = "MainActivity";
    LocationService locationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        final Context context = this;
        locationService = new LocationService(context);
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
                        showDialog();
                    }
                }

            });


            // add the listener so it will tries to suggest while the user types
            myAutoComplete.addTextChangedListener(new Listener(this));

            // ObjectItemData has no value at first
            GetObject[] ObjectItemData = new GetObject[0];

            // set our adapter
            myAdapter = new CustomArrayAdapter(this, R.layout.list_view_row, ObjectItemData);
            myAutoComplete.setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.delete_class_dialog);
                dialog.show();

                final Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
                Button deleteButton = (Button) dialog.findViewById(R.id.btnOk);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();
                        i = arrayList.size();
                        dialog.cancel();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        };

        list.setOnItemClickListener(onItemClickListener);

        final Dialog save = new Dialog(MainActivity.this);
        save.setContentView(R.layout.save_classes);

        final Button noButton = (Button) save.findViewById(R.id.btnNo);
        final Button yesButton = (Button) save.findViewById(R.id.btnYes);

        final Button btnNext = (Button) findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String inOrOut = locationService.checkIfInside();
                if (arrayList.size() != 0) {
                    databaseH.deleteRoomData();
                    for (int i = 0; i < arrayList.size(); i++) {
                        databaseH.create(new GetObject(arrayList.get(i)));
                    }
                    save.show();
                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            save.cancel();
                            if(inOrOut.equals("Lafferre")) {
                                Log.d(TAG, "Inside Laffere!");
                                Intent intent = new Intent(v.getContext(), Map.class);
                                startActivityForResult(intent, 0);
                            }
                            else{
                                Log.d(TAG, "Outside Lafferre!");
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=Lafferre+Hall+Columbia+Missouri");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
//                                Intent intent = new Intent(v.getContext(), MapsActivity.class);
//                                startActivityForResult(intent, 0);
                            }
                        }
                    });

                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseH.deleteStoreData();
                            for (int i = 0; i < arrayList.size(); i++) {
                                databaseH.store(new GetObject(arrayList.get(i)));
                                System.out.println(arrayList.get(i));
                            }
                            if(inOrOut.equals("Lafferre")) {
                                Log.d(TAG, "Inside Laffere!");
                                Intent intent = new Intent(v.getContext(), Map.class);
                                startActivityForResult(intent, 0);
                            }
                            else{
                                Log.d(TAG, "Outside Lafferre!");
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=Lafferre+Hall+Columbia+Missouri");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
//                                Intent intent = new Intent(v.getContext(), MapsActivity.class);
//                                startActivityForResult(intent, 0);
                            }
                        }
                    });

                }else {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.no_class_dialog);
                    dialog.show();

                    final Button cancelButton = (Button) dialog.findViewById(R.id.btnOk);

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Enter a class by using the Search Bar", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                }
            }
        });


    }

//    public void goToOutsideMap(View view){
//        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);
//    }

    public void showDialog() {

        CustomAlertDelete customAlertDelete = new CustomAlertDelete();
        customAlertDelete.show(getFragmentManager(), "My Alert");

    }



}