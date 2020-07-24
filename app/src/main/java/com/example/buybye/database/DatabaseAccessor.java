package com.example.buybye.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


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
}
