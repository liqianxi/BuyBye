package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.Objects;

public class FrontPageActivity extends AppCompatActivity implements UserProfileStatusListener {
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_front_page);
        ActivityCollector.addActivity(this);
        ImageView FrontPageIcon = findViewById(R.id.FrontPageIcon);
        ImageView FrontPageSlogan = findViewById(R.id.FrontPageSlogan);
        FrontPageIcon.setImageResource(R.drawable.buybyeicon);
        FrontPageSlogan.setImageResource(R.drawable.slogan);
        if (this.userDatabaseAccessor.isLoggedin()) {
            userDatabaseAccessor.getUserProfile(this);
        }else{
            Intent intent = new Intent(FrontPageActivity.this,LoginContainerActivity.class);
            startActivity(intent);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        Bundle bundle = new Bundle();
        // put the user object into the bundle, Profile activity can access directly:
        bundle.putParcelable("UserObject", user);
        intent.putExtras(bundle);
        startActivity(intent);

        finish();
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