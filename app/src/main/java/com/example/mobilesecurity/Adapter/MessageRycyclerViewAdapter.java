package com.example.mobilesecurity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesecurity.Model.Message;
import com.example.mobilesecurity.databinding.MessageItemBinding;

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
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
//        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
      //  return messageViewHolder;
        return new MessageViewHolder(MessageItemBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message item = getItem(position);
        holder.binding.receivedMessage.setText(item.getMessage());
        holder.binding.receivedName.setText(item.getContact_name());
        holder.binding.receivedDateTime.setText(item.getTime());
//        holder.fragment_message_contact_name.setText(item.getContact_name());
//        holder.fragment_message.setText(item.getMessage());
//        holder.fragment_message_time.setText(item.getTime());
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

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private final MessageItemBinding binding;


        public MessageViewHolder(MessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
