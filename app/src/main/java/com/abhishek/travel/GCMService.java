package com.abhishek.travel;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.kinvey.android.push.KinveyGCMService;

/**
 * Created by dell on 2/5/2016.
 */
public class GCMService extends KinveyGCMService {
    @Override
    public void onMessage(String message) {
        Log.d("GCMService",message);
        displayNotification(message);
    }
    @Override
    public void onError(String error) {
        displayNotification(error);
    }
    @Override
    public void onDelete(String deleted) {
        Log.d("GCMService",deleted + " deleted");
        displayNotification(deleted);
    }
    @Override
    public void onRegistered(String gcmID) {
        Log.d("GCMService","gcm:"+gcmID);
        displayNotification(gcmID);
    }
    @Override
    public void onUnregistered(String oldID) {
        Log.d("GCMService","old:"+oldID);
        displayNotification(oldID);
    }
    //This method will return the WakefulBroadcastReceiver class you define in the next step
    public Class getReceiver() {
        return GCMReceiver.class;
    }
    private void displayNotification(String message){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.fab_background)
                .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                .setContentText(message);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
