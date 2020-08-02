package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.activities.CatogoryDisplayAdapter;
import com.example.buybye.activities.NewSalePostActivity;
import com.example.buybye.activities.PostItemListAdapter;
import com.example.buybye.activities.SearchActivity;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ItemListRequestListener;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.ArrayList;
import java.util.Objects;


public class ExploreFragment extends Fragment implements ItemListRequestListener, UserProfileStatusListener {

    private TextView test;
    private ItemDatabaseAccessor itemDatabaseAccessor;
    private UserDatabaseAccessor userDatabaseAccessor;
    private ArrayList<Item> items = new ArrayList<Item>();
    private User currentUser;
    private TextView addNewPost;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentUser = bundle.getParcelable("User");
        }
        userDatabaseAccessor = new UserDatabaseAccessor();
        addNewPost = view.findViewById(R.id.addPost);
        ImageView searchBar = view.findViewById(R.id.searchSaleItem);
        searchBar.setImageResource(R.drawable.search_icon);

        // Lookup the recyclerview in activity layout
        RecyclerView cardDisplayRecyclerView = view.findViewById(R.id.cardViewDisplayCatagory);

        ArrayList<String> strs = new ArrayList<>();
        strs.add("shoes");
        strs.add("socks");
        strs.add("shoes2");

        // Create adapter passing in the sample user data
        CatogoryDisplayAdapter adapter = new CatogoryDisplayAdapter(strs);
        // Attach the adapter to the recyclerview to populate items
        cardDisplayRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cardDisplayRecyclerView.setLayoutManager(horizontalLayoutManagaer);




        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewSalePostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("currentUser",currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        itemDatabaseAccessor = new ItemDatabaseAccessor();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        itemDatabaseAccessor.getAllItems(ExploreFragment.this);
    }

    @Override
    public void onGetItemSuccess(ArrayList<Item> items) {
        if(items != null){
            this.items = items;

        }
        Log.v("test", String.valueOf(items.size()));

        GridView itemList = (GridView) Objects.requireNonNull(getView()).findViewById(R.id.itemList);
        PostItemListAdapter postItemListAdapter = new PostItemListAdapter(getContext(),items);
        itemList.setAdapter(postItemListAdapter);
        userDatabaseAccessor.getUserProfile(this);
    }

    @Override
    public void onGetItemFailure() {

    }

    @Override
    public void onProfileStoreSuccess() {

    }

    @Override
    public void onProfileStoreFailure() {

    }

    @Override
    public void onProfileRetrieveSuccess(User user) {
        this.currentUser = user;
    }

    @Override
    public void onProfileRetrieveFailure() {

    }

    @Override
    public void onProfileUpdateSuccess(User user) {

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