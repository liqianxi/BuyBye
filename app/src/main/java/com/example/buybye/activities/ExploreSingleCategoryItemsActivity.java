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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
    private ArrayList<String> sortTypes;
    private ArrayAdapter<String> sortAdapter;
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
            sortTypes = new ArrayList<>();
            sortTypes.addAll(Arrays.asList(getResources().getStringArray(R.array.sortOrder)));
            sortAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sortTypes);
            sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortSpinner.setAdapter(sortAdapter);

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

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedOrder = sortTypes.get(i);
                switch (selectedOrder){
                    case "Price Ascent":
                        Collections.sort(items, new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return Double.compare(o2.getPrice(), o1.getPrice());
                            }
                        });
                        adapter.notifyDataSetChanged();

                        break;

                    case "Price Descent":

                        Collections.sort(items, new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return Double.compare(o1.getPrice(), o2.getPrice());
                            }
                        });
                        adapter.notifyDataSetChanged();

                        break;

                    case "Recently Post":
                        Collections.sort(items, new Comparator<Item>() {
                            @Override
                            public int compare(Item o1, Item o2) {
                                return o1.getPostTime().compareTo(o2.getPostTime());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void OnGetCategoryItemsFailure() {

    }
}