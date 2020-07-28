package com.example.buybye.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buybye.entities.Item;
import com.example.buybye.listeners.ItemAddDeleteListener;
import com.example.buybye.listeners.ItemListRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static java.util.Objects.requireNonNull;

public class ItemDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "ItemDatabaseAccessor";
    final String referenceName = "Items";

    public ItemDatabaseAccessor() {
        super();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public String generateRandomString(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }


    // note here:
    // keep a unique item id in the item, then use this id as document id.
    // we will know which item is being selected so that we can get relevant info by searching this uid.

    public void addItem(Item item, final ItemAddDeleteListener itemAddDeleteListener){
        String uid = generateRandomString();
        String name = "peps0";
        if(item.getItemName()!=null){
            name = item.getItemName();
        }
        uid=name+uid;
        item.setItemId(uid);

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

    public void deleteItem(String uid, final ItemAddDeleteListener listener){
        this.firestore
                .collection(referenceName)
                .document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG,"Item deleted");
                    listener.onItemDeleteSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG,"Item not deleted");
                    listener.onItemDeleteFailure();
                });

    }
    public void getAllItems(final ItemListRequestListener listener){
        this.firestore
                .collection(referenceName)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<Item> items = new ArrayList<>();
                        for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                            Item item = document.toObject(Item.class);
                            items.add(item);
                        }
                        listener.onGetItemSuccess(items);
                    }

                    else{
                        listener.onGetItemFailure();
                    }
                });
    }

}
