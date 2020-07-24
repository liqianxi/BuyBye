package com.example.buybye.entities;

import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    private String email;
    private String userName;
    private String phoneNumber;
    private String userRegion;
    private String password;
    private String Gender;
    private ArrayList<buyerPost> buyerPostArray;
    private ArrayList<sellerPost> sellerPostArray;


    public User(String userName, String phoneNumber, String password, String Gender, String userRegion){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRegion = userRegion;
        this.Gender = Gender;
    }
    public User(String email, String userName, String phoneNumber, String password){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;

    }
    public User(String userName, String phoneNumber, String userRegion,ArrayList<buyerPost> buyerPostArray,ArrayList<sellerPost> sellerPostArray){
        this.userName = userName;
        this.userRegion = userRegion;
        this.phoneNumber = phoneNumber;
        this.buyerPostArray = buyerPostArray;
        this.sellerPostArray = sellerPostArray;
    }
    public User(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<buyerPost> getBuyerPostArray() {
        return buyerPostArray;
    }

    public ArrayList<sellerPost> getSellerPostArray() {
        return sellerPostArray;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBuyerPostArray(ArrayList<buyerPost> buyerPostArray) {
        this.buyerPostArray = buyerPostArray;
    }

    public void setSellerPostArray(ArrayList<sellerPost> sellerPostArray) {
        this.sellerPostArray = sellerPostArray;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

}
