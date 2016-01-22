package com.example.deepatro.socketio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
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
    private int notifyId;
    private String user;
    private Socket socket;

    public SocketListenerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        notifyId = 1;
        user ="greggmi";
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        host = sp.getString("Hostname", "");
        Log.d("Socket host", host);
        startSocket();

        Log.d("socket instance", "connecting");
        socket.connect();
        socket.on("reservations", notifyIncomingMessages);

        return Service.START_REDELIVER_INTENT;
    }

    private void startSocket(){
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
                Log.w("json is", data.toString());
                String event, user_name, topo_name, message;
                try {
                    event = data.getString("event");
                    user_name = data.getString("user_name");
                    topo_name = data.getString("topo_name");
                } catch (JSONException e) {
                    Log.e("Value not found", e.getMessage());
                    return;
                }
                if (user.equals(user_name)) {
                    message = "Your topology, " + topo_name + ", has been " + event;
                    sendNotification(android.R.drawable.ic_menu_view, "LaasNG", message);
                }else{
                    Log.e("username not matching"+user, data.toString());
                }
        }
    };


    private void sendNotification(int icon, CharSequence title,  CharSequence notifyText){
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(android.R.drawable.ic_menu_view)
                        .setContentTitle(title)
                        .setContentText(notifyText);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notifyId ,mBuilder.build());
        notifyId ++;

    }


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
