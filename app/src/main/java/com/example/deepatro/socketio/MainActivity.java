package com.example.deepatro.socketio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class MainActivity extends ActionBarActivity {
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, LoginInfo.class);
        startActivity(i);
        sp = getSharedPreferences("sp", MODE_PRIVATE);

        Log.d("Socket host", sp.getString("Hostname", ""));
        Log.d("Socket user", sp.getString("Username", ""));
        startService(new Intent(this, SocketListenerService.class));


        Intent i = new Intent(this, LoginInfo.class);
        startActivity(i);
        sp = getSharedPreferences("sp", MODE_PRIVATE);

        Log.d("Socket host", sp.getString("Hostname", ""));
        Log.d("Socket user", sp.getString("Username", ""));
        try {
            socket = IO.socket("http://" + sp.getString("Hostname", "") + ":8000");
            Log.d("socket instance", "created");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
