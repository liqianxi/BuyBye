package com.example.buybye.listeners;

import com.example.buybye.entities.User;

public interface GetSingleUserListener {
    void onGetUserSuccess(User user);
    void onGetUserFailure();
}
