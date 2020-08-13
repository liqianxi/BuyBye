package com.example.buybye.listeners;

import com.example.buybye.entities.Item;

import java.util.ArrayList;

public interface GetCategoryItemsListener {
    void OnGetCategoryItemsSuccess(ArrayList<Item> items);
    void OnGetCategoryItemsFailure();
}
