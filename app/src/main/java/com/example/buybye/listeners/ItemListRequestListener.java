package com.example.buybye.listeners;

import com.example.buybye.entities.Item;

import java.util.ArrayList;

public interface ItemListRequestListener {
    void onGetItemSuccess(ArrayList<Item> items);
    void onGetItemFailure();

}
