package com.example.buybye.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class AddSingleItemActivity extends AppCompatActivity {
    private EditText enterPickUpDate;
    private EditText enterItemName;
    private EditText enterItemDescription;
    private EditText enterItemPrice;
    private GridView gridView;
    private ArrayList<Bitmap> pictureList = new ArrayList<>();
    private ArrayList<Uri> pictureUriList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_single_item);
        enterItemName = findViewById(R.id.enterItemName);
        enterItemDescription = findViewById(R.id.enterItemDescription);
        enterItemPrice = findViewById(R.id.enterItemPrice);
        enterPickUpDate = findViewById(R.id.enterPickUpDate);
        gridView = findViewById(R.id.GridPictureView);
        // get pictures from local storage
        Button AddItemButton = findViewById(R.id.addImageButton);
        Button ConfirmButton = findViewById(R.id.confirmButton);
        AddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AddSingleItemActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddSingleItemActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                    return;

                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            //    public Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime){
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date dob_var = new Date(2000, 1, 10) ;
                try {
                    dob_var = sdf.parse(enterPickUpDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Item item = new Item(enterItemName.getText().toString(),pictureUriList,Double.parseDouble(enterItemPrice.getText().toString()),enterItemDescription.getText().toString(), dob_var);


            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            if(clipData != null){
                for(int i=0;i<clipData.getItemCount();i++){
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    try {
                        pictureUriList.add(imageUri);
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        pictureList.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }else{
                Uri imageUri = data.getData();
                try {
                    pictureUriList.add(imageUri);
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    pictureList.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(),pictureList);
            gridView.setAdapter(imageAdapter);
        }

    }
}