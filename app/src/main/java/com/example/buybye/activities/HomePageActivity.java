package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.fragments.AccountFragment;
import com.example.buybye.fragments.ExploreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar


        setContentView(R.layout.activity_home_page);
        currentUser = (User) getIntent().getParcelableExtra("UserObject");
        Log.v("test",currentUser.getUserName()); // here correct
        Log.v("test","123213");
        switchFragment(R.layout.fragment_explore);
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        //int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_FULLSCREEN;
       // decorView.setSystemUiVisibility(uiOptions);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            switchFragment(R.layout.fragment_explore);

                            return true;
                        case R.id.check_buy_demand:
                            switchFragment(R.layout.fragment_wish_to_get);
                            return true;
                        case R.id.my_account:
                            switchFragment(R.layout.fragment_account);
                            return true;
                    }
                    return false;
                };
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void switchFragment(int caseId){
        androidx.fragment.app.FragmentManager t = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelable("User",currentUser);
        switch(caseId){
            case R.layout.fragment_explore:
                Fragment exploreFragment = new ExploreFragment();
                exploreFragment.setArguments(bundle);
                t.beginTransaction().replace(R.id.frame, exploreFragment).commit();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.layout.fragment_account:
                Fragment accountFragment = new AccountFragment();
                t.beginTransaction().replace(R.id.frame, accountFragment).commit();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case -1:
                break;
        }

    }

}