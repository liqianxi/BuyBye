package com.example.buybye.entities;

import java.util.ArrayList;


public class buyerPost extends Post{
    public ArrayList<Item> itemList;
    buyerPost(ArrayList<Item> itemList){
        this.itemList=itemList;
    }
}
