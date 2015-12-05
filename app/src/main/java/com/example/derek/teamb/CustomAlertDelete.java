package com.example.derek.teamb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Derek on 11/15/2015.
 */
public class CustomAlertDelete extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("Too Many Classes");
        builder.setMessage("Only five classes allowed");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Click a class in order to delete", Toast.LENGTH_LONG).show();
            }
        });
        Dialog dialog = builder.create();


        return dialog;
    }


}
