package com.example.buybye.entities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Item implements Parcelable {
    private String itemName;
    private ArrayList<Uri> pictureArray;
    private double price;
    private String description;
    private Date pickUpTime;
    private boolean isSoldOut;

    public Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime){
        this.description = description;
        this.itemName = itemName;
        this.price = price;
        this.pickUpTime = pickUpTime;
        this.pictureArray = pictureArray;
    }

    protected Item(Parcel in) {
        itemName = in.readString();
        pictureArray = in.createTypedArrayList(Uri.CREATOR);
        price = in.readDouble();
        description = in.readString();
        isSoldOut = in.readByte() != 0;
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

    public ArrayList<Uri> getPictureArray() {
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

    public void setPictureArray(ArrayList<Uri> pictureArray) {
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
        parcel.writeTypedList(pictureArray);
        parcel.writeDouble(price);
        parcel.writeString(description);
        parcel.writeByte((byte) (isSoldOut ? 1 : 0));
    }
}
