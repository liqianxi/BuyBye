package com.example.buybye.listeners;

public interface ItemAddDeleteListener {

    void onItemsAddedSuccess();
    /**
     * when a new Item fails to add:
     */
    void onItemsAddedFailure();

    /**
     * Called when a Item deleted successfully:
     */
    void onItemDeleteSuccess();

    /**
     * Called when a Item deleted failed:
     */
    void onItemDeleteFailure();
}
