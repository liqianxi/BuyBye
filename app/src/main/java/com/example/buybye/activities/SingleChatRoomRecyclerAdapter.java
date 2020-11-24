package com.example.buybye.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleChatRoomRecyclerAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private ArrayList<Message> messages;
    private String userId;
    private Context mContext;

    public SingleChatRoomRecyclerAdapter(Context context,String userId, ArrayList<Message> messages){
        mContext = context;
        this.messages = messages;
        this.userId = userId;

    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(message.getSender().equals(userId)){
            //means you send this message
            return VIEW_TYPE_MESSAGE_SENT;
        }else{
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_message_layout, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.receive_message_layout, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }
    private String checkSameDay(String date){
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String[] splitStr = date.split("\\s+");
        if(splitStr[0].equals(timeStamp)){
            //same day
            return splitStr[1];
        }
        else{
            return date;
        }




    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }
    private class SentMessageHolder extends RecyclerView.ViewHolder{
        private ImageView chatUserIcon;
        private TextView chatUserText, chatUserTime;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            chatUserIcon = itemView.findViewById(R.id.chatUserIcon);
            chatUserText = itemView.findViewById(R.id.chatUserText);
            chatUserTime = itemView.findViewById(R.id.chatUserTime);
        }
        void bind(Message message){
            chatUserIcon.setImageResource(R.drawable.ic_launcher_foreground);
            chatUserText.setText(message.getText());

            chatUserTime.setText(checkSameDay(message.getDate()));
            Log.v("timeInAdapter",checkSameDay(message.getDate()));


        }
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView chatOtherTime, chatOtherText;
        ImageView chatOtherIcon;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            chatOtherText = (TextView) itemView.findViewById(R.id.chatOtherText);
            chatOtherTime = (TextView) itemView.findViewById(R.id.chatOtherTime);
            chatOtherIcon = (ImageView) itemView.findViewById(R.id.chatOtherIcon);
        }

        void bind(Message message) {
            chatOtherIcon.setImageResource(R.drawable.ic_launcher_foreground);
            chatOtherText.setText(message.getText());
            chatOtherTime.setText(checkSameDay(message.getDate()));
        }
    }
    @Override
    public int getItemCount() {
        return messages.size();
    }
}