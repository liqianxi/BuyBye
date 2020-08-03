package com.example.buybye.listeners;

import com.example.buybye.entities.Item;

import java.util.ArrayList;
import java.util.List;

public interface ItemQueryListener {
    void onQueryFinish(List<Item> items);
    void onQueryFailure();
}
