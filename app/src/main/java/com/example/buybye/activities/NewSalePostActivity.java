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
import com.example.buybye.listeners.ItemAddDeleteListener;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewSalePostActivity extends AppCompatActivity implements ItemAddDeleteListener {
    private ArrayList<Item> Items = new ArrayList<>();
    private ListView itemsList;
    private PostItemListAdapter adapter;
    private ItemDatabaseAccessor itemDatabaseAccessor = new ItemDatabaseAccessor();
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    private int counter = -1;
    private User CurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_post);
        CurrentUser = getIntent().getParcelableExtra("currentUser");
        EditText postName = findViewById(R.id.PostName);
        EditText postDesc = findViewById(R.id.PostDescription);
        EditText postPhoneNum = findViewById(R.id.PostPhoneNumber);
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
                for(int i=0;i<Items.size();i++){
                    itemDatabaseAccessor.addItem(Items.get(i),NewSalePostActivity.this);
                }

            }
        });
        if(counter!=-1 && counter == (Items.size()-1)){
            //all items has been added
            
            Intent intent = new Intent(NewSalePostActivity.this, NotifyPostSuccessActivity.class);
            startActivity(intent);
        }




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
    public void onItemAddedSuccess() {
        counter++;

    }

    @Override
    public void onItemAddedFailure() {

    }

    @Override
    public void onItemDeleteSuccess() {

    }

    @Override
    public void onItemDeleteFailure() {

    }
}