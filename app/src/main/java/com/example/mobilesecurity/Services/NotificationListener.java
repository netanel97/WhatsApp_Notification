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

import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.Model.MyDB;
import com.example.mobilesecurity.Utils.Constants;
import com.example.mobilesecurity.Utils.MSPV3;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NotificationListener extends NotificationListenerService {
    private MyDB myDB;

    public static final String WHATSAPP_MESSAGE = "WHATSAPP_MESSAGE";
    private static final String TAG = "pttt";

    private HashMap<String, ArrayList<Message>> arrayListMessages;

    private static final String TAG1 = "ptttt";
    private static final String TAG2 = "bdika";
    private static final String WA_PACKAGE = "com.whatsapp";

    private String lastMassege = "";

    private  Context context;

    private String sender;
    private String message;
    private String time;

    @Override
    public void onCreate() {
        super.onCreate();
        this.arrayListMessages = new HashMap<>();
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

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        if (!sbn.getPackageName().equals(WA_PACKAGE)) return;
        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;//VALUE

        String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
        String message = bundle.getString(NotificationCompat.EXTRA_TEXT);
        long time = sbn.getPostTime();


        String id = sbn.getKey();
        Log.d("reason", "" + reason);
        Log.d("message", "" + message);
        if (reason == 8 && !from.contains("WhatApp") && !message.contains("new messages") && !message.contains("Calling")) {
            Log.i(TAG1, "Deleted From: " + from);
            Log.i(TAG1, "Deleted Message: " + message);
            Log.i(TAG1, "Notification ID" + id);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String sTime = formatter.format(new Date(time));
            this.sender = from;
            updateArr(from,message,sTime);

            Intent intent = new Intent(WHATSAPP_MESSAGE);
            intent.putExtra(Constants.sender, from);
            intent.putExtra(Constants.KEY_message, message);
            intent.putExtra(Constants.KEY_time, sTime);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        }


    }

    private void updateArr(String sender,String message,String time) {
        if (!message.contains("messages from")) {
            Message myMessage = new Message(sender, message, time);
            if (arrayListMessages.get(sender) == null) {
                arrayListMessages.put(sender, new ArrayList<>());
            }

            arrayListMessages.get(sender).add(myMessage);
            saveToSP(myMessage);

        }
    }

    private void saveToSP(Message message) {
        Log.d("message", "saveToSP: "+message);
        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);
        if (myDB == null) {
            myDB = new MyDB();
        }
        if(myDB.getMessages().get(sender) == null){
            myDB.getMessages().put(sender,new ArrayList<Message>());

        }
        myDB.getMessages().get(message.getContact_name()).add(message);
        String json = new Gson().toJson(myDB);
        MSPV3.getMe().putString("MY_DB", json);
    }

}
