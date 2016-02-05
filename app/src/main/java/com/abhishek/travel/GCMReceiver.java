package com.abhishek.travel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by dell on 2/5/2016.
 */
public class GCMReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GCMReceiver","GCMReceiver");
        ComponentName comp = new ComponentName(context.getPackageName(), com.abhishek.travel.GCMService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
