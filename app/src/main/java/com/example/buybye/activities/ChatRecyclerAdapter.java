package com.example.buybye.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.ChatRoom;

import java.util.ArrayList;


public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder>  {
    private ArrayList<ChatRoom> chatRooms;
    private String userId;
    private ChatRecyclerAdapter.RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
        boolean onLongClick(View view, int position);

    }
    public ChatRecyclerAdapter(String userId,ArrayList<ChatRoom> chatRooms, ChatRecyclerAdapter.RecyclerViewClickListener listener){
        this.chatRooms = chatRooms;
        this.mListener = listener;
        this.userId = userId;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView messageImage;
        private TextView contactName;
        private TextView recentMessageDate;
        private TextView messageSummary;
        private TextView unreadMessageNum;

        private ChatRecyclerAdapter.RecyclerViewClickListener mListener;
        public MyViewHolder(View view, ChatRecyclerAdapter.RecyclerViewClickListener listener){
            super(view);
            messageImage = view.findViewById(R.id.messageImage);
            contactName = view.findViewById(R.id.contactName);
            recentMessageDate = view.findViewById(R.id.recentMessageDate);
            messageSummary = view.findViewById(R.id.messageSummary);
            unreadMessageNum = view.findViewById(R.id.unreadMessageNum);


            mListener = listener;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onLongClick(view, getAdapterPosition());
            return true;
        }
    }
    @NonNull
    @Override
    public ChatRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_chat_recycler_display,parent,false);

        ChatRecyclerAdapter.MyViewHolder myViewHolder = new ChatRecyclerAdapter.MyViewHolder(view,mListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerAdapter.MyViewHolder holder, int position) {
        ChatRoom chatRoom = chatRooms.get(position);
        int otherUserIndex = 0;
        int userIndex = -1;
        for(int i=0;i<chatRoom.getUsers().size();i++){
            if(chatRoom.getUsers().get(i).equals(userId)){
                userIndex = i;
            }else{
                otherUserIndex = i;
            }
        }
        ImageView messageImage = holder.messageImage;
        TextView contactName = holder.contactName;
        TextView recentMessageDate = holder.recentMessageDate;
        TextView messageSummary = holder.messageSummary;
        TextView unreadMessageNum = holder.unreadMessageNum;
        /*
        *
        * note: the unread num array size is different from the actual value of each unread message num in each chat
        *
        * 
        * */
        if (chatRoom.getUnreadNum().size() !=0){
            if(chatRoom.getUnreadNum().get(userIndex) == 0){
                unreadMessageNum.setVisibility(View.INVISIBLE);
            }else{
                unreadMessageNum.setText(String.format("%s", chatRoom.getUnreadNum().get(userIndex)));
            }
        }


        messageImage.setImageResource(R.drawable.ic_launcher_foreground);
        contactName.setText(chatRoom.getUserNames().get(otherUserIndex));
        recentMessageDate.setText(chatRoom.getRecentMessageDate());
        messageSummary.setText(chatRoom.getMessageSummary());



    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }
}