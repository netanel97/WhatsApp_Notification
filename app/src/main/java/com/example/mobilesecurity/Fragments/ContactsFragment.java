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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobilesecurity.Adapter.SendersRycyclerViewAdapter;
import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.Model.MyDB;
import com.example.mobilesecurity.R;
import com.example.mobilesecurity.Services.NotificationListener;
import com.example.mobilesecurity.Utils.Constants;
import com.example.mobilesecurity.Utils.MSPV3;
import com.example.mobilesecurity.databinding.FragmentContactsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.HashMap;

public class ContactsFragment extends Fragment {
    private FragmentContactsBinding binding;

    private MyDB myDB;
    private RecyclerView contactRV;
    private SendersRycyclerViewAdapter mAdapter;
    private HashMap<String, ArrayList<Message>> arrayListMessages;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactsBinding.inflate(inflater, container, false);

        if (Settings.Secure.getString(getContext().getContentResolver(), "enabled_notification_listeners").contains(getContext().getApplicationContext().getPackageName())) {
            //service is enabled do something
            IntentFilter intentFilter = new IntentFilter(NotificationListener.NOTIFICATION_SERVICE);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBRD, new IntentFilter(NotificationListener.WHATSAPP_MESSAGE));
        }


        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);
        Log.d("This is myDB",""+ myDB);
        if (myDB == null) {
            myDB = new MyDB();
        }

        this.arrayListMessages = myDB.getMessages();
        ArrayList<String> keys = new ArrayList<>(arrayListMessages.keySet());
        contactRV = binding.listMessages;
        mAdapter = new SendersRycyclerViewAdapter(getContext());
        mAdapter.updateSendersList(keys);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        contactRV.setLayoutManager(linearLayoutManager);
        contactRV.setAdapter(mAdapter);
        Intent intent = new Intent(getContext(), NotificationListener.class);
        getActivity().startService(intent);
        mAdapter.setItemSenderClickListener(new SendersRycyclerViewAdapter.ItemClickListener() {
            @Override
            public void changeScreenItem(String sender) {
                Bundle args = new Bundle();
                args.putString(Constants.KEY_SENDER, sender);
                LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBRD);
                final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_MessagesFragment, args);//moving to..


            }
        });


        return binding.getRoot();

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Entered","enter2");
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBRD);

    }



    private BroadcastReceiver myBRD = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().runOnUiThread(() ->{
                Log.d("Enter","Enter");
                String sender = intent.getStringExtra(Constants.sender);
                String message = intent.getStringExtra(Constants.KEY_message);
                String time = intent.getStringExtra(Constants.KEY_time);
                Message myMessage = new Message(sender, message, time);
                if (arrayListMessages.get(sender) == null) {
                    arrayListMessages.put(sender, new ArrayList<>());
                }
                arrayListMessages.get(sender).add(myMessage);
                ArrayList<String> keys = new ArrayList<>(arrayListMessages.keySet());
                mAdapter.updateSendersList(keys);
            });
        }
    };
}

