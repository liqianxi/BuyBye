package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.entities.ActivityCollector;

import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_search);
        TextView cancel = findViewById(R.id.CancelText);
        ActivityCollector.addActivity(this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
}