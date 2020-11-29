package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.database.ChatDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.ChatRoom;
import com.example.buybye.entities.Message;
import com.example.buybye.listeners.SingleChatRoomListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatRoomActivity extends AppCompatActivity implements SingleChatRoomListener {
    private String userId;
    private String otherName;
    private String roomId;
    private ChatDatabaseAccessor chatDatabaseAccessor = new ChatDatabaseAccessor();
    private ChatRoom chatroom;
    private ArrayList<Message> messages = new ArrayList<>();
    private SingleChatRoomRecyclerAdapter adapter;
    private RecyclerView chatDetailDisplayRecycler;
    private ImageView backButton;
    private ImageView sendMessageImage;
    private TextView chatActivityTitle;
    private EditText inputMessageBar;
    private int userIndex = 0;
    private int otherIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_chat_room);
        ActivityCollector.addActivity(this);

        userId = getIntent().getStringExtra("userId");
        otherName = getIntent().getStringExtra("otherName");
        roomId = getIntent().getStringExtra("roomId");

        chatDetailDisplayRecycler = findViewById(R.id.chatDetailDisplayRecycler);
        backButton = findViewById(R.id.backButton);
        sendMessageImage = findViewById(R.id.sendMessageImage);
        chatActivityTitle = findViewById(R.id.chatActivityTitle);
        inputMessageBar = findViewById(R.id.inputMessageBar);
        chatActivityTitle.setText(otherName);

        adapter = new SingleChatRoomRecyclerAdapter(this,userId,this.messages);
        chatDetailDisplayRecycler.setAdapter(adapter);
        chatDetailDisplayRecycler.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.removeActivity(ChatRoomActivity.this);
                Intent intent = new Intent(ChatRoomActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });
        chatDatabaseAccessor.retrieveChatRoom(roomId, new SingleChatRoomListener() {
            @Override
            public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {
                chatroom = chatRoom;
                for(int i=0;i<chatroom.getUsers().size();i++){
                    if(chatroom.getUsers().get(i).equals(userId)){
                        userIndex = i;
                        Log.v("userIndex", String.valueOf(userIndex));
                    }else{
                        otherIndex = i;
                        Log.v("otherIndex", String.valueOf(otherIndex));
                    }
                }
                //this.messages.clear();
                //this.messages.addAll(chatRoom.getMessages());

                //adapter.notifyDataSetChanged();
                chatroom.messageNumReset(userIndex);
                chatDatabaseAccessor.updateChatRoom(chatroom, new SingleChatRoomListener() {
                    @Override
                    public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {

                    }

                    @Override
                    public void onRetrieveChatRoomFailure() {

                    }

                    @Override
                    public void onUpdateChatRoomSuccess() {
                        sendMessageImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // send message out
                                String text = inputMessageBar.getText().toString();
                                inputMessageBar.setText("");
                                if(text.length()>=30){
                                    chatroom.setMessageSummary(text.substring(0,30));
                                }else{
                                    chatroom.setMessageSummary(text);
                                }
                                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                                chatroom.setRecentMessageDate(timeStamp);
                                Message message = new Message(text,timeStamp,userId);
                                chatroom.getMessages().add(message);
                                chatroom.messageNumAdd((int)otherIndex);//every time other guy send a message, you will for sure get one unread message.
                                Log.v("other unread num", String.valueOf(chatroom.getUnreadNum().get(otherIndex)));
                                chatDatabaseAccessor.updateChatRoom(chatroom, new SingleChatRoomListener() {
                                    @Override
                                    public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {

                                    }

                                    @Override
                                    public void onRetrieveChatRoomFailure() {

                                    }

                                    @Override
                                    public void onUpdateChatRoomSuccess() {

                                    }

                                    @Override
                                    public void onUpdateChatRoomFailure() {

                                    }

                                    @Override
                                    public void onSnapShotRetrieveSuccess(ChatRoom chatRoom) {

                                    }

                                    @Override
                                    public void onSnapShotRetrieveFailure() {

                                    }
                                });

                            }
                        });
                        chatDatabaseAccessor.addSingleRoomSnapshotListener(roomId, new SingleChatRoomListener() {
                            @Override
                            public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {

                            }

                            @Override
                            public void onRetrieveChatRoomFailure() {

                            }

                            @Override
                            public void onUpdateChatRoomSuccess() {

                            }

                            @Override
                            public void onUpdateChatRoomFailure() {

                            }

                            @Override
                            public void onSnapShotRetrieveSuccess(ChatRoom chatRoom) {
                                Log.v("SnapShot","retrieve snapshot for room success");
                                chatroom = chatRoom;
                                Log.v("origin message", String.valueOf(messages.size()));
                                messages.clear();
                                messages.addAll(chatRoom.getMessages());
                                Log.v("size", String.valueOf(messages.size()));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onSnapShotRetrieveFailure() {
                                Log.v("SnapShot","retrieve snapshot for room failure");
                            }
                        });
                    }

                    @Override
                    public void onUpdateChatRoomFailure() {

                    }

                    @Override
                    public void onSnapShotRetrieveSuccess(ChatRoom chatRoom) {

                    }

                    @Override
                    public void onSnapShotRetrieveFailure() {

                    }
                });




            }

            @Override
            public void onRetrieveChatRoomFailure() {

            }

            @Override
            public void onUpdateChatRoomSuccess() {

            }

            @Override
            public void onUpdateChatRoomFailure() {

            }

            @Override
            public void onSnapShotRetrieveSuccess(ChatRoom chatRoom) {

            }

            @Override
            public void onSnapShotRetrieveFailure() {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {

    }

    @Override
    public void onRetrieveChatRoomFailure() {

    }

    @Override
    public void onUpdateChatRoomSuccess() {

    }

    @Override
    public void onUpdateChatRoomFailure() {

    }

    @Override
    public void onSnapShotRetrieveSuccess(ChatRoom chatRoom) {

    }

    @Override
    public void onSnapShotRetrieveFailure() {

    }
    // logic: open activity -> retrieve chatroom -> add single room snap shot listener



}