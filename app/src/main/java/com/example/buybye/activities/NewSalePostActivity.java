package com.example.buybye.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.example.buybye.R;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.entities.sellerPost;
import com.example.buybye.listeners.ItemAddDeleteListener;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class NewSalePostActivity extends AppCompatActivity implements ItemAddDeleteListener, UserProfileStatusListener {
    private ArrayList<Item> Items = new ArrayList<>();
    private ListView itemsList;
    private PostItemListAdapter adapter;
    private ItemDatabaseAccessor itemDatabaseAccessor = new ItemDatabaseAccessor();
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    private User CurrentUser;
    private ArrayList<sellerPost> currentPosts;
    private EditText postName;
    private EditText postDesc;
    private EditText postPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_post);
        CurrentUser = getIntent().getParcelableExtra("currentUser");
        postName = findViewById(R.id.PostName);
        postDesc = findViewById(R.id.PostDescription);
        postPhoneNum = findViewById(R.id.PostPhoneNumber);
        Button newItemButton = findViewById(R.id.addItemButton);
        Button postButton = findViewById(R.id.postOutButton);
        itemsList = findViewById(R.id.itemListView);
        adapter = new PostItemListAdapter(getApplicationContext(),Items);
        itemsList.setAdapter(adapter);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSalePostActivity.this,AddSingleItemActivity.class);
                startActivityForResult(intent,100);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save post data to the database
                ArrayList<Item> tempList = new ArrayList<>();
                for(int i=0;i<Items.size();i++){
                    Item item = Items.get(i);
                    item.setOwner(CurrentUser);
                    tempList.add(item);
                }
                itemDatabaseAccessor.addItem(tempList,NewSalePostActivity.this);

            }
        });
        Log.v("test","in2");








    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Item item = null;
            if (data != null) {
                item = data.getParcelableExtra("item");
            }
            Items.add(item);
            adapter.notifyDataSetChanged();


        }



    }


    @Override
    public void onItemsAddedSuccess() {
        //all items has been added
        currentPosts = CurrentUser.getSellerPostArray();
        if (currentPosts == null){
            currentPosts = new ArrayList<>();
        }
        sellerPost sellerPostInstance = new sellerPost();
        String postNameString = postName.getText().toString();
        String postDescriptionString = postDesc.getText().toString();
        String phoneNum = postPhoneNum.getText().toString();
        Log.v("test","in5");
        sellerPostInstance.setPhoneNumber(phoneNum);
        sellerPostInstance.setDescriptions(postDescriptionString);
        sellerPostInstance.setPostName(postNameString);
        sellerPostInstance.setItemList(Items);
        currentPosts.add(sellerPostInstance);
        Log.v("test","in3");
        //CurrentUser.setSellerPostArray(currentPosts);
        HashMap<String,Object> updateHash = new HashMap<>();
        updateHash.put("sellerPostArray",currentPosts);
        userDatabaseAccessor.updateUserProfile(CurrentUser,updateHash,NewSalePostActivity.this );
    }

    @Override
    public void onItemsAddedFailure() {

    }

    @Override
    public void onItemDeleteSuccess() {

    }

    @Override
    public void onItemDeleteFailure() {

    }

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
        CurrentUser.setSellerPostArray(currentPosts);
        Log.v("test","here");
        Intent intent = new Intent(NewSalePostActivity.this, NotifyPostSuccessActivity.class);
        startActivity(intent);
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