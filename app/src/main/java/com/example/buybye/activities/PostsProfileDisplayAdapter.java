package com.example.buybye.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.Post;
import com.example.buybye.entities.sellerPost;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class PostsProfileDisplayAdapter extends RecyclerView.Adapter<PostsProfileDisplayAdapter.MyViewHolder>{
    private ArrayList<sellerPost> posts;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView postName;
        public TextView postDate;
        public TextView postDescription;
        public Button detailButton;
        public RecyclerView itemRecyclerView;

        public MyViewHolder(View view){
            super(view);
            postName = view.findViewById(R.id.postName);
            postDate = view.findViewById(R.id.postDate);
            postDescription = view.findViewById(R.id.postDescription);
            detailButton = view.findViewById(R.id.detailsButton);
            itemRecyclerView = view.findViewById(R.id.itemRecyclerView);
            detailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }
    }
    public PostsProfileDisplayAdapter(ArrayList<sellerPost> posts){
        this.posts = posts;


    }


    @NonNull
    @Override
    public PostsProfileDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item_folding_layout, parent, false);
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_post_normal,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsProfileDisplayAdapter.MyViewHolder holder, int position) {
        sellerPost post = posts.get(position);
        TextView postName = holder.postName;
        postName.setText(post.getPostName());
        TextView postDate = holder.postDate;
        postDate.setText(post.getPostDate().toString());
        TextView postDescription = holder.postDescription;
        postDescription.setText(post.getDescriptions());


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
