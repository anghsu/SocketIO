package com.example.deepatro.socketio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {
    private SharedPreferences sp;

    private ArrayAdapter<String> adapt;
    private ArrayList<String> logArray;
    private String filename = "laasng_notify.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Button edit_login = (Button) findViewById(R.id.edit_login);
        final Button view_log = (Button) findViewById(R.id.viewlog);
        edit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Settings
                Log.d("clicked edit", "clicked edit");
                startActivity(new Intent(getApplicationContext(), LoginInfo.class));

            }
        });



        view_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked view", "clicked view");
                setContentView(R.layout.activity_log);
                ListView loglist = (ListView) findViewById(R.id.loglist);
                final Button clear_log = (Button) findViewById(R.id.clearLog);

                logArray = populate_list(filename);

                adapt = new ArrayAdapter<String>( loglist.getContext(), android.R.layout.simple_list_item_1, logArray);
                loglist.setAdapter(adapt);

                clear_log.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Clear Log
                        try{
                            BufferedOutputStream buf = new BufferedOutputStream(openFileOutput(filename, Context.MODE_PRIVATE));
                            buf.write(("").getBytes());
                            buf.flush();
                            buf.close();
                        }catch (IOException e){
                            Log.d("IOError", e.getMessage());
                        }
                        ListView loglist = (ListView) findViewById(R.id.loglist);
                        logArray = populate_list(filename);
                        adapt = new ArrayAdapter<String>( loglist.getContext(), android.R.layout.simple_list_item_1, logArray);
                        loglist.setAdapter(adapt);

                    }
                });

            }
        });
    }

    private ArrayList<String> populate_list(String filename){
        try {
            logArray=new ArrayList<String>();

            BufferedInputStream buf = new BufferedInputStream(openFileInput(filename));
            Scanner scan = new Scanner(buf).useDelimiter(",|\\n");

            while (scan.hasNext()){
                Long time = Long.parseLong(scan.next());
                String topo_name = scan.next();
                String event = scan.next();
                String user = scan.next();
                logArray.add(topo_name +" "+event+" "+user+" ");
            }

            return logArray;
        }catch (IOException e) {
            Log.d("IOError", e.getMessage());
            return logArray;
        }
    }

    protected void onStart(Bundle savedInstanceState) {
        Log.d("START", "S");
        super.onStart();
    }

    protected void onResume(Bundle savedInstanceState) {
        Log.d("RESUME", "S");
        super.onResume();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Log.d("message", "backpressed");
        startActivity(new Intent(this, MainActivity.class));
    }
}
