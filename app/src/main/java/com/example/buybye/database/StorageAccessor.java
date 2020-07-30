package com.example.buybye.database;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class StorageAccessor{
    public static final String TAG = "StorageAccessor";
    private FirebaseStorage storage;
    private StorageReference storageRef;
    public StorageAccessor(){
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = this.storage.getReference();
    }
    public FirebaseStorage getStorage() {
        return storage;
    }

    public StorageReference getStorageRef() {
        return storageRef;
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
    public void getImagesUri(ArrayList<Uri> uri, int index, ArrayList<Uri> realUri){

        Log.v("test","index "+index);
        Uri file = uri.get(index);
        StorageReference imagesRef = storageRef.child("images/"+getAlphaNumericString(10));
        UploadTask uploadTask = imagesRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("test","number"+index+"item upload failed");
                int i = index;
                i++;
                realUri.add(null);
                if(i<uri.size()){
                    getImagesUri(uri,i,realUri);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                int i = index;
                i++;
                realUri.add(imagesRef.getDownloadUrl().getResult());
                if(i<uri.size()){
                    getImagesUri(uri,i,realUri);
                }
            }
        });


    }
}
