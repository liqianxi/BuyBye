package com.example.buybye.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.buybye.R;
import com.example.buybye.entities.Item;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AddSingleItemActivity extends AppCompatActivity {
    private EditText enterPickUpDate;
    private EditText enterItemName;
    private EditText enterItemDescription;
    private EditText enterItemPrice;
    private GridView gridView;
    private ArrayList<Bitmap> pictureList = new ArrayList<>();
    private ArrayList<Uri> pictureUriList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_add_single_item);
        enterItemName = findViewById(R.id.enterItemName);
        enterItemDescription = findViewById(R.id.enterItemDescription);
        enterItemPrice = findViewById(R.id.enterItemPrice);
        enterPickUpDate = findViewById(R.id.enterPickUpDate);
        gridView = findViewById(R.id.GridPictureView);
        // get pictures from local storage
        Button AddItemButton = findViewById(R.id.addImageButton);
        Button ConfirmButton = findViewById(R.id.confirmItemButton);
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
        //    public Item(String itemName, ArrayList<Uri> pictureArray, double price, String description, Date pickUpTime){
        ConfirmButton.setOnClickListener(view -> {
           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date dob_var = new Date(2000, 1, 10) ;
            try {
                dob_var = sdf.parse(enterPickUpDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            Log.v("test12313213","212112");
            Log.v("test",pictureUriList.get(0).toString());
            Log.v("test12313213213","212112");
            Item item = new Item(enterItemName.getText().toString(),pictureUriList,Double.parseDouble(enterItemPrice.getText().toString()),enterItemDescription.getText().toString(), java.util.Date.from( Instant.now() ) );
            //Log.v("test",pictureUriList.get(0).toString());
            Intent returnIntent = new Intent(AddSingleItemActivity.this, NewSalePostActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("item",item);
            returnIntent.putExtras(bundle);

            setResult(Activity.RESULT_OK,returnIntent);
            finish();


            //send back item




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
            Log.v("size", String.valueOf(pictureList.size()));
            gridView.setAdapter(imageAdapter);
        }

    }
}