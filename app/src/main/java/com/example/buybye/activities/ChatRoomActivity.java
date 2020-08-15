package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
    private ArrayList<Message> messages;
    private SingleChatRoomRecyclerAdapter adapter;
    private RecyclerView chatDetailDisplayRecycler;
    private ImageView backButton;
    private ImageView sendMessageImage;
    private TextView chatActivityTitle;
    private EditText inputMessageBar;

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
        setRecycler();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.removeActivity(ChatRoomActivity.this);
                Intent intent = new Intent(ChatRoomActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });
        chatDatabaseAccessor.addSingleRoomSnapshotListener(roomId,this);
        chatDatabaseAccessor.retrieveChatRoom(roomId,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void setRecycler(){
        adapter = new SingleChatRoomRecyclerAdapter(userId,messages);
        chatDetailDisplayRecycler.setAdapter(adapter);
        chatDetailDisplayRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {
        this.chatroom = chatRoom;
        sendMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send message out
                String text = inputMessageBar.getText().toString();
                if(text.length()>=30){
                    chatRoom.setMessageSummary(text.substring(0,30));
                }else{
                    chatRoom.setMessageSummary(text);
                }
                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss").format(new Date());
                chatRoom.setRecentMessageDate(timeStamp);
                Message message = new Message(text,timeStamp,userId);//(String text, String date, String sender, ArrayList<String> pictures)
                chatRoom.getMessages().add(message);
                chatDatabaseAccessor.updateChatRoom(chatRoom, new SingleChatRoomListener() {
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
        this.chatroom = chatRoom;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSnapShotRetrieveFailure() {

    }

}