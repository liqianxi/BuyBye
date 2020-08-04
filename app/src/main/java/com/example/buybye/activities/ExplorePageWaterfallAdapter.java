package com.example.buybye.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExplorePageWaterfallAdapter extends RecyclerView.Adapter<ExplorePageWaterfallAdapter.MyViewHolder> {
    private ArrayList<Item> items;
    private RecyclerViewClickListener mListener;



    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
        boolean onLongClick(View view, int position);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView ItemName;
        private ImageView itemImage;
        private TextView itemPrice;
        private TextView sellerName;
        private RecyclerViewClickListener mListener;
        public MyViewHolder(View view, RecyclerViewClickListener listener){
            super(view);
            ItemName = view.findViewById(R.id.ItemName);
            itemImage = view.findViewById(R.id.itemImage);
            itemPrice = view.findViewById(R.id.itemPrice);
            sellerName = view.findViewById(R.id.sellerName);
            mListener = listener;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            mListener.onLongClick(view, getAdapterPosition());
            return true;
        }
    }


    public ExplorePageWaterfallAdapter(ArrayList<Item> items,RecyclerViewClickListener listener){
        this.items = items;
        this.mListener = listener;
    }
    @NonNull
    @Override
    public ExplorePageWaterfallAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.each_post,parent,false);
        ExplorePageWaterfallAdapter.MyViewHolder myViewHolder = new ExplorePageWaterfallAdapter.MyViewHolder(view,mListener);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExplorePageWaterfallAdapter.MyViewHolder holder, int position) {
        TextView ItemName = holder.ItemName;
        ImageView itemImage = holder.itemImage;
        TextView itemPrice = holder.itemPrice;
        TextView sellerName = holder.sellerName;
        Item item = items.get(position);
        ItemName.setText(item.getItemName());
        itemPrice.setText(String.format("%s", item.getPrice()));
        sellerName.setText(item.getOwner());

        if (item!=null && item.getPictureArray() != null){
            String uri = item.getPictureArray().get(0);
            if (uri == null){
                itemImage.setImageResource(R.drawable.ic_launcher_foreground);
            }
            else{
                try {
                    //final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                    //getContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    Picasso.get().load(uri).fit().into(itemImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }}

    @Override
    public int getItemCount() {
        return items.size();
    }


}
