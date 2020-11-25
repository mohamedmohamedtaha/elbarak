package com.muslimcharityapps.elbarak;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

//import plusequalsto.com.plusequalstoapp.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    int count;
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FirebaseMessaging", "From: " + remoteMessage.getFrom());
        Log.d("FirebaseMessaging", "Message Notification Body: " + remoteMessage.getData());
        // Check if message contains a data payload.
        if (remoteMessage.getData() != null) {
            sendNotification(remoteMessage);
            count++;
            Log.d("FirebaseMessaging", "Notification Number: " + count);
            Intent intentCount = new Intent("NotificationCount");
            Bundle bundle = new Bundle();
            bundle.putInt("Count", count);
            intentCount.putExtras(bundle);
            broadcaster.sendBroadcast(intentCount);
        } else {
            count = 0;
            Log.d("FirebaseMessaging", "Notification Number: " + count);
        }
//        if (remoteMessage.getData().size() > 0) {
//            Log.d("FirebaseMessaging", "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }
        // Check if message contains a menu payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d("FirebaseMessaging", "Message Notification Body: " + remoteMessage.getData().size());
//        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String content = data.get("content");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "PlusEqualsTo";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "PlusEqualsToNotification",
                    NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("Plus Equals To");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_logo_test)
                .setContentTitle(title)
                .setContentTitle(content)
                .setContentInfo("info");
        notificationManager.notify(1, notificationBuilder.build());
    }
}