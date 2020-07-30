package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class PostItemListAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;
    private Context context;
    public PostItemListAdapter(Context context, ArrayList<Item> items){
        super(context,0,items);
        this.items = items;
        this.context = context;
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
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
        Uri uri = Uri.parse(item.getPictureArray().get(0));
        if (uri == null){
            itemImage.setImageResource(R.drawable.ic_launcher_foreground);
        }
        else{
            try {
                //final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                //getContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                itemImage.setImageBitmap(getBitmapFromUri(uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}