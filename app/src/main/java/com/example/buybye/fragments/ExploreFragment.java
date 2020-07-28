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
import com.example.buybye.activities.PostItemListAdapter;
import com.example.buybye.activities.SearchActivity;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.entities.Item;
import com.example.buybye.listeners.ItemListRequestListener;

import java.util.ArrayList;
import java.util.Objects;


public class ExploreFragment extends Fragment implements ItemListRequestListener{

    private TextView test;
    private ItemDatabaseAccessor itemDatabaseAccessor;
    private ArrayList<Item> items = new ArrayList<Item>();

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ImageView searchBar = view.findViewById(R.id.searchSaleItem);
        searchBar.setImageResource(R.drawable.searchbar);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        itemDatabaseAccessor = new ItemDatabaseAccessor();
        itemDatabaseAccessor.getAllItems(ExploreFragment.this);

        return view;
    }

    @Override
    public void onGetItemSuccess(ArrayList<Item> items) {
        if(items != null){
            this.items = items;
        }
        GridView itemList = (GridView) Objects.requireNonNull(getView()).findViewById(R.id.itemList);
        PostItemListAdapter postItemListAdapter = new PostItemListAdapter(getContext(),items);
        itemList.setAdapter(postItemListAdapter);
    }

    @Override
    public void onGetItemFailure() {

    }
}