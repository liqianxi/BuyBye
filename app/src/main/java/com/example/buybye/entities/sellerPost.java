package com.example.buybye.entities;

import java.util.ArrayList;

public class sellerPost extends Post {
    public ArrayList<Item> itemList;
    public sellerPost() {
        super();
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }
}
