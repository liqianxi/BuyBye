package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        backToFrontPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotifyPostSuccessActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

    }
}