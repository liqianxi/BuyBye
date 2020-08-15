package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.database.ChatDatabaseAccessor;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.ChatRoom;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.Message;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ChatRoomListener;
import com.example.buybye.listeners.GetSingleItemListener;
import com.example.buybye.listeners.GetSingleUserListener;
import com.example.buybye.listeners.SingleChatRoomListener;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ItemDetailDisplayActivity extends AppCompatActivity implements GetSingleItemListener, GetSingleUserListener, com.example.buybye.activities.ImageRecyclerAdapter.RecyclerViewClickListener, UserProfileStatusListener {
    private ImageRecyclerAdapter ImageRecyclerAdapter;
    private String itemId;
    private Item item;
    private User itemOwner;
    private User currentUser;
    private ChatRoom chatRoom;
    private RecyclerView itemImageDisplayRecyclerView;
    private TextView singleItemDescription;
    private TextView singleItemTitle;
    private TextView singleItemPrice;
    private TextView singleItemOwnerName;
    private ImageView ownerImage;
    private ImageView backButton2;
    private ImageView markIcon;
    private ImageView sayHiIcon;
    private TextView markTitle;
    private TextView sayHiTitle;
    private String roomId;
    private LinearLayout contactOwnerLayout;

    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    private ItemDatabaseAccessor itemDatabaseAccessor = new ItemDatabaseAccessor();
    private ChatDatabaseAccessor chatDatabaseAccessor = new ChatDatabaseAccessor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_display);
        itemId = Objects.requireNonNull(getIntent().getExtras()).getString("itemId");
        bindViews();
        ActivityCollector.addActivity(this);

        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.removeActivity(ItemDetailDisplayActivity.this);
                Intent intent = new Intent(ItemDetailDisplayActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

        // here goto onGetItemSuccess
        itemDatabaseAccessor.getSingleItem(itemId,this);
    }
    private void bindViews(){
        itemImageDisplayRecyclerView = findViewById(R.id.itemImageDisplayRecyclerView);
        backButton2 = findViewById(R.id.backButton);
        singleItemDescription = findViewById(R.id.singleItemDescription);
        singleItemTitle = findViewById(R.id.singleItemTitle);
        singleItemPrice = findViewById(R.id.singleItemPrice);
        singleItemOwnerName = findViewById(R.id.singleItemOwnerName);
        ownerImage = findViewById(R.id.ownerImage);
        markIcon = findViewById(R.id.markIcon);
        sayHiIcon = findViewById(R.id.sayHiIcon);
        markTitle = findViewById(R.id.markTitle);
        sayHiTitle = findViewById(R.id.sayHiTitle);
        contactOwnerLayout = findViewById(R.id.contactOwnerLayout);
    }
    @Override
    protected void onStart() {
        super.onStart();
        userDatabaseAccessor.getUserProfile(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onGetItemSuccess(Item item) {
        this.item = item;
        // success then go to onGetUserSuccess
        userDatabaseAccessor.getSingleUser(item.getOwner(),this);
    }

    @Override
    public void onGetItemFailure() {

    }

    @Override
    public void onGetUserSuccess(User user) {
        this.itemOwner = user;
        ImageRecyclerAdapter = new ImageRecyclerAdapter(item.getPictureArray(),1,this);
        itemImageDisplayRecyclerView.setAdapter(ImageRecyclerAdapter);
        itemImageDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        singleItemDescription.setText(item.getDescription());
        singleItemTitle.setText(item.getItemName());
        singleItemPrice.setText(String.format("%s", item.getPrice()));
        singleItemOwnerName.setText(itemOwner.getUserName());
        ownerImage.setImageResource(R.drawable.ic_launcher_foreground);





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onGetUserFailure() {

    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public boolean onLongClick(View view, int position) {
        return false;
    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.currentUser = user;
        roomId = currentUser.getEmail()+itemOwner.getEmail();

        if(currentUser.getMarkedItems().contains(itemId)){
            markIcon.setImageResource(R.drawable.unstar);

        }else{
            markIcon.setImageResource(R.drawable.star);

        }
        markIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser.getMarkedItems().contains(itemId)){
                    markIcon.setImageResource(R.drawable.unstar);
                    currentUser.getMarkedItems().remove(itemId);
                }else{
                    markIcon.setImageResource(R.drawable.star);
                    currentUser.getMarkedItems().add(itemId);
                }
                HashMap<String,Object> updateHash = new HashMap<>();
                updateHash.put("markedItems",currentUser.getMarkedItems());
                userDatabaseAccessor.updateUserProfile(currentUser, updateHash, new UserProfileStatusListener() {
                    @Override
                    public void onProfileStoreSuccess() {

                    }

                    @Override
                    public void onProfileStoreFailure() {

                    }

                    @Override
                    public void onProfileRetrieveSuccess(User user) {

                    }

                    @Override
                    public void onProfileRetrieveFailure() {

                    }

                    @Override
                    public void onProfileUpdateSuccess(User user) {

                    }

                    @Override
                    public void onProfileUpdateFailure() {

                    }

                    @Override
                    public void onValidateSuccess() {

                    }

                    @Override
                    public void onValidateFailure() {

                    }

                    @Override
                    public void onDeleteSuccess() {

                    }

                    @Override
                    public void onDeleteFailure() {

                    }
                });

            }
        });
        contactOwnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatDatabaseAccessor.retrieveChatRoom(roomId, new SingleChatRoomListener() {
                    @Override
                    public void onRetrieveChatRoomSuccess(ChatRoom chatRoom) {
                        Intent intent = new Intent(getApplicationContext(),ChatRoomActivity.class);
                        ArrayList<String> names = chatRoom.getUserNames();
                        ArrayList<String> ids = chatRoom.getUsers();
                        int otherIndex = 0;
                        for(int i=0;i<ids.size();i++){
                            if(!ids.get(i).equals(currentUser.getEmail())){
                                otherIndex = i;
                            }
                        }
                        intent.putExtra("otherName",names.get(otherIndex));
                        intent.putExtra("roomId",chatRoom.getChatRoomId());
                        intent.putExtra("userId",currentUser.getEmail());

                        startActivity(intent);
                    }

                    @Override
                    public void onRetrieveChatRoomFailure() {
                        //create new room
                        ArrayList<String> users = new ArrayList<>();
                        ArrayList<String> userNames = new ArrayList<>();
                        users.add(currentUser.getEmail());
                        users.add(itemOwner.getEmail());
                        userNames.add(currentUser.getUserName());
                        userNames.add(itemOwner.getUserName());
                        ArrayList<Message> messages = new ArrayList<>();
                        chatRoom = new ChatRoom(users,userNames,currentUser.getEmail()+itemOwner.getEmail(),messages);
                        // if no previous chatroom between two users, then we add a new one
                        chatDatabaseAccessor.addChatRoom(chatRoom, new ChatRoomListener() {
                            @Override
                            public void OnChatRoomAddSuccess() {

                            }

                            @Override
                            public void OnChatRoomAddFailure() {

                            }

                            @Override
                            public void OnSnapShotReceiveSuccess(ArrayList<ChatRoom> chatRooms) {

                            }

                            @Override
                            public void OnSnapShotReceiveFailure() {

                            }

                            @Override
                            public void OnRetrieveSuccess(ArrayList<ChatRoom> chatRooms) {

                            }

                            @Override
                            public void OnRetrieveFailure() {

                            }
                        });
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
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

    }

    @Override
    public void onProfileUpdateFailure() {

    }

    @Override
    public void onValidateSuccess() {

    }

    @Override
    public void onValidateFailure() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFailure() {

    }
}