package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.activities.NewSalePostActivity;
import com.example.buybye.activities.PostItemListAdapter;
import com.example.buybye.activities.SearchActivity;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ItemListRequestListener;

import java.util.ArrayList;
import java.util.Objects;


public class ExploreFragment extends Fragment implements ItemListRequestListener{

    private TextView test;
    private ItemDatabaseAccessor itemDatabaseAccessor;
    private ArrayList<Item> items = new ArrayList<Item>();
    private User currentUser;
    private TextView addNewPost;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentUser = bundle.getParcelable("User");
        }

        addNewPost = view.findViewById(R.id.addPost);
        ImageView searchBar = view.findViewById(R.id.searchSaleItem);
        searchBar.setImageResource(R.drawable.searchbar);
        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewSalePostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("currentUser",currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        itemDatabaseAccessor = new ItemDatabaseAccessor();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        itemDatabaseAccessor.getAllItems(ExploreFragment.this);
    }

    @Override
    public void onGetItemSuccess(ArrayList<Item> items) {
        if(items != null){
            this.items = items;

        }
        Log.v("test", String.valueOf(items.size()));

        GridView itemList = (GridView) Objects.requireNonNull(getView()).findViewById(R.id.itemList);
        PostItemListAdapter postItemListAdapter = new PostItemListAdapter(getContext(),items);
        itemList.setAdapter(postItemListAdapter);
    }

    @Override
    public void onGetItemFailure() {

    }
}