package com.example.buybye.database;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

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
    public ArrayList<Uri> getImagesUri(ArrayList<Uri> uri, int index, ArrayList<Uri> realUri) {
        final ArrayList<Uri> result = realUri;
        Log.v("test0", String.valueOf(uri.size()));
        Log.v("test", "index " + index);

        Uri file = uri.get(index);
        String name = "images/" + getAlphaNumericString(10);
        StorageReference imagesRef = storageRef.child(name);
        UploadTask uploadTask = imagesRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.v("test", "number" + index + "item upload failed");

                int i = index;
                i++;
                result.add(null);
                if (i < uri.size()) {
                    getImagesUri(uri, i, result);
                }
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

                            int i = index;
                            i++;
                            Uri downloadUri = task.getResult();
                            Log.v("result:", downloadUri.toString());
                            result.add(downloadUri);
                            if (i < uri.size()) {
                                Log.v("resultvalue", String.valueOf(result.size()));
                                getImagesUri(uri, i, result);
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

        return result;
    }
}
