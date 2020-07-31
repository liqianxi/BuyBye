package com.example.buybye.database;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buybye.entities.Item;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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
    public interface CustomListener{
        public void onSingleReady(ArrayList<Uri> uris, int curIndex);
        public void onAllReady(ArrayList<Uri> uris);
    }
    private CustomListener listener;
    public void setListener(CustomListener listener){
        this.listener = listener;
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


    public void getImagesUri(ArrayList<Uri> uri0, int index, ArrayList<Uri> realUri) {
        if(index >= uri0.size()){
            listener.onAllReady(realUri);
        }
        else{
            Uri uri = uri0.get(index);
            final ArrayList<Uri> result = realUri;
            String name = "images/" + getAlphaNumericString(10);
            StorageReference imagesRef = storageRef.child(name);
            UploadTask uploadTask = imagesRef.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.v("test", "number" + index + "item upload failed");
                    result.add(null);

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return imagesRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                Uri downloadUri = task.getResult();
                                result.add(downloadUri);
                                if(listener !=null && index < uri0.size()){
                                    listener.onSingleReady(result, index);
                                }
                            } else {
                                Log.v("test", "failed get real uri");
                                // Handle failures
                                // ...
                            }
                        }
                    });
                }
            });

    }}

}
