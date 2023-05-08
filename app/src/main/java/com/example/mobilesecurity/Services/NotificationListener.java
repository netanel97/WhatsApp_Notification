package com.example.mobilesecurity.Services;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationListener extends NotificationListenerService {

    public static final String RADIO_STAION = "100FM";
    private static final String TAG = "pttt";
    private static final String TAG1 = "ptttt";
    private static final String TAG2 = "bdika";
    private static final String WA_PACKAGE = "com.whatsapp";

    private String lastMassege = "";

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Notification Listener connected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.i(TAG, "Notification Listener Disconnected");
    }

//    @Override
//    public void onNotificationPosted(StatusBarNotification sbn) {
//
//        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
//        Notification notification = sbn.getNotification();
//        Bundle bundle = notification.extras;//VALUE
//
//    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {


//        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
//        Log.d("pttt", "Reason: " + reason);
//        Notification notification = sbn.getNotification();
      //  Bundle bundle = notification.extras;//VALUE
        //super.onNotificationRemoved(sbn, rankingMap, reason);
        //        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;

        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;//VALUE

        String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
        String message = bundle.getString(NotificationCompat.EXTRA_TEXT);
        long time = sbn.getPostTime();


        String id = sbn.getKey();
        String[] arr =id.split("\\|");
        Log.d("reason", "onNotificationRemoved:" + reason);
        //!message.contains("messages from") && !message.contains("new messages") && !arr[3].equals("null") &&
        if(reason == 8){
            Log.i(TAG1, "Deleted From: " + from);
            Log.i(TAG1, "Deleted Message: " + message);
            Log.i(TAG1, "Notification ID" + id);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String sTime = formatter.format(new Date(time));
            Log.i(TAG, "Time" + sTime);

            Intent intent = new Intent(RADIO_STAION);
            intent.putExtra("sender", from);
            intent.putExtra("message", message);
            intent.putExtra("time", sTime);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }

    }

//    @Override
//    public void onNotificationRemoved(StatusBarNotification sbn) {
//        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
//
//
//        Notification notification = sbn.getNotification();
//        Bundle bundle = notification.extras;//VALUE
//
//        String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
//        String message = bundle.getString(NotificationCompat.EXTRA_TEXT);
//        long time = sbn.getPostTime();
//
//
//        String id = sbn.getKey();
//        String[] arr =id.split("\\|");
//
//        if(!message.contains("messages from") && !message.contains("new messages") && !arr[3].equals("null")){
//            Log.i(TAG1, "Deleted From: " + from);
//            Log.i(TAG1, "Deleted Message: " + message);
//            Log.i(TAG1, "Notification ID" + id);
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//            String sTime = formatter.format(new Date(time));
//            Log.i(TAG, "Time" + sTime);
//
//            Intent intent = new Intent(RADIO_STAION);
//            intent.putExtra("sender", from);
//            intent.putExtra("message", message);
//            intent.putExtra("time", sTime);
//            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//        }
//    }
}
