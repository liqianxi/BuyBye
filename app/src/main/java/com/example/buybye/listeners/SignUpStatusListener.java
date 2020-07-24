package com.example.buybye.listeners;

public interface SignUpStatusListener {
    /**
     * Called when a new user is created, the function spec:
     */
    void onRegisterSuccess();

    /**
     * Called when the signup fails:
     */
    void onRegisterFailure();

    /**
     * Called when the password set by the user is too weak
     */
    void onWeakPassword();


    /**
     * Called when the name already exists in the database
     */
    void onUserAlreadyExist();
    /**
     * Called when the email set by the user is invalid
     */
    void onInvalidEmail();
}
