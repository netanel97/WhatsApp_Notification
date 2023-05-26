package com.example.mobilesecurity.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.Model.MyDB;
import com.example.mobilesecurity.Utils.Constants;
import com.example.mobilesecurity.Utils.MSPV3;
import com.example.mobilesecurity.Adapter.MessageRycyclerViewAdapter;
import com.example.mobilesecurity.databinding.FragmentMessagesBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

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
        String key = getArguments().getString(Constants.KEY_SENDER);
        String js = MSPV3.getMe().getString(Constants.KEY_DB, "");
        myDB = new Gson().fromJson(js, MyDB.class);
        if(myDB == null){
            myDB = new MyDB();
        }
        mAdapter.updateMessage(myDB.getMessages().get(key));//update MessageRycycler
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        messagesRV.setLayoutManager(linearLayoutManager);
        messagesRV.setAdapter(mAdapter);
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}