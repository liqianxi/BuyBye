package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.GetSingleItemListener;
import com.example.buybye.listeners.GetSingleUserListener;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.HashMap;

public class ItemDetailDisplayActivity extends AppCompatActivity implements GetSingleItemListener, GetSingleUserListener, com.example.buybye.activities.ImageRecyclerAdapter.RecyclerViewClickListener, UserProfileStatusListener {
    private ImageRecyclerAdapter ImageRecyclerAdapter;
    private String itemId;
    private Item item;
    private User itemOwner;
    private User currentUser;
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

    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    private ItemDatabaseAccessor itemDatabaseAccessor = new ItemDatabaseAccessor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_display);
        itemId = getIntent().getExtras().getString("itemId");
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



        // here goto onGetItemSuccess
        itemDatabaseAccessor.getSingleItem(itemId,this);
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

        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.removeActivity(ItemDetailDisplayActivity.this);
                Intent intent = new Intent(ItemDetailDisplayActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });


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