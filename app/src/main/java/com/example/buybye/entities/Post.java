package com.example.buybye.entities;

import java.util.Date;

public abstract class Post {
    private String userName;
    private String descriptions;
    private Date postDate;
    private String phoneNumber;

    Post(){}
    Post(String userName, String descriptions, Date postDate, String phoneNumber){
        this.userName = userName;
        this.descriptions = descriptions;
        this.postDate = postDate;
        this.phoneNumber = phoneNumber;
    }

    public Date getPostDate() {
        if (postDate == null){
            throw new NullPointerException();
        }
        else{
            return postDate;
        }
    }

    public String getPhoneNumber() {
        if (phoneNumber == null){
            throw new NullPointerException();
        }
        else{
            return phoneNumber;
        }
    }

    public String getUserName() {
        if (userName == null){
            throw new NullPointerException();
        }
        else{
            return userName;
        }
    }

    public String getDescriptions() {
        if (descriptions == null){
            throw new NullPointerException();
        }
        else{
            return descriptions;
        }

    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

