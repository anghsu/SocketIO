package com.example.deepatro.socketio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private SharedPreferences sp;
    TextView logdump;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Button edit_login = (Button) findViewById(R.id.edit_login);
        edit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Settings
                Log.d("clicked edit", "");
                startActivity(new Intent(getApplicationContext(), LoginInfo.class));

            }
        });

        final Button view_log = (Button) findViewById(R.id.viewlog);
        view_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clicked view", "");
                setContentView(R.layout.activity_log);
                logdump = (TextView) findViewById(R.id.logdump);
                //store logdump to be dump of file
                logdump.setText("BLAHBALHBLAHBHLAHBAL");
            }
        });
    }

    protected void onStart(Bundle savedInstanceState) {
        Log.d("START", "S");
        super.onStart();
        //startActivity(new Intent(this, LoginInfo.class));
    }

    protected void onResume(Bundle savedInstanceState) {
        Log.d("RESUME", "S");
        super.onResume();
        //startActivity(new Intent(this, LoginInfo.class));
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
}
