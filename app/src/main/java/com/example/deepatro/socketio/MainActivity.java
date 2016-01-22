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


public class MainActivity extends ActionBarActivity {
    EditText mNameText;
    EditText mHostname;
    private String host;
    private String username;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check for Previous Saved Settings
        LoadLogin();
        //Otherwise ask for New settings
        Log.d("PREV USER", username);
        Log.d("PREV HOST", host);
        if (username.isEmpty() || host.isEmpty()){
            init();

        }
        startService(new Intent(this, SocketListenerService.class));
        setContentView(R.layout.activity_welcome);
        Button edit_login = (Button) findViewById(R.id.edit_login);
        edit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Settings
                init();
            }
        });

    }

    private void init() {
        setContentView(R.layout.activity_login);
        mNameText = (EditText) findViewById(R.id.username);
        mHostname = (EditText) findViewById(R.id.hostname);

        Button submit = (Button) findViewById(R.id.enter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Settings
                SaveLogin("Username", mNameText.getText().toString());
                SaveLogin("Hostname", mHostname.getText().toString());
                LoadLogin();
                Log.d("SAVED HOST", host);
                Log.d("SAVED USER", username);
            }
        });
    }
    private void LoadLogin(){
        //load anything previously saved, otherwise return None
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        username = sp.getString("Username", "");
        host = sp.getString("Hostname", "");
    }

    private void SaveLogin(String key, String value) {
        //Overwrite any previously saved settings
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
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
