package com.example.buybye.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.buybye.R;
import com.example.buybye.activities.PostItemListAdapter;
import com.example.buybye.entities.Item;

import java.util.ArrayList;


public class ExploreFragment extends Fragment {




    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        // Inflate the layout for this fragment
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("steam",null,12.8,null,null));//Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime)
        items.add(new Item("steam2",null,132.8,null,null));//Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime)
        items.add(new Item("steam3",null,12.8,null,null));//Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime)

        Log.v("in fragment","666");
        GridView itemList = (GridView) view.findViewById(R.id.itemList);
        PostItemListAdapter postItemListAdapter = new PostItemListAdapter(getContext(),items);
        itemList.setAdapter(postItemListAdapter);
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
}