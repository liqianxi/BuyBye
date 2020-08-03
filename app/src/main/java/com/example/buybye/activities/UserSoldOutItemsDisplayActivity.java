package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ItemQueryListener;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSoldOutItemsDisplayActivity extends AppCompatActivity implements UserProfileStatusListener, ItemQueryListener {
    private User currentUser;
    private ArrayList<Item> soldItems;

    private ItemRecyclerAdapter itemRecyclerAdapter;
    private RecyclerView soldItemDisplayView;
    private boolean itemType = true;
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
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
        setContentView(R.layout.activity_user_sold_out_items_display);
        soldItemDisplayView = findViewById(R.id.soldItemDisplayView);
        userDatabaseAccessor.getUserProfile(this);

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
        userDatabaseAccessor.getUserPostItemsWithQuery(this,itemType);

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

    @Override
    public void onQueryFinish(List<Item> items) {
        soldItems = new ArrayList<Item>(items);
        itemRecyclerAdapter = new ItemRecyclerAdapter(soldItems);
        soldItemDisplayView.setAdapter(itemRecyclerAdapter);
        soldItemDisplayView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onQueryFailure() {

    }
}