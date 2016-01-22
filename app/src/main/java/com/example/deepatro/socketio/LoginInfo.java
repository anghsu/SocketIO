package com.example.deepatro.socketio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Check for Previous Saved Settings
        LoadLogin();
        //Otherwise ask for New settings
        Log.d("PREV USER", username);
        Log.d("PREV HOST", host);
        i = new Intent(this, SocketListenerService.class);
        mNameText = (EditText) findViewById(R.id.username);
        mHostname = (EditText) findViewById(R.id.hostname);
        if (!username.isEmpty() && !host.isEmpty()){
            mNameText.setText(username);
            mHostname.setText(host);
            startService(i);
        }

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

                stopService(i);
                startService(i);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



    }

    public void onStart(Bundle savedInstance) {

    }

    private void init() {

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


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Log.d("message", "backpressed");
        startActivity(new Intent(this, MainActivity.class));
    }
}
