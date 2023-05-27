package com.example.mobilesecurity.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagesViewModel {
    private final MutableLiveData<HashMap<String, ArrayList<Message>>> allMessages;


    public MessagesViewModel() {
        allMessages = new MutableLiveData<>();


    }

    public LiveData<HashMap<String, ArrayList<Message>>> getAllMessages() {
        if (allMessages.getValue() == null) {
            allMessages.setValue(new HashMap<String, ArrayList<Message>>());
        }
        return allMessages;
    }


    private BroadcastReceiver myBRD = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String sender = intent.getStringExtra(Constants.sender);
            String message = intent.getStringExtra(Constants.KEY_message);
            String time = intent.getStringExtra(Constants.KEY_time);
            Message myMessage = new Message(sender, message, time);
            if (allMessages.getValue().get(sender) == null) {
                allMessages.getValue().put(sender, new ArrayList<>());
            }
            allMessages.getValue().get(sender).add(myMessage);


        }

    };


    public BroadcastReceiver getReciver() {
        return myBRD;
    }
}
