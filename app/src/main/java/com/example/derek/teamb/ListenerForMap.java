package com.example.derek.teamb;

/**
 * Created by Derek on 10/4/2015.
 */
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class ListenerForMap implements TextWatcher{

    public static final String TAG = "Listener.java";
    Context context;

    public ListenerForMap(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        try{

            // if you want to see in the logcat what the user types
            Log.e(TAG, "User input: " + userInput);

            Map map = ((Map) context);

            // update the adapater
            map.myAdapter.notifyDataSetChanged();

            // get suggestions from the database
            GetObject[] myObjs = map.databaseH.read(userInput.toString());

            // update the adapter
            map.myAdapter = new CustomMapArrayAdapter(map, R.layout.list_view_row, myObjs);

            map.myAutoComplete.setAdapter(map.myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}