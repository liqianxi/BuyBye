package com.example.buybye.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buybye.entities.Item;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<Bitmap> {
    private ArrayList<Bitmap> images;
    private Context context;
    public ImageAdapter(Context context, ArrayList<Bitmap> images){
        super(context,0,images);
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(images.get(position));


        return imageView;
    }
}
