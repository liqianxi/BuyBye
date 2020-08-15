package com.example.buybye.entities;

import java.util.ArrayList;
import java.util.Date;

public class Message {
    private String text;
    private Date date;
    private String sender;
    private ArrayList<String> pictures;
    public Message(String text, Date date, String sender, ArrayList<String> pictures){
        this.text = text;
        this.date = date;
        this.sender = sender;
        this.pictures = pictures;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public Date getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public void setDate(Date date) {
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
