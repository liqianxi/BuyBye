package com.example.buybye.entities;

import java.util.ArrayList;

public class sellerPost extends Post {
    public ArrayList<Item> itemList;
    public sellerPost(ArrayList<Item> itemList){
        this.itemList=itemList;
    }
}
