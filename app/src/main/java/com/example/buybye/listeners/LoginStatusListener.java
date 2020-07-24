package com.example.buybye.listeners;

public interface LoginStatusListener {

    /**
     * Called when the user is logged in, the function to do:
     */
    void onLoginSuccess();

    /**
     * Called when the login fails:
     */
    void onLoginFailure();
}
