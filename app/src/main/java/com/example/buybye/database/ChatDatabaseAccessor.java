package com.example.buybye.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buybye.entities.ChatRoom;
import com.example.buybye.entities.Item;
import com.example.buybye.listeners.ChatRoomListener;
import com.example.buybye.listeners.SingleChatRoomListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Objects.requireNonNull;

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
    public void getRelatedRooms(String userId,ChatRoomListener listener){
        this.firestore
                .collection(referenceName)
                .whereArrayContains("users",userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if(task.isSuccessful()){
                                                   ArrayList<ChatRoom> chatRooms = new ArrayList<>();
                                                   for (QueryDocumentSnapshot document : requireNonNull(task.getResult())) {
                                                       ChatRoom chatRoom = document.toObject(ChatRoom.class);
                                                       chatRooms.add(chatRoom);
                                                   }
                                                   listener.OnRetrieveSuccess(chatRooms);

                                               }

                                               else{
                                                   listener.OnRetrieveFailure();
                                               }
                                           }
                                       }
                );
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
    public void retrieveChatRoom(String roomId, SingleChatRoomListener listener){
        this.firestore
                .collection(referenceName)
                .document(roomId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ChatRoom chatRoom = task.getResult().toObject(ChatRoom.class);
                        if(task.isSuccessful() && chatRoom!=null){
                            Log.v("test123",chatRoom.getChatRoomId());
                            listener.onRetrieveChatRoomSuccess(chatRoom);
                        }

                        else{
                            listener.onRetrieveChatRoomFailure();
                        }
                    }
                });

    }
    public void updateChatRoom(ChatRoom chatRoom, SingleChatRoomListener listener){

        this.firestore
                .collection(referenceName)
                .document(chatRoom.getChatRoomId())
                .update("Messages",chatRoom.getMessages()
                        ,"recentMessageDate",chatRoom.getRecentMessageDate()
                        ,"messageSummary",chatRoom.getMessageSummary()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onUpdateChatRoomSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onUpdateChatRoomFailure();
            }
        })


        ;
    }
    public void addSingleRoomSnapshotListener(String roomId, SingleChatRoomListener listener){
        this.firestore
                .collection(referenceName)
                .document(roomId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        ChatRoom chatRoom = value.toObject(ChatRoom.class);
                        Log.v("inside",chatRoom.getChatRoomId());
                        if(chatRoom != null){
                            listener.onSnapShotRetrieveSuccess(chatRoom);
                        }else{
                            listener.onSnapShotRetrieveFailure();
                        }
                    }
                });
    }
}