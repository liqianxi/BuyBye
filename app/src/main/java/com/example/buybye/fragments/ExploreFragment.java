package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.activities.AllCategoriesDisplayActivity;
import com.example.buybye.activities.CategoryDisplayAdapter;
import com.example.buybye.activities.ExplorePageWaterfallAdapter;
import com.example.buybye.activities.ItemDetailDisplayActivity;
import com.example.buybye.activities.NewSalePostActivity;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.Catagory;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ItemListRequestListener;
import com.example.buybye.listeners.UserProfileStatusListener;

import java.util.ArrayList;


public class ExploreFragment extends Fragment implements ItemListRequestListener, UserProfileStatusListener{

    private TextView test;
    private ItemDatabaseAccessor itemDatabaseAccessor;
    private UserDatabaseAccessor userDatabaseAccessor;
    private ArrayList<Item> items = new ArrayList<Item>();
    private User currentUser;
    private TextView addNewPost;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView itemsRecyclerExplorePage;
    private ExplorePageWaterfallAdapter adapter2;
    private StaggeredGridLayoutManager gridLayoutManager;
    private View view;
    private Button backToTopButton;
    private LinearLayout SearchBarLinearLayout;
    private ScrollView scrollView;
    private int topHeight = 0;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            currentUser = bundle.getParcelable("User");
        }
        userDatabaseAccessor = new UserDatabaseAccessor();
        SearchBarLinearLayout = view.findViewById(R.id.SearchBarId);
        addNewPost = view.findViewById(R.id.addPost);
        ImageView searchBar = view.findViewById(R.id.searchSaleItem);
        searchBar.setImageResource(R.drawable.search_icon);
        scrollView = view.findViewById(R.id.scrollViewId);
        backToTopButton = view.findViewById(R.id.backToTheTop);
        backToTopButton.setOnClickListener(view -> {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
            backToTopButton.setVisibility(View.GONE);
        });
        // Lookup the recyclerview in activity layout
        RecyclerView cardDisplayRecyclerView = view.findViewById(R.id.cardViewDisplayCatagory);
        cardDisplayRecyclerView.setEnabled(false);

        ArrayList<Catagory> categories = new ArrayList<>();
        categories.add(new Catagory("Shoes",R.drawable.shoes_small));
        categories.add(new Catagory("Clothes",R.drawable.clothes_small));
        categories.add(new Catagory("Home",R.drawable.home_small));
        categories.add(new Catagory("All Catagories",R.drawable.all_small));




        // Create adapter passing in the sample user data
        CategoryDisplayAdapter adapter = new CategoryDisplayAdapter(categories, new CategoryDisplayAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(position == categories.size()-1){
                    Intent intent = new Intent(getContext(), AllCategoriesDisplayActivity.class);
                    startActivity(intent);
                }
            }
        });
        // Attach the adapter to the recyclerview to populate items
        cardDisplayRecyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cardDisplayRecyclerView.setLayoutManager(horizontalLayoutManagaer);




        // Lookup the recyclerview in activity layout
        itemsRecyclerExplorePage = view.findViewById(R.id.itemsRecyclerExplorePage);







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
        itemDatabaseAccessor = new ItemDatabaseAccessor();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        itemDatabaseAccessor.getAllItems(ExploreFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (!scrollView.canScrollVertically(1)) {
                    // bottom of scroll view

                }
                if (!scrollView.canScrollVertically(-1)) {
                    // top of scroll view
                    backToTopButton.setVisibility(View.VISIBLE);
                }
                SearchBarLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onGetItemSuccess(ArrayList<Item> items) {
        if(items != null){
            this.items = items;

        }


        // Create adapter passing in the sample user data
        gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        adapter2 = new ExplorePageWaterfallAdapter(items, new ExplorePageWaterfallAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                String itemId = items.get(position).getItemId();

                Intent intent = new Intent(getActivity(), ItemDetailDisplayActivity.class);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View view, int position) {
                return true;
            }
        });

        itemsRecyclerExplorePage.setAdapter(adapter2);
        // Set layout manager to position the items
        itemsRecyclerExplorePage.setLayoutManager(gridLayoutManager);
        //itemsRecyclerExplorePage.setItemAnimator(null);
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