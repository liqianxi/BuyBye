package com.example.buybye.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {
    private String text;
    private String date;
    private String sender;
    private String senderName;
    private ArrayList<String> pictures;

    public Message(String text, String date, String sender){
        this.text = text;
        this.date = date;
        this.sender = sender;
    }
    public Message(){}

    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }


    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }
}
