package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.User;
import com.example.buybye.fragments.AccountFragment;
import com.example.buybye.fragments.ExploreFragment;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class HomePageActivity extends AppCompatActivity implements UserProfileStatusListener {
    private BottomNavigationView bottomNavigationView;
    private User currentUser;
    private UserDatabaseAccessor userDatabaseAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        userDatabaseAccessor = new UserDatabaseAccessor();
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_home_page);
        currentUser = (User) getIntent().getParcelableExtra("UserObject");

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
        userDatabaseAccessor.getUserProfile(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                accountFragment.setArguments(bundle);
                t.beginTransaction().replace(R.id.frame, accountFragment).commit();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case -1:
                break;
        }

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