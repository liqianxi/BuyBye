package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;

import java.util.ArrayList;

public class PostItemListAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;
    private Context context;
    PostItemListAdapter(Context context, ArrayList<Item> items){
        super(context,0,items);
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.each_post,parent,false);
        }
        Item item = items.get(position);
        TextView price = view.findViewById(R.id.itemPrice);
        TextView sellerName = view.findViewById(R.id.sellerName);
        TextView itemName = view.findViewById(R.id.ItemName);
        ImageView itemImage = view.findViewById(R.id.itemImage);
        price.setText(String.format("%s", item.getPrice()));
        itemName.setText(item.getItemName());
        itemImage.setImageResource(R.drawable.ic_launcher_foreground);
        return view;
    }
}