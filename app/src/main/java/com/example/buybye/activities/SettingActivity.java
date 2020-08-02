package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private TextView settingsTitle;
    private TextView UserInfoButton;
    private Button SignOutButton;
    private UserDatabaseAccessor userDatabaseAccessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_setting);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActivityCollector.addActivity(this);
        userDatabaseAccessor = new UserDatabaseAccessor();
        settingsTitle = findViewById(R.id.settingsTitle);
        UserInfoButton = findViewById(R.id.UserInfoButton);
        SignOutButton = findViewById(R.id.signOutButton);
        SignOutButton.setOnClickListener(view -> {
            //sign out
            userDatabaseAccessor.logoutUser();
            // go to the login activity again:
            Toast.makeText(getApplicationContext(),
                    "You are Logged out!", Toast.LENGTH_LONG).show();
            ActivityCollector.finishAll();
            Intent intent = new Intent(getApplicationContext(), LoginContainerActivity.class);
            startActivity(intent);
            finish();

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}