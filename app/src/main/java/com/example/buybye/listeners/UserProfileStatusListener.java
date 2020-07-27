package com.example.buybye.listeners;


import com.example.buybye.entities.User;

public interface UserProfileStatusListener {
    /**
     * Called when profile data stored successfully:
     */
    void onProfileStoreSuccess();

    /**
     * Called when profile data stored fails:
     */
    void onProfileStoreFailure();

    /**
     * Called when profile retrieve successfully:
     * @param user
     *      receives a user object containing all info about the current user.
     */
    void onProfileRetrieveSuccess(User user);

    /**
     * Called when profile retrieve failed:
     */
    void onProfileRetrieveFailure();

    /**
     * Called when profile updated successfully:
     * @param user
     *      receives the updated user object.
     */
    void onProfileUpdateSuccess(User user);

    /**
     * Called when profile update fails:
     */
    void onProfileUpdateFailure();

    void onValidateSuccess();

    void onValidateFailure();
    void onDeleteSuccess();
    void onDeleteFailure();
}
