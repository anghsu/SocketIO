package com.example.deepatro.socketio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketListenerService extends Service {
    public SocketListenerService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("socket instance", "connecting");
        socket.connect();
        socket.on("reservations", notifyIncomingMessages);

        return Service.START_REDELIVER_INTENT;
    }

    private Socket socket;
    {
        try {
            socket = IO.socket("http://laas-greggmi.cisco.com:8000");
            Log.d("socket instance", "created");
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
