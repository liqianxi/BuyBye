package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.buybye.R;
import com.example.buybye.entities.sellerPost;

import java.util.ArrayList;
import java.util.Calendar;

public class PostsDisplayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_display);

        recyclerView = (RecyclerView) findViewById(R.id.itemRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);


        // specify an adapter (see also next example)
        ArrayList<sellerPost> list = new ArrayList<>();
        sellerPost sellerPost0 = new sellerPost();
        sellerPost0.setPostDate(Calendar.getInstance().getTime());
        sellerPost0.setPostName("pure honey");
        sellerPost0.setDescriptions("pure");
        list.add(sellerPost0);


        mAdapter = new PostsProfileDisplayAdapter(list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);




    }
}