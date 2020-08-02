package com.example.buybye.activities;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;

import java.util.ArrayList;

public class CatogoryDisplayAdapter extends RecyclerView.Adapter<CatogoryDisplayAdapter.MyViewHolder> {

    private ArrayList<String> catagories;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView CatagoryName;
        public MyViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.cardForCatagory);
            CatagoryName = view.findViewById(R.id.CatagoryName);


        }



    }
    public CatogoryDisplayAdapter(ArrayList<String> catagories){
        this.catagories = catagories;

    }




    @NonNull
    @Override
    public CatogoryDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_catagory_display_card,parent,false);

        CatogoryDisplayAdapter.MyViewHolder myViewHolder = new CatogoryDisplayAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatogoryDisplayAdapter.MyViewHolder holder, int position) {
        TextView CatagoryName = holder.CatagoryName;
        CardView cardView = holder.cardView;
        CatagoryName.setText(catagories.get(position));
        cardView.setBackgroundResource(R.drawable.shoes_catagory);

    }

    @Override
    public int getItemCount() {
        return catagories.size();
    }
}
