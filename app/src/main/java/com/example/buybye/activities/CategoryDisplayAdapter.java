package com.example.buybye.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.Catagory;

import java.util.ArrayList;

public class CategoryDisplayAdapter extends RecyclerView.Adapter<CategoryDisplayAdapter.MyViewHolder> {

    private ArrayList<Catagory> catagories;
    private CategoryDisplayAdapter.RecyclerViewClickListener mListener;



    public interface RecyclerViewClickListener {

        void onClick(View view, int position);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView CatagoryName;
        public MyViewHolder(View view, CategoryDisplayAdapter.RecyclerViewClickListener listener){
            super(view);
            cardView = view.findViewById(R.id.cardForCatagory);
            CatagoryName = view.findViewById(R.id.CatagoryName);
            mListener = listener;
            view.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
    public CategoryDisplayAdapter(ArrayList<Catagory> catagories, CategoryDisplayAdapter.RecyclerViewClickListener listener){
        this.catagories = catagories;
        this.mListener = listener;
    }




    @NonNull
    @Override
    public CategoryDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_category_display_card,parent,false);

        CategoryDisplayAdapter.MyViewHolder myViewHolder = new CategoryDisplayAdapter.MyViewHolder(view,mListener);
        return myViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CategoryDisplayAdapter.MyViewHolder holder, int position) {
        TextView CatagoryName = holder.CatagoryName;
        CardView cardView = holder.cardView;
        CatagoryName.setText(catagories.get(position).getName());
        cardView.setBackgroundResource(catagories.get(position).getImage());
        CatagoryName.setTextColor(R.color.ColorDarkGray);

    }

    @Override
    public int getItemCount() {
        return catagories.size();
    }
}
