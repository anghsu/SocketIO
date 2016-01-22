package com.example.deepatro.socketio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check for Previous Saved Settings
        LoadLogin();
        //Otherwise ask for New settings
        Log.d("PREV USER", username);
        Log.d("PREV HOST", host);
        if (username.isEmpty() || host.isEmpty()){
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
                    return;
                }
            });
        }
        finish();
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

}
