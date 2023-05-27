package com.example.mobilesecurity.Fragments;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

    private MessagesViewModel messagesViewModel;

    private MyDB myDB;
    private RecyclerView contactRV;
    private SendersRycyclerViewAdapter mAdapter;
    private HashMap<String, ArrayList<Message>> arrayListMessages;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactsBinding.inflate(inflater, container, false);
        messagesViewModel = new MessagesViewModel();
        messagesViewModel.getAllMessages().observe(getViewLifecycleOwner(),observer);

        if (Settings.Secure.getString(getContext().getContentResolver(), "enabled_notification_listeners").contains(getContext().getApplicationContext().getPackageName())) {
            //service is enabled do something
            IntentFilter intentFilter = new IntentFilter(NotificationListener.NOTIFICATION_SERVICE);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(messagesViewModel.getReciver(), new IntentFilter(NotificationListener.WHATSAPP_MESSAGE));
        }

        String js = MSPV3.getMe().getString("MY_DB", "");
        myDB = new Gson().fromJson(js, MyDB.class);
        if (myDB == null) {
            myDB = new MyDB();
        }

        this.arrayListMessages = myDB.getMessages();
        contactRV = binding.listMessages;
        mAdapter = new SendersRycyclerViewAdapter(getContext());
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
                LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(messagesViewModel.getReciver());
                final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_MessagesFragment, args);//moving to..


            }
        });


        return binding.getRoot();

    }

    Observer<HashMap<String, ArrayList<Message>>> observer = new Observer<HashMap<String, ArrayList<Message>>>() {


        @Override
        public void onChanged(HashMap<String, ArrayList<Message>> stringArrayListHashMap) {
            ArrayList<String> keys = new ArrayList<>(arrayListMessages.keySet());
            mAdapter.updateSendersList(keys);
        }
    };



    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(messagesViewModel.getReciver());

    }
}

