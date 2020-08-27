package com.example.buybye.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class ChatRoom {
    private ArrayList<String> users;
    private ArrayList<String> userNames;
    private String chatRoomId;
    private ArrayList<Message> Messages;
    private ArrayList<Integer> unreadNum = new ArrayList<>();
    private String recentMessageDate;

    private String messageSummary;

    public ChatRoom(){

    }

    public void messageNumAdd(int index){
        int num = unreadNum.get(index);
        num++;
        unreadNum.set(index,num);

    }
    public void messageNumReset(int index){
        unreadNum.set(index,0);
    }
    public ArrayList<Integer> getUnreadNum() {
        return unreadNum;
    }

    public ChatRoom(ArrayList<String> users, ArrayList<String> userNames, String chatRoomId, ArrayList<Message> Messages){
        this.users = users;
        this.userNames = userNames;
        this.chatRoomId = chatRoomId;
        this.Messages = Messages;
        this.unreadNum.add(0);
        this.unreadNum.add(0);
    }




    public String getMessageSummary() {
        return messageSummary;
    }

    public void setMessageSummary(String messageSummary) {
        this.messageSummary = messageSummary;
    }



    public String getRecentMessageDate() {
        return recentMessageDate;
    }

    public void setRecentMessageDate(String recentMessageDate) {
        this.recentMessageDate = recentMessageDate;
    }


    public ArrayList<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(ArrayList<String> userNames) {
        this.userNames = userNames;
    }

    public ArrayList<Message> getMessages() {
        return Messages;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setMessages(ArrayList<Message> messages) {
        Messages = messages;
    }


}
