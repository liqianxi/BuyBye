package com.example.buybye.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


public class DatabaseAccessor {
    public static final String TAG = "DatabaseAccessor";
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore firestore;  // cloud database
    FirebaseAuth firebaseAuth;    // register, login
    FirebaseUser currentUser; // logged in current user




    /**
     * Constructor method, objectify the declarations
     */
    DatabaseAccessor() {
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }
    public FirebaseAuth getFirebaseAuth(){

        return firebaseAuth;
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }


}
