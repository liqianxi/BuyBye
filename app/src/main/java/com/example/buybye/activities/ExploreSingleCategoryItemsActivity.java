package com.example.buybye.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.entities.Item;
import com.example.buybye.listeners.GetCategoryItemsListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ExploreSingleCategoryItemsActivity extends AppCompatActivity implements GetCategoryItemsListener {
    private TextView filterTitle;
    private Spinner sortSpinner;
    private Spinner regionSpinner;
    private TextView searchTitle;
    private ImageView backButton4;
    private RecyclerView itemsCategoryRecyclerExplorePage;
    private ArrayList<Item> items;
    private String category;
    private ExplorePageWaterfallAdapter adapter;
    private StaggeredGridLayoutManager gridLayoutManager;
    private String selectedOrder;
    private ItemDatabaseAccessor itemDatabaseAccessor = new ItemDatabaseAccessor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_single_category_items);
        bindView();
        category = getIntent().getStringExtra("Category");
        addSpinner("sort");

    }

    @Override
    protected void onStart() {
        super.onStart();
        itemDatabaseAccessor.getCategoryItems(category,this);
    }
    private void addSpinner(String type){
        if(type.equals("sort")){
            ArrayList<String> sortTypes = new ArrayList<>();
            sortTypes.addAll(Arrays.asList(getResources().getStringArray(R.array.sortOrder)));
            ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sortTypes);
            sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortSpinner.setAdapter(sortAdapter);
            sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedOrder = sortTypes.get(i);
                    switch (selectedOrder){
                        case "Price Ascent":

                            break;

                        case "Price Descent":

                            break;

                        case "Recently Post":

                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{

        }

    }
    private void bindView(){
        filterTitle = findViewById(R.id.filterTitle);
        sortSpinner = findViewById(R.id.sortSpinner);
        regionSpinner = findViewById(R.id.regionSpinner);
        searchTitle = findViewById(R.id.searchTitle);
        backButton4 = findViewById(R.id.backButton4);
        itemsCategoryRecyclerExplorePage = findViewById(R.id.itemsCategoryRecyclerExplorePage);

    }

    @Override
    public void OnGetCategoryItemsSuccess(ArrayList<Item> items) {
        this.items = items;

        gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        adapter = new ExplorePageWaterfallAdapter(items, new ExplorePageWaterfallAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String itemId = items.get(position).getItemId();
                Intent intent = new Intent(getApplicationContext(), ItemDetailDisplayActivity.class);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View view, int position) {
                return false;
            }
        });
        itemsCategoryRecyclerExplorePage.setAdapter(adapter);
        // Set layout manager to position the items
        itemsCategoryRecyclerExplorePage.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void OnGetCategoryItemsFailure() {

    }
}