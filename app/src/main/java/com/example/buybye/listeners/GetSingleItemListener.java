package com.example.buybye.listeners;

import com.example.buybye.entities.Item;

public interface GetSingleItemListener {
    void onGetItemSuccess(Item item);
    void onGetItemFailure();
}
