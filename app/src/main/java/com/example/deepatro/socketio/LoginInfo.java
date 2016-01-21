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
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check for Previous Saved Settings
        Log.d("here", "FD");
        LoadLogin();
        Log.d("FLJ", "DF");
        //Otherwise ask for New settings
        if (mNameText.getText().toString().isEmpty() || mHostname.getText().toString().isEmpty()){
            setContentView(R.layout.activity_login);
            mNameText = (EditText) findViewById(R.id.username);
            mHostname = (EditText) findViewById(R.id.Hostname);

            Button submit = (Button) findViewById(R.id.enter);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Save Settings
                    SaveLogin("Username", mNameText.getText().toString());
                    SaveLogin("Hostname", mHostname.getText().toString());
                }
            });
        }
    }

    private void LoadLogin(){
        //load anything previously saved, otherwise return None
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        mNameText.setText(sp.getString("Username", ""));
        mHostname.setText(sp.getString("Hostname", ""));
    }

    private void SaveLogin(String key, String value) {
        //Overwrite any previously saved settings
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
}
