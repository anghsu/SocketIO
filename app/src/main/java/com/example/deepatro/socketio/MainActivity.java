package com.example.deepatro.socketio;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends ActionBarActivity {


    private Socket socket ;
    {
        try{
            socket = IO.socket("http://laas-deepatro.cisco.com:8000");
            Log.d("socket instance", "created");
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("socket instance", "connecting");
        socket.connect();
        socket.on("reservations", notifyIncomingMessages);
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

    private Emitter.Listener notifyIncomingMessages = new Emitter.Listener(){
        @Override
        public  void  call(final Object... args){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try {
                        message = data.getString("message");

                    } catch (JSONException e) {
                        return;
                    }
                    Log.d("message is", message);
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
