package com.example.deepatro.socketio;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketListenerService extends Service {
    private SharedPreferences sp;
    private String host;
    private Socket socket;

    public SocketListenerService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        host = sp.getString("Hostname", "");
        Log.d("Socket host", host);
        Log.d("socket instance", "connecting");
        startSocket();
        socket.connect();
        socket.on("reservations", notifyIncomingMessages);
        return Service.START_REDELIVER_INTENT;
    }

    private void startSocket()
    {
        try {
            String url = "http://" + host + ":8000";
            socket = IO.socket(url);
            Log.d("socket instance", "created " + url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Emitter.Listener notifyIncomingMessages = new Emitter.Listener(){
        @Override
        public  void  call(final Object... args){

                JSONObject data = (JSONObject) args[0];
                Log.i("json is", data.toString());
                String message;
                try {
                    message = data.getString("message");

                } catch (JSONException e) {
                    return;
                }
                Log.d("message is", message);

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
