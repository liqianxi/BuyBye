package com.example.buybye.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;

import java.util.ArrayList;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView item_recycler_each_num;
        public TextView item_recycler_title_name;
        public TextView item_recycler_each_name;
        public TextView item_recycler_sold_status;
        public TextView item_recycler_title_sold_status;
        public TextView item_recycler_title_desc;
        public TextView item_recycler_each_description;
        public View divider;
        public View divider2;
        public TextView item_recycler_each_price;
        public ViewHolder(View view){
            super(view);
            item_recycler_each_description = view.findViewById(R.id.item_recycler_each_description);
            item_recycler_title_desc = view.findViewById(R.id.item_recycler_title_desc);
            item_recycler_title_sold_status = view.findViewById(R.id.item_recycler_title_sold_status);
            item_recycler_sold_status = view.findViewById(R.id.item_recycler_sold_status);
            item_recycler_each_name = view.findViewById(R.id.item_recycler_each_name);
            item_recycler_title_name = view.findViewById(R.id.item_recycler_title_name);
            item_recycler_each_num = view.findViewById(R.id.item_recycler_each_num);
            divider = view.findViewById(R.id.divider);
            divider2 = view.findViewById(R.id.divider2);
            item_recycler_each_price = view.findViewById(R.id.item_recycler_each_price);
        }
    }
    public ItemRecyclerAdapter(ArrayList<Item> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_recycler_each, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        TextView item_recycler_each_num = holder.item_recycler_each_num;
        TextView item_recycler_each_name = holder.item_recycler_each_name;
        TextView item_recycler_sold_status = holder.item_recycler_sold_status;
        TextView item_recycler_each_description= holder.item_recycler_each_description;
        TextView item_recycler_each_price = holder.item_recycler_each_price;
        item_recycler_each_num.setText(String.format("%s", (position+1)));
        item_recycler_each_name.setText(item.getItemName());
        boolean status = item.isSoldOut();
        if(status){
            item_recycler_sold_status.setText("SOLD OUT");
        }else{
            item_recycler_sold_status.setText("SELLING");
        }
        item_recycler_each_description.setText(item.getDescription());
        item_recycler_each_price.setText(String.format("%s", item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
