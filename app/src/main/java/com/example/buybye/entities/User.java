package com.example.buybye.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class User implements Parcelable {
    private String email;
    private String userName;
    private String phoneNumber;
    private String userProvince;
    private String userCity;
    private String password;
    private String Gender;
    private ArrayList<String> markedItems = new ArrayList<>();
    private HashMap<Date,ArrayList<String>> browseHistory;
    private ArrayList<buyerPost> buyerPostArray = new ArrayList<>();
    private ArrayList<sellerPost> sellerPostArray = new ArrayList<>();


    public User(String userName, String phoneNumber, String password, String Gender, String userProvince, String userCity){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userProvince = userProvince;
        this.userCity = userCity;
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
        this.userProvince = userRegion;
        this.phoneNumber = phoneNumber;
        this.buyerPostArray = buyerPostArray;
        this.sellerPostArray = sellerPostArray;
    }
    public User(){}

    protected User(Parcel in) {
        email = in.readString();
        userName = in.readString();
        phoneNumber = in.readString();
        userCity = in.readString();
        userProvince = in.readString();
        password = in.readString();
        Gender = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public ArrayList<String> getMarkedItems() {
        return markedItems;
    }

    public void setMarkedItems(ArrayList<String> markedItems) {
        this.markedItems = markedItems;
    }

    public HashMap<Date, ArrayList<String>> getBrowseHistory() {
        return browseHistory;
    }

    public void setBrowseHistory(HashMap<Date, ArrayList<String>> browseHistory) {
        this.browseHistory = browseHistory;
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

    public String getUserCity() {
        return userCity;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(userName);
        parcel.writeString(phoneNumber);
        parcel.writeString(userCity);
        parcel.writeString(userProvince);
        parcel.writeString(password);
        parcel.writeString(Gender);
    }
}
