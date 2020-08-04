package com.example.buybye.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.example.buybye.entities.Catagory;

import java.util.ArrayList;

public class CategoryListRecyclerAdapter extends RecyclerView.Adapter<CategoryListRecyclerAdapter.MyViewHolder>  {
    private ArrayList<String> categories;
    private CategoryListRecyclerAdapter.RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void onClick(View view, int position);


    }
    public CategoryListRecyclerAdapter(ArrayList<String> categories, CategoryListRecyclerAdapter.RecyclerViewClickListener listener){
        this.categories = categories;
        this.mListener = listener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView enterArrow;
        public TextView CategoryNameTitle;
        public MyViewHolder(View view, CategoryListRecyclerAdapter.RecyclerViewClickListener listener){
            super(view);
            enterArrow = view.findViewById(R.id.enterArrow);
            CategoryNameTitle = view.findViewById(R.id.CategoryNameTitle);
            mListener = listener;
            view.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public CategoryListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_adapter_each,parent,false);

        CategoryListRecyclerAdapter.MyViewHolder myViewHolder = new CategoryListRecyclerAdapter.MyViewHolder(view,mListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListRecyclerAdapter.MyViewHolder holder, int position) {
        ImageView enterArrow = holder.enterArrow;
        TextView CategoryNameTitle = holder.CategoryNameTitle;
        enterArrow.setImageResource(R.drawable.arrow_right);
        CategoryNameTitle.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
