package com.example.buybye.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buybye.entities.Item;
import com.example.buybye.listeners.GetSingleItemListener;
import com.example.buybye.listeners.ItemAddDeleteListener;
import com.example.buybye.listeners.ItemListRequestListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.requireNonNull;

public class ItemDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "ItemDatabaseAccessor";
    final String referenceName = "Items";

    public ItemDatabaseAccessor() {
        super();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    // note here:
    // keep a unique item id in the item, then use this id as document id.
    // we will know which item is being selected so that we can get relevant info by searching this uid.

    public void addItem(ArrayList<Item> items, final ItemAddDeleteListener itemAddDeleteListener){
        ArrayList<Task<Void>> taskList = new ArrayList<Task<Void>>();
        for(int i=0;i<items.size();i++){
            Item item = items.get(i);
            String uid = getAlphaNumericString(8);
            String name = "peps0";
            if(item.getItemName()!=null){
                name = item.getItemName();
            }
            uid=name+uid;
            item.setItemId(uid);
            taskList.add(this.firestore.collection(referenceName)
                    .document(uid).set(item));
        }//new
        Tasks.whenAll(taskList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        itemAddDeleteListener.onItemsAddedSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        itemAddDeleteListener.onItemsAddedFailure();
                    }
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
    public void getSingleItem(String itemId, GetSingleItemListener listener){
        this.firestore.collection(referenceName)
                .document(itemId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Item item = documentSnapshot.toObject(Item.class);
                        listener.onGetItemSuccess(item);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onGetItemFailure();

                    }
                });
    }
}
