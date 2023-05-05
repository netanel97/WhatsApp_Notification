package com.example.mobilesecurity.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesecurity.Adapter.MessageRycyclerViewAdapter;
import com.example.mobilesecurity.Adapter.SendersRycyclerViewAdapter;
import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.Model.MyDB;
import com.example.mobilesecurity.R;
import com.example.mobilesecurity.Services.NotificationListener;
import com.example.mobilesecurity.Utils.MSPV3;
import com.example.mobilesecurity.databinding.FragmentContactsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.HashMap;

public class ContactsFragment extends Fragment {
    private FragmentContactsBinding binding;
    private MyDB myDB;
    private RecyclerView contactRV;
    private String sender;
    private String message;
    private String time;

    private SendersRycyclerViewAdapter mAdapter;

    private HashMap<String, ArrayList<Message>> arrayListMessages;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        if (Settings.Secure.getString(getContext().getContentResolver(),"enabled_notification_listeners").contains(getContext().getApplicationContext().getPackageName()))
        {
            //service is enabled do something
            IntentFilter intentFilter = new IntentFilter(NotificationListener.NOTIFICATION_SERVICE);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBRD, new IntentFilter(NotificationListener.RADIO_STAION));

        } else {
            //service is not enabled try to enabled by calling...
            startActivity(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);
        if (myDB == null) {
            myDB = new MyDB();
            this.arrayListMessages = new HashMap<>();
        }


        contactRV = binding.listMessages;
        mAdapter = new SendersRycyclerViewAdapter(getContext());
        ArrayList<String> keys = new ArrayList<String>(arrayListMessages.keySet());
        mAdapter.updateSendersList(keys);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        contactRV.setLayoutManager(linearLayoutManager);
        contactRV.setAdapter(mAdapter);
        mAdapter.setItemSenderClickListener(new SendersRycyclerViewAdapter.ItemClickListener(){
            @Override
            public void changeScreenItem(String sender) {
                Bundle args = new Bundle();
                args.putString("SENDER",sender);
                final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_MessagesFragment,args);//moving to..


            }});


        return binding.getRoot();

    }
//
//    Observer <HashMap<String, ArrayList<Message>>> observer = new Observer<HashMap<String, ArrayList<Message>>>(){
//
//        @Override
//        public void onChanged(HashMap<String, ArrayList<Message>> stringArrayListHashMap) {
//            ArrayList<String> keys = new ArrayList<String>(arrayListMessages.keySet());
//            Log.d("bdika", "onCreateView: " + keys);
//            mAdapter.updateSendersList(keys);
//        }
//
//    };

    private BroadcastReceiver myBRD = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
          sender = intent.getStringExtra("sender");
          message = intent.getStringExtra("message");
          time = intent.getStringExtra("time");

            if (!message.contains("messages from")) {
                Message myMessage = new Message(sender, message, time);
                if(arrayListMessages.get(sender) == null){
                    arrayListMessages.put(sender,new ArrayList<Message>());
                }
                arrayListMessages.get(sender).add(myMessage);
                saveToSP(myMessage);
                ArrayList<String> keys = new ArrayList<String>(arrayListMessages.keySet());
                mAdapter.updateSendersList(keys);
            }
        }

    };


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
//        myDB.getMessages().add(message);

//        Collections.sort(myDB.getRecords(), new SortByScore());

        String json = new Gson().toJson(myDB);
        MSPV3.getMe().putString("MY_DB", json);
    }
}
