package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.buybye.R;
import com.example.buybye.entities.ActivityCollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AllCategoriesDisplayActivity extends AppCompatActivity {
    private ArrayList<String> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        ActivityCollector.addActivity(this);
        categories = new ArrayList<>();
        categories.addAll(Arrays.asList(getResources().getStringArray(R.array.Categories)));
        setContentView(R.layout.activity_all_categories_display);
        RecyclerView recyclerView = findViewById(R.id.CategoryListRecyclerView);
        CategoryListRecyclerAdapter adapter = new CategoryListRecyclerAdapter(categories, (view, position) -> {
            //enter one category
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setImageResource(R.drawable.back_icon);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.removeActivity(AllCategoriesDisplayActivity.this);
                Intent intent = new Intent(AllCategoriesDisplayActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}