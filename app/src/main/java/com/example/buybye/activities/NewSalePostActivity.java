package com.example.buybye.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.buybye.R;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.StorageAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.ActivityCollector;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.entities.sellerPost;
import com.example.buybye.listeners.ItemAddDeleteListener;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;

public class NewSalePostActivity extends AppCompatActivity implements ItemAddDeleteListener, UserProfileStatusListener{
    private ArrayList<Item> Items = new ArrayList<>();
    private RecyclerView itemsList;

    private ItemDatabaseAccessor itemDatabaseAccessor = new ItemDatabaseAccessor();
    private UserDatabaseAccessor userDatabaseAccessor = new UserDatabaseAccessor();
    private User CurrentUser;
    private ArrayList<sellerPost> currentPosts;
    private EditText postName;
    private EditText postDesc;
    private EditText postPhoneNum;
    private StorageAccessor storageAccessor = new StorageAccessor();
    private Button newItemButton;
    private Button postButton;
    private ImageView backButton;
    private ExplorePageWaterfallAdapter explorePageWaterfallAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_new_sale_post);
        backButton = findViewById(R.id.backButton);
        postName = findViewById(R.id.PostName);
        postDesc = findViewById(R.id.PostDescription);
        postPhoneNum = findViewById(R.id.PostPhoneNumber);
        newItemButton = findViewById(R.id.addItemButton);
        postButton = findViewById(R.id.postOutButton);
        itemsList = findViewById(R.id.itemListView);
        registerForContextMenu(itemsList);
        backButton.setImageResource(R.drawable.back_icon);
        backButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(NewSalePostActivity.this);
            builder.setMessage("Back to the Explore Page?\nYour Post will not be saved").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Toast.makeText(NewSalePostActivity.this,"Back to the Explore Page",Toast.LENGTH_SHORT).show();
                    ActivityCollector.removeActivity(NewSalePostActivity.this);
                    Intent intent = new Intent(NewSalePostActivity.this,HomePageActivity.class);
                    startActivity(intent);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert11 = builder.create();
            alert11.show();

        });
        explorePageWaterfallAdapter = new ExplorePageWaterfallAdapter(Items, new ExplorePageWaterfallAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public boolean onLongClick(View view, int position) {
                itemsList.showContextMenu();
                return true;
            }
        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        itemsList.setAdapter(explorePageWaterfallAdapter);
        itemsList.setLayoutManager(staggeredGridLayoutManager);
        userDatabaseAccessor.getUserProfile(this);
        ActivityCollector.addActivity(this);



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 1, "DELETE");
        menu.add(0, 2, 2,"EDIT");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        switch (item.getItemId()) {

            case 1:
                //delete

                return true;

            case 2:
                //edit

                return true;

            default:
                return super.onContextItemSelected(item);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Item item = null;
            if (data != null) {
                item = data.getParcelableExtra("item");
            }
            Items.add(item);
            explorePageWaterfallAdapter.notifyDataSetChanged();


        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemsAddedSuccess() {
        //all items has been added
        currentPosts = CurrentUser.getSellerPostArray();
        if (currentPosts == null){
            currentPosts = new ArrayList<>();
        }
        sellerPost sellerPostInstance = new sellerPost();
        String postNameString = postName.getText().toString();
        String postDescriptionString = postDesc.getText().toString();
        String phoneNum = postPhoneNum.getText().toString();
        sellerPostInstance.setPhoneNumber(phoneNum);
        sellerPostInstance.setDescriptions(postDescriptionString);
        sellerPostInstance.setPostName(postNameString);
        sellerPostInstance.setItemList(Items);
        sellerPostInstance.setPostDate(java.util.Date.from( Instant.now() ));
        currentPosts.add(sellerPostInstance);

        //CurrentUser.setSellerPostArray(currentPosts);
        HashMap<String,Object> updateHash = new HashMap<>();
        updateHash.put("sellerPostArray",currentPosts);
        userDatabaseAccessor.updateUserProfile(CurrentUser,updateHash,NewSalePostActivity.this );
    }
    public ArrayList<String> ArrayToString(ArrayList<Uri> uris){
        ArrayList<String> stringUris = new ArrayList<>();
        for(int i=0;i<uris.size();i++){
            stringUris.add(uris.get(i).toString());
        }
        return stringUris;
    }

    @Override
    public void onItemsAddedFailure() {
        Log.v("test123da213","failed");
    }

    @Override
    public void onItemDeleteSuccess() {

    }

    @Override
    public void onItemDeleteFailure() {

    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.CurrentUser = user;
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSalePostActivity.this,AddSingleItemActivity.class);
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent,100);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save post data to the database
                ArrayList<Integer> allUriSize = new ArrayList<>();
                ArrayList<Uri> allUriSingle = new ArrayList<>();
                ArrayList<ArrayList<Uri>> resultUri = new ArrayList<>();
                for(int i=0;i<Items.size();i++){
                    ArrayList<Uri> singleItemUriList = new ArrayList<>();
                    for(int j=0;j<Items.get(i).getPictureArray().size();j++){
                        allUriSingle.add(Uri.parse(Items.get(i).getPictureArray().get(j)));
                        singleItemUriList.add(Uri.parse(Items.get(i).getPictureArray().get(j)));

                    }
                    allUriSize.add(singleItemUriList.size());

                }

                ArrayList<Uri> resultList = new ArrayList<>();

                storageAccessor.getImagesUri(allUriSingle,0,resultList);
                storageAccessor.setListener(new StorageAccessor.CustomListener(){
                    @Override
                    public void onSingleReady(ArrayList<Uri> uris, int curIndex) {
                        int curIndexTemp = curIndex +1;
                        storageAccessor.getImagesUri(allUriSingle,curIndexTemp,uris);

                    }

                    @Override
                    public void onAllReady(ArrayList<Uri> uris) {
                        ArrayList<Uri> tempList0 = uris;

                        for(int i=0;i<allUriSize.size();i++){
                            ArrayList<Uri> tempList = new ArrayList<>();
                            for(int j=0;j<allUriSize.get(i);j++){
                                tempList.add(uris.get(0));
                                tempList0.remove(0);
                            }
                            resultUri.add(tempList);
                        }
                        ArrayList<Item> tempList = new ArrayList<>();
                        for(int i=0;i<Items.size();i++){
                            Item item = Items.get(i);
                            ArrayList<Uri> tempList2 = resultUri.get(i);
                            item.setPictureArray(ArrayToString(tempList2));
                            item.setOwner(CurrentUser.getEmail());
                            tempList.add(item);
                        }
                        itemDatabaseAccessor.addItem(tempList,NewSalePostActivity.this);

                    }

                });


            }
        });
    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {
        CurrentUser.setSellerPostArray(currentPosts);
        Intent intent = new Intent(NewSalePostActivity.this, NotifyPostSuccessActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProfileUpdateFailure() {

    }

    @Override
    public void onValidateSuccess() {

    }

    @Override
    public void onValidateFailure() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFailure() {

    }


}