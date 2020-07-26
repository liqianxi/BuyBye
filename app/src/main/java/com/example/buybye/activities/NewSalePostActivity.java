package com.example.buybye.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class NewSalePostActivity extends AppCompatActivity {
    private ArrayList<Item> Items = new ArrayList<>();
    private ArrayList<Uri> allPictureUriList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale_post);
        EditText postName = findViewById(R.id.PostName);
        EditText postDesc = findViewById(R.id.PostDescription);
        EditText postPhoneNum = findViewById(R.id.PostPhoneNumber);
        Button newItemButton = findViewById(R.id.addItemButton);
        GridView itemsGrid = findViewById(R.id.itemsImageGrid);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSalePostActivity.this,AddSingleItemActivity.class);
                startActivityForResult(intent,100);
            }
        });
        ArrayList<Bitmap> tempList = new ArrayList<>();
        for(int i=0;i<allPictureUriList.size();i++){

            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(allPictureUriList.get(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            tempList.add(bitmap);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Item item = (Item)getIntent().getSerializableExtra("item");
            Items.add(item);
            if(item.getPictureArray() !=null){
                allPictureUriList.add(item.getPictureArray().get(0));
            }
        }



    }
}