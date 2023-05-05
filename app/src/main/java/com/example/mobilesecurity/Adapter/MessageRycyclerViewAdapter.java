package com.example.mobilesecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MessageRycyclerViewAdapter extends RecyclerView.Adapter<MessageRycyclerViewAdapter.MessageViewHolder> {
    private Context context;
    private ArrayList<Message> messages;


    public MessageRycyclerViewAdapter(Context context){
        this.context = context;
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message item = getItem(position);
        holder.fragment_message_contact_name.setText(item.getContact_name());
        holder.fragment_message.setText(item.getMessage());
        holder.fragment_message_time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {  return messages.size();}

    public void updateMessage(final ArrayList<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }


    public Message getItem(int position) {
        return messages.get(position);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView fragment_message_contact_name;
        private MaterialTextView fragment_message;
        private MaterialTextView fragment_message_time;



        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fragment_message_contact_name = itemView.findViewById(R.id.fragment_message_contact_name);
            this.fragment_message = itemView.findViewById(R.id.fragment_message);
            this.fragment_message_time = itemView.findViewById(R.id.fragment_message_time);
        }
    }

}
