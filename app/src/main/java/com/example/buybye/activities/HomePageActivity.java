package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.GridView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_home_page);

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("steam",null,12.8,null,null));//Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime)
        items.add(new Item("steam2",null,132.8,null,null));//Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime)
        items.add(new Item("steam3",null,12.8,null,null));//Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime)


        GridView itemList = (GridView)findViewById(R.id.itemList);
        PostItemListAdapter postItemListAdapter = new PostItemListAdapter(this,items);
        itemList.setAdapter(postItemListAdapter);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_page:
                                //openFragment(HomeFragment.newInstance("", ""));
                                return true;
                            case R.id.add_new_post:
                                //openFragment(SmsFragment.newInstance("", ""));
                                return true;
                            case R.id.my_account:
                                //openFragment(NotificationFragment.newInstance("", ""));
                                return true;
                        }
                        return false;
                    }
                    /*
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                openFragment(HomeFragment.newInstance("", ""));
                                return true;
                            case R.id.navigation_sms:
                                openFragment(SmsFragment.newInstance("", ""));
                                return true;
                            case R.id.navigation_notifications:
                                openFragment(NotificationFragment.newInstance("", ""));
                                return true;
                        }
                        return false;
                    }*/
                };
    }
}