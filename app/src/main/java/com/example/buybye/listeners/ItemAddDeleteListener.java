package com.example.buybye.listeners;

public interface ItemAddDeleteListener {
    /**
     * Called when a new Item added:
     */
    void onItemAddedSuccess();

    /**
     * when a new Item fails to add:
     */
    void onItemAddedFailure();

    /**
     * Called when a Item deleted successfully:
     */
    void onItemDeleteSuccess();

    /**
     * Called when a Item deleted failed:
     */
    void onItemDeleteFailure();
}
