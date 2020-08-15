package com.example.buybye.listeners;

import com.example.buybye.entities.ChatRoom;

import java.util.ArrayList;

public interface ChatRoomListener {
    void OnChatRoomAddSuccess();
    void OnChatRoomAddFailure();
    void OnSnapShotReceiveSuccess(ArrayList<ChatRoom> chatRooms);
    void OnSnapShotReceiveFailure();
    void OnRetrieveSuccess(ArrayList<ChatRoom> chatRooms);
    void OnRetrieveFailure();

}
