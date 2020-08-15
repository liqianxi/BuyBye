package com.example.buybye.database;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.buybye.entities.ChatRoom;
import com.example.buybye.entities.Item;
import com.example.buybye.listeners.ChatRoomListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatDatabaseAccessor extends DatabaseAccessor {
    public static final String TAG = "ChatDatabaseAccessor";
    final String referenceName = "ChatRoom";
    public ChatDatabaseAccessor() {
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
    public void addChatRoom(ChatRoom chatRoom, ChatRoomListener listener){

        this.firestore
                .collection(referenceName)
                .document(chatRoom.getChatRoomId())
                .set(chatRoom)
                .addOnSuccessListener(aVoid -> {
                    Log.v(TAG,"ChatRoom Create Successful");
                    listener.OnChatRoomAddSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.v(TAG,"ChatRoom Create Failure");
                    listener.OnChatRoomAddFailure();
                });

    }

    public void addSnapshotListener(String userId, ChatRoomListener listener){
        this.firestore
                .collection(referenceName)
                .whereArrayContains("users",userId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        ArrayList<ChatRoom> chatRooms = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            chatRooms.add(doc.toObject(ChatRoom.class));
                        }
                        listener.OnSnapShotReceiveSuccess(chatRooms);

                    }
                });
    }
}
