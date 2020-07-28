package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.buybye.R;

public class NotifyPostSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_post_success);
        TextView title = findViewById(R.id.PostSuccessTitle);
        Button backToFrontPageButton = findViewById(R.id.BackToFrontPageButton);


    }
}