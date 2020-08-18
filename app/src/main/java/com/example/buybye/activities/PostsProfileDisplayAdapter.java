package com.example.buybye.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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
        public TextView postTitleInList;
        public TextView postItemAmount;

        public TextView postDateInView;
        public TextView itemListTitle;
        public FoldingCell fc;
        public RecyclerView itemRecyclerView;

        public MyViewHolder(View view){
            super(view);
            postTitleInList = view.findViewById(R.id.postTitleInList);
            postDateInView = view.findViewById(R.id.postDateInView);
            itemListTitle = view.findViewById(R.id.itemListTitle);
            postItemAmount = view.findViewById(R.id.ItemAmountText);
            fc = view.findViewById(R.id.folding_cell);
            itemRecyclerView = view.findViewById(R.id.itemRecyclerView);


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
        View view = inflater.inflate(R.layout.each_item_folding_layout,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsProfileDisplayAdapter.MyViewHolder holder, int position) {
        sellerPost post = posts.get(position);
        TextView postTitleInList = holder.postTitleInList;
        postTitleInList.setText(post.getPostName());
        TextView postDateInView = holder.postDateInView;
        postDateInView.setText(post.getPostDate().toString());
        TextView itemListTitle = holder.itemListTitle;
        itemListTitle.setText("title");
        TextView amount = holder.postItemAmount;
        amount.setText(String.format("Total Items Amount %d", post.getItemList().size()));
        RecyclerView itemRecyclerView = holder.itemRecyclerView;

        ArrayList<Item> items = post.getItemList();
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(items, new ItemRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Item item = items.get(position);

                Intent intent = new Intent(view.getContext(), ItemDetailDisplayActivity.class);
                intent.putExtra("itemId",item.getItemId());
                view.getContext().startActivity(intent);

            }
        });

        // Attach the adapter to the recyclerview to populate items
        itemRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(holder.postTitleInList.getContext()));
        itemRecyclerView.addItemDecoration(new DividerItemDecoration(itemRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        FoldingCell fc = holder.fc;
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fc.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
