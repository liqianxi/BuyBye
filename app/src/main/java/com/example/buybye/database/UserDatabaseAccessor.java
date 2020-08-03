package com.example.buybye.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.GetSingleUserListener;
import com.example.buybye.listeners.ItemQueryListener;
import com.example.buybye.listeners.LoginStatusListener;
import com.example.buybye.listeners.SignUpStatusListener;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Author: Shway Wang.
 * Version: 1.0.1
 * UserDatabaseAccessor class extends all access interfaces. Provides all user related access
 * methods.
 */
public class UserDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "UserDatabaseAccessor";
    protected final String referenceName = "Users";

    /**
     * Constructor, default setting, call super().
     */
    public UserDatabaseAccessor() {
        super();
    }

    /**
     * Determine whether the current user is logged in or not
     * @return
     *      returns a boolean, if logged in, true; other wise, false.
     */
    public boolean isLoggedin() {
        return (firebaseAuth.getCurrentUser() != null);
    }

    /**
     * Void method to log out the current logged in user
     */
    public void logoutUser() {
        this.firebaseAuth.signOut();
    }

    /**
     * Create a new user record in the database according to the information in the past in user
     * object.
     * @param user
     *      the user object containing information to create the new record in database
     * @param listener
     *      if the record is added successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void registerUser(User user, final SignUpStatusListener listener) {
        if (this.isLoggedin()) {    // if the user is logged in, logout first
            this.logoutUser();
        }
        // create a user.
        this.firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.v(TAG, "registered successfully!");
                            listener.onRegisterSuccess();
                        } else {
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                Log.v(TAG, "onComplete: weak_password");
                                listener.onWeakPassword();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Log.v(TAG, "onComplete: malformed_email");
                                listener.onInvalidEmail();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Log.v(TAG, "onComplete: exist_email");
                                listener.onUserAlreadyExist();
                            } catch (Exception e) {
                                Log.v(TAG, "onComplete: " + e.getMessage());
                                listener.onRegisterFailure();
                            }
                        }
                    }
                });
    }

    /**
     * Login the user according to the email and password contained in the user object past in.
     * @param user
     *      the user object containing the information required to login.
     * @param listener
     *      if the user is logged in successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void loginUser(User user, final LoginStatusListener listener) {
        // login the rider:
        if (this.isLoggedin()) {
            Log.v(TAG, "Login successfully!");
            listener.onLoginSuccess();
        } else {
            this.firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "Login successfully!");
                            listener.onLoginSuccess();
                        } else {
                            Log.v(TAG, "Login failed!");
                            listener.onLoginFailure();
                        }
                    });
        }
    }

    /**
     * Add the detailed information of the user to a separate part in the database. password is set
     * to null due to security measures.
     * @param user
     *      the object containing all information of the current user, including email and password.
     * @param listener
     *      if the info is stored successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void createUserProfile(User user, final UserProfileStatusListener listener) {
        Log.v(TAG, "Ready to create user profile.");
        // should not let any one see the password!
        user.setPassword(null);
        // check if logged in:
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null) {
            // the user is logged in successfully
            Log.v(TAG, "User is logged in!");
            Log.v(TAG, "Ready to store user information!");
            String Id = setUserId(user);
            this.firestore
                    .collection(referenceName)
                    .document(Objects.requireNonNull(this.currentUser.getEmail()))
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        Log.v(TAG, "User info saved!");
                        listener.onProfileStoreSuccess();
                    })
                    .addOnFailureListener(e -> {
                        Log.v(TAG, "User info did not save successfully!");
                        listener.onProfileStoreFailure();
                    });
        } else {    // the user is not logged in
            Log.v(TAG, "User is not logged in!");
        }
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
    public String setUserId(User currentUser){
        String Id = getAlphaNumericString(8);
        if(currentUser.getEmail() != null){
            Id = currentUser.getEmail();
        }
        return Id;
    }
    /**
     * Update the user profile according to the User object past in. The key used is the Uid
     * assigned by firestore automatically
     * @param user
     *      the user object containing new information
     * @param listener
     *      if the user is updated successfully, call the onSuccess method, otherwise, onFailure.
     */
    public void updateUserProfile(final User user, HashMap<String,Object> hashmap, final UserProfileStatusListener listener) {
        Log.v(TAG, "Ready to create user profile.");
        // should not let any one see the password!
        user.setPassword(null);
        // check if logged in:
        FirebaseFirestore firestore = getFirestore();
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).reload().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    // the user is logged in successfully
                    Log.v(TAG, "User is logged in!");
                    Log.v(TAG, "Ready to store user information!");
                    Map<String, Object> map = hashmap;



                    firestore
                            .collection(referenceName)
                            .document(Objects.requireNonNull(currentUser.getEmail()))
                            .update(map)
                            .addOnSuccessListener(e -> {
                                Log.v(TAG, "User info updated!");
                                listener.onProfileUpdateSuccess(user);
                            })
                            .addOnFailureListener(e -> {
                                Log.v(TAG, "User info did not update successfully!");
                                listener.onProfileUpdateFailure();
                            });
                } else {    // the user is not logged in
                    Log.v(TAG, "User is not logged in!");
                }
            }
        });

    }
    /**
     * Get the whole set of user information from the collection "Users". Password is null due to
     * security measures.
     * @param listener
     *      if the user info is retrieved successfully, call the onSuccess method,
     *      otherwise, onFailure.
     */
    public void getUserProfile(final UserProfileStatusListener listener) {
        this.currentUser = firebaseAuth.getCurrentUser();
        // check if logged in:
        if (this.currentUser != null) {
            Objects.requireNonNull(this.firestore
                    .collection(this.referenceName)
                    .document(Objects.requireNonNull(currentUser.getEmail()))
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Log.v(TAG, "Get User Successfully!");
                        if (documentSnapshot.exists()) {
                            listener.onProfileRetrieveSuccess(documentSnapshot
                                    .toObject(User.class));
                        }
                    }).addOnFailureListener(e -> {
                        Log.v(TAG, "Get User Failed!");
                        listener.onProfileRetrieveFailure();
                    }));
        } else {
            Log.v(TAG, "User is not logged in!");
        }
    }
    public void isValidated(final UserProfileStatusListener listener){
        //FirebaseAuth.getInstance().getCurrentUser()

        firebaseAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    boolean emailVerified = currentUser.isEmailVerified();
                    if(emailVerified){
                        Log.v(TAG,"Validate success");
                        listener.onValidateSuccess();

                    }else{
                        Log.v(TAG,"Validate failed");
                        listener.onValidateFailure();
                    }
                } else {
                    Log.v(TAG, "User is not logged in!");
                }
            }
        });

    }
    public void deleteUser(final UserProfileStatusListener listener){
        this.currentUser = firebaseAuth.getCurrentUser();
        if (this.currentUser != null && !this.currentUser.isEmailVerified()) {
            this.currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "User account deleted.");
                        listener.onDeleteSuccess();
                    }
                    else{
                        Log.d(TAG, "User account not deleted.");
                        listener.onDeleteFailure();
                    }
                }
            });
        }
    }
    public void getUserPostItemsWithQuery(final ItemQueryListener listener,boolean isSoldOut){
        this.currentUser = firebaseAuth.getCurrentUser();

        if (this.currentUser != null) {
            String email = this.currentUser.getEmail();
            this.firestore
                    .collection("Items")
                    .whereEqualTo("owner",email)
                    .whereEqualTo("soldOut",isSoldOut).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        Log.v(TAG,"get query result success");
                        listener.onQueryFinish(queryDocumentSnapshots.toObjects(Item.class));
                    }).addOnFailureListener(e -> {
                        Log.v(TAG,"get query result failure");
                        listener.onQueryFailure();
                    });

        }

    }
    public void getSingleUser(String userId, GetSingleUserListener listener){
        this.firestore
                .collection(referenceName)
                .document(userId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.v(TAG,"get user successful");
                User user = documentSnapshot.toObject(User.class);

                listener.onGetUserSuccess(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v(TAG,"get user failed");
                listener.onGetUserFailure();
            }
        });
    }
}
