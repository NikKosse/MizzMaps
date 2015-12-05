package com.example.derek.teamb;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.database.teamb.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class Start extends AppCompatActivity {


    DbHelper databaseH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        databaseH = new DbHelper(Start.this);
        databaseH.deleteRoomData();


        Button btnStart = (Button) findViewById(R.id.buttonNext);
        Button btnLoad = (Button) findViewById(R.id.buttonContinue);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (v.getContext(),MainActivity.class );
                startActivityForResult(intent, 0);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                try {
                    List<String> labels = databaseH.getStorage();
                    if (labels.size() !=0) {
                        i = 1;
                    }else{
                        showDialog();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(i==1) {
                    Intent intent = new Intent(v.getContext(), Map.class);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    public void showDialog() {

        CustomAlertLoad customAlertLoad = new CustomAlertLoad();
        customAlertLoad.show(getFragmentManager(), "My Alert");

    }
}
