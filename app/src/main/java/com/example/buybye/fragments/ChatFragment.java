package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.activities.ChatRecyclerAdapter;
import com.example.buybye.activities.ChatRoomActivity;
import com.example.buybye.database.ChatDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ChatRoom;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ChatRoomListener;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements UserProfileStatusListener, ChatRoomListener {
    private TextView topBar;
    private RecyclerView chatDisplayRecyclerView;
    private ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    private User currentUser;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    private ChatDatabaseAccessor chatDatabaseAccessor = new ChatDatabaseAccessor();
    private int otherIndex;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        topBar = view.findViewById(R.id.chatPageTopBarTitle);
        chatDisplayRecyclerView = view.findViewById(R.id.chatDisplayRecyclerView);
        userDatabaseAccessor.getUserProfile(this);
        return view;

    }
    private void setRecyclerView(){
        chatRecyclerAdapter = new ChatRecyclerAdapter(this.currentUser.getEmail(),chatRooms, new ChatRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                ChatRoom chatRoom = chatRooms.get(position);
                Intent intent = new Intent(getContext(), ChatRoomActivity.class);
                ArrayList<String> names = chatRoom.getUserNames();
                ArrayList<String> ids = chatRoom.getUsers();
                otherIndex = 0;
                for(int i=0;i<ids.size();i++){
                    if(!ids.get(i).equals(currentUser.getEmail())){
                        otherIndex = i;
                    }
                }
                intent.putExtra("otherName",names.get(otherIndex));
                intent.putExtra("roomId",chatRoom.getChatRoomId());
                intent.putExtra("userId",currentUser.getEmail());

                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View view, int position) {
                return false;
            }
        });
        chatDisplayRecyclerView.setAdapter(chatRecyclerAdapter);
        chatDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.currentUser = user;
        setRecyclerView();
        chatDatabaseAccessor.getRelatedRooms(currentUser.getEmail(),this);


    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {

    }

    @Override
    public void onValidateSuccess() {

    }

    @Override
    public void onValidateFailure() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFailure() {

    }

    @Override
    public void OnChatRoomAddSuccess() {

    }

    @Override
    public void OnChatRoomAddFailure() {

    }

    @Override
    public void OnSnapShotReceiveSuccess(ArrayList<ChatRoom> chatRooms) {
        this.chatRooms.clear();
        this.chatRooms.addAll(chatRooms);
        chatRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnSnapShotReceiveFailure() {

    }

    @Override
    public void OnRetrieveSuccess(ArrayList<ChatRoom> chatRooms) {
        this.chatRooms.clear();
        this.chatRooms.addAll(chatRooms);
        chatRecyclerAdapter.notifyDataSetChanged();
        chatDatabaseAccessor.addSnapshotListener(currentUser.getEmail(),this);
    }

    @Override
    public void OnRetrieveFailure() {

    }
}
