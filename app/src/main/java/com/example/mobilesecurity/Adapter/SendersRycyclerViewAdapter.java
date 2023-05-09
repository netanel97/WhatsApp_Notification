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
import java.util.HashMap;

public class SendersRycyclerViewAdapter  extends RecyclerView.Adapter<SendersRycyclerViewAdapter.SenderViewHolder> {

    private Context context;
    private ArrayList<String> senders;
    private ItemClickListener listener;


    public SendersRycyclerViewAdapter(Context context){
        this.context = context;
        senders = new ArrayList<>();
    }
    public SendersRycyclerViewAdapter setItemSenderClickListener(ItemClickListener itemClickListener){
        this.listener = itemClickListener;
        return this;
    }



    public String getItem(int position) {
        return senders.get(position);
    }

    @NonNull
    @Override
    public SendersRycyclerViewAdapter.SenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_user, parent, false);
        SenderViewHolder senderViewHolder = new SenderViewHolder(view);
        return senderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendersRycyclerViewAdapter.SenderViewHolder holder, int position) {
        String item = getItem(position);
        holder.fragment_message_contact_name.setText(item);
    }

    @Override
    public int getItemCount() {
        return senders == null ? 0 : senders.size();
    }
    public void updateSendersList(final ArrayList<String> senders){
        this.senders = senders;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void changeScreenItem(String sender);
    }


    class SenderViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView fragment_message_contact_name;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fragment_message_contact_name = itemView.findViewById(R.id.fragment_message_contact_name);
            itemView.setOnClickListener(view ->
            {
                int position = getAdapterPosition();
                listener.changeScreenItem(senders.get(position));
            });


        }
    }

}
