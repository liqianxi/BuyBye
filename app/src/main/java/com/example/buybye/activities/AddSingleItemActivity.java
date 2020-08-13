package com.example.buybye.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.buybye.R;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.Item;
import com.example.buybye.fragments.DatePickerFragment;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class AddSingleItemActivity extends AppCompatActivity{
    private EditText enterPickUpDate;
    private EditText enterItemName;
    private EditText enterItemDescription;
    private EditText enterItemPrice;
    private Spinner categorySpinner;
    private ArrayList<String> categories;
    private RecyclerView GridPictureView;
    private ImageRecyclerAdapter imageRecyclerAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ArrayList<String> pictureStringList = new ArrayList<>();
    private String selectedCategory;
    private String addUri = "android.resource://com.example.buybye/drawable/add_item";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        pictureStringList.add(addUri);
        setContentView(R.layout.activity_add_single_item);
        ActivityCollector.addActivity(this);
        initralizeViews();
        imageRecyclerAdapter = new ImageRecyclerAdapter(pictureStringList,2,new ImageRecyclerAdapter.RecyclerViewClickListener(){

            @Override
            public void onClick(View view, int position) {
                if(position == pictureStringList.size()-1){
                    if (ActivityCompat.checkSelfPermission(AddSingleItemActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(AddSingleItemActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                        return;

                    }
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                    intent.setType("image/*");
                    startActivityForResult(intent,1);
                }
            }

            @Override
            public boolean onLongClick(View view, int position) {
                return false;
            }
        });
        addCategoriesSpinner();
        GridPictureView.setAdapter(imageRecyclerAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        GridPictureView.setLayoutManager(staggeredGridLayoutManager);

        // get pictures from local storage

        Button ConfirmButton = findViewById(R.id.confirmItemButton);

        ConfirmButton.setOnClickListener(view -> {
           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date dob_var = new Date(2000, 1, 10) ;
            try {
                dob_var = sdf.parse(enterPickUpDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            pictureStringList.remove(addUri);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String pickUpDate = enterPickUpDate.getText().toString();

            Date date = java.util.Date.from( Instant.now() ) ;
            try {
                date = format.parse(pickUpDate);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Item item = new Item(enterItemName.getText().toString(),pictureStringList,Double.parseDouble(enterItemPrice.getText().toString()),enterItemDescription.getText().toString(), date ,selectedCategory);
            //Log.v("test",pictureUriList.get(0).toString());
            item.setSoldOut(false);
            Intent returnIntent = new Intent(AddSingleItemActivity.this, NewSalePostActivity.class);
            Bundle bundle = new Bundle();
            Log.v("sendItem", String.valueOf(item.getPrice()));
            Log.v("sendItem2", String.valueOf(item.getPictureArray().size()));
            bundle.putParcelable("item",item);
            returnIntent.putExtras(bundle);

            setResult(Activity.RESULT_OK,returnIntent);
            finish();


            //send back item




        });




    }
    public void initralizeViews(){
        enterItemName = findViewById(R.id.enterItemName);
        enterItemDescription = findViewById(R.id.enterItemDescription);
        enterItemPrice = findViewById(R.id.enterItemPrice);
        enterPickUpDate = findViewById(R.id.enterPickUpDate);
        GridPictureView = findViewById(R.id.GridPictureView);
        categorySpinner = findViewById(R.id.spinner);
    }
    public void addCategoriesSpinner(){

        categories = new ArrayList<>();
        categories.addAll(Arrays.asList(getResources().getStringArray(R.array.Categories)));
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoriesAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = categories.get(i);
                Log.v("selectedCategory",selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;
        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = 2 * space;
            int pos = parent.getChildAdapterPosition(view);
            outRect.left = space;
            outRect.right = space;
            if (pos < 2)
                outRect.top = 2 * space;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

// Check for the freshest data.

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            pictureStringList.remove(addUri);
            if(clipData != null){
                for(int i=0;i<clipData.getItemCount();i++){
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    try {
                        getApplicationContext().getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                        pictureStringList.add(imageUri.toString());

                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }else{
                Uri imageUri = data.getData();

                try {
                    getApplicationContext().getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    pictureStringList.add(imageUri.toString());

                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            pictureStringList.add(addUri);
            imageRecyclerAdapter.notifyDataSetChanged();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}