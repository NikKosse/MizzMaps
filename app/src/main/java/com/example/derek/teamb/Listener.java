package com.example.derek.teamb;

/**
 * Created by Derek on 10/4/2015.
 */
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class Listener implements TextWatcher{

    public static final String TAG = "Listener.java";
    Context context;

    public Listener(Context context){
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

            MainActivity mainActivity = ((MainActivity) context);

            // update the adapater
            mainActivity.myAdapter.notifyDataSetChanged();

            // get suggestions from the database
            MyObject[] myObjs = mainActivity.databaseH.read(userInput.toString());

            // update the adapter
            mainActivity.myAdapter = new CustomArrayAdapter(mainActivity, R.layout.list_view_row, myObjs);

            mainActivity.myAutoComplete.setAdapter(mainActivity.myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}