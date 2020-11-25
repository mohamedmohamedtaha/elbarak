package com.muslimcharityapps.elbarak.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

import com.muslimcharityapps.elbarak.utils.Values;

/**
 * Created by Dhairya_Creative on 21-12-2016.
 */

public class MyIntentService extends IntentService implements Values {

    public MyIntentService() {
        super("Play");
    }

    private MediaPlayer mp;

    @Override
    protected void onHandleIntent(Intent intent) {

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RECEIVER_EVENTS_FROM_NOTIFICATION);
        /*broadcastIntent.setAction(intent.getAction());*/
        broadcastIntent.putExtra(INTENT_ACTION, intent.getAction());
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
    }
}

