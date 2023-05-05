package com.example.mobilesecurity.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;

import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.Model.MyDB;
import com.example.mobilesecurity.Services.NotificationListener;
import com.example.mobilesecurity.Utils.Constants;
import com.example.mobilesecurity.Utils.MSPV3;
import com.example.mobilesecurity.Adapter.MessageRycyclerViewAdapter;
import com.example.mobilesecurity.databinding.FragmentMessagesBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;
    private RecyclerView messagesRV;
    private MessageRycyclerViewAdapter mAdapter;
    private ArrayList<Message> listsArrayList;

    private MyDB myDB;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        messagesRV = binding.listMessages;
        mAdapter = new MessageRycyclerViewAdapter(getContext());

        String key = getArguments().getString("SENDER");
        String js = MSPV3.getMe().getString(Constants.KEY_DB, "");
        myDB = new Gson().fromJson(js, MyDB.class);
        if(myDB == null){
            myDB = new MyDB();
        }
        Log.d("bdika2", "onCreateView: " + key);
        mAdapter.updateMessage(myDB.getMessages().get(key));//update MessageRycycler
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        messagesRV.setLayoutManager(linearLayoutManager);
        messagesRV.setAdapter(mAdapter);
        return binding.getRoot();
    }

//    private BroadcastReceiver myBRD = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//
//            String sender = intent.getStringExtra("sender");
//            String message = intent.getStringExtra("message");
//            String time = intent.getStringExtra("time");
//
//            if(!message.contains("messages from")){
//
//
//                Message myMessage = new Message(sender, message, time);
////                arrayListMessages.get(sender).add(myMessage);
//                saveToSP(myMessage);
//              //  listsArrayList.sort(Comparator.comparing(Message::getContact_name));
//                mAdapter.notifyItemInserted(listsArrayList.size());
//            }
//
//        }
//    };

//    private void saveToSP(Message message) {
//        String js = MSPV3.getMe().getString("MY_DB", "");
//        myDB = new Gson().fromJson(js, MyDB.class);
//        if(myDB == null){
//            myDB = new MyDB();
//        }
//        myDB.getMessages().add(message);
//
////        Collections.sort(myDB.getRecords(), new SortByScore());
//
//        String json = new Gson().toJson(myDB);
//        MSPV3.getMe().putString("MY_DB", json);
//    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}