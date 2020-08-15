package com.example.buybye.listeners;

import com.example.buybye.entities.ChatRoom;
import com.example.buybye.entities.Message;

import java.util.ArrayList;

public interface SingleChatRoomListener {
    void onRetrieveChatRoomSuccess(ChatRoom chatRoom);
    void onRetrieveChatRoomFailure();
    void onUpdateChatRoomSuccess();
    void onUpdateChatRoomFailure();
    void onSnapShotRetrieveSuccess(ChatRoom chatRoom);
    void onSnapShotRetrieveFailure();
}
