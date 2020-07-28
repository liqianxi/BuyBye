package com.example.buybye.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buybye.entities.Item;
import com.example.buybye.listeners.ItemAddDeleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ItemDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "ItemDatabaseAccessor";
    final String referenceName = "Items";
    ItemDatabaseAccessor() {
        super();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    public String generateRandomString(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
    public void addItem(Item item, final ItemAddDeleteListener itemAddDeleteListener){
        String uid = generateRandomString();
        String name = "peps0";
        if(item.getItemName()!=null){
            name = item.getItemName();
        }
        uid=name+uid;
        this.firestore.collection(referenceName)
                .document(uid).set(item)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG, "Item added");
                    itemAddDeleteListener.onItemAddedSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG, "Item add failed");
                    itemAddDeleteListener.onItemAddedFailure();
                });

    }


}
