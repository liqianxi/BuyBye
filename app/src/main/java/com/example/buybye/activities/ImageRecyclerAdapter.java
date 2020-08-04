package com.example.buybye.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>  {
    private ArrayList<String> images;
    private int typeCode;
    private ImageRecyclerAdapter.RecyclerViewClickListener mListener;
    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
        boolean onLongClick(View view, int position);

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView imageRecyclerView;

        public ViewHolder(View view){
            super(view);
            imageRecyclerView = view.findViewById(R.id.imageRecyclerView);
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
    public ImageRecyclerAdapter(ArrayList<String> images, int typeCode,ImageRecyclerAdapter.RecyclerViewClickListener listener){
        this.images = images;
        this.mListener = listener;
        this.typeCode = typeCode;
    }



    @NonNull
    @Override
    public ImageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layout = R.layout.image_recycler;
        // Inflate the custom layout
        if(typeCode == 2){
            layout = R.layout.add_item_image_display;
        }
        View contactView = inflater.inflate(layout, parent, false);
        // Return a new holder instance
        ImageRecyclerAdapter.ViewHolder viewHolder = new ImageRecyclerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageRecyclerAdapter.ViewHolder holder, int position) {
        String uri = images.get(position);
        ImageView imageRecyclerView = holder.imageRecyclerView;

        if (uri == null){
            imageRecyclerView.setImageResource(R.drawable.ic_launcher_foreground);
        }
        else{
            try {
                //final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                //getContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                Picasso.get().load(uri).fit().into(imageRecyclerView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


}
