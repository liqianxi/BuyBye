package com.example.buybye.entities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Item implements Parcelable {
    private String itemName;
    private String City;
    private String Province;
    private String OwnerId;
    private ArrayList<String> pictureArray;
    private double price;
    private String description;
    private Date pickUpTime;
    private boolean isSoldOut;
    private String category;
    private String itemId;
    private Date postTime;

    public Item(){};

    public Item(String itemName, ArrayList<String> pictureArray, double price, String description, Date pickUpTime){
        this.description = description;
        this.itemName = itemName;
        this.price = price;
        this.pickUpTime = pickUpTime;
        this.pictureArray = pictureArray;
    }
    public Item(String itemName, ArrayList<String> pictureArray, double price, String description, Date pickUpTime,String category){
        this.description = description;
        this.itemName = itemName;
        this.price = price;
        this.pickUpTime = pickUpTime;
        this.pictureArray = pictureArray;
        this.category = category;
    }


    protected Item(Parcel in) {
        //must in the same order as write in parcel
        itemName = in.readString();
        OwnerId = in.readString();
        pictureArray = in.createStringArrayList();
        price = in.readDouble();
        description = in.readString();
        isSoldOut = in.readByte() != 0;
        itemId = in.readString();
        category = in.readString();
        pickUpTime = new Date(in.readLong());

    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setOwner(String OwnerId) {
        this.OwnerId = OwnerId;
    }

    public String getOwner() {
        return OwnerId;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getPictureArray() {
        if (pictureArray == null){
            throw new NullPointerException();
        }
        else{
            return pictureArray;
        }

    }

    public String getDescription() {
        if (description == null){
            throw new NullPointerException();
        }
        else{
            return description;
        }

    }

    public String getItemName() {
        if (itemName == null){
            throw new NullPointerException();
        }
        else {
            return itemName;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPictureArray(ArrayList<String> pictureArray) {
        this.pictureArray = pictureArray;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(OwnerId);
        parcel.writeStringList(pictureArray);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeByte((byte) (isSoldOut ? 1 : 0));
        parcel.writeString(itemId);
        parcel.writeString(category);
        parcel.writeLong(pickUpTime.getTime());

    }


}
