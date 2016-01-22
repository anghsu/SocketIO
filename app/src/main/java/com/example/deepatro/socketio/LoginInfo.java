package com.example.deepatro.socketio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by anghsu on 1/21/16.
 */
public class LoginInfo extends Activity {
    EditText mNameText;
    EditText mHostname;
    private String host;
    private String username;
    private SharedPreferences sp;
    //Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check for Previous Saved Settings
        LoadLogin();
        //Otherwise ask for New settings
        Log.d("PREV USER", username);
        Log.d("PREV HOST", host);
        //i = new Intent(getApplicationContext(), SocketListenerService.class);
        if (username.isEmpty() || host.isEmpty()){
            Log.d("EMPTY", "E");
            init();
        }
        setContentView(R.layout.activity_welcome);
        Button edit_login = (Button) findViewById(R.id.edit_login);
        edit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Settings
                Log.d("NEW", "N");

                init();

            }
        });
        //startService(i);
        Log.d("HERE", "H");
        finish();

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

                //stopService(i);
                //startService(i);
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
    public void onDestroy() {
        super.onDestroy();
    }
}
