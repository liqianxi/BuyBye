package com.example.buybye.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.buybye.ObservableScrollView;
import com.example.buybye.R;
import com.example.buybye.activities.CatogoryDisplayAdapter;
import com.example.buybye.activities.ExplorePageWaterfallAdapter;
import com.example.buybye.activities.NewSalePostActivity;
import com.example.buybye.activities.PostItemListAdapter;
import com.example.buybye.activities.SearchActivity;
import com.example.buybye.database.ItemDatabaseAccessor;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.Catagory;
import com.example.buybye.entities.Item;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.ItemListRequestListener;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.example.buybye.listeners.fOnFocusListenable;

import java.util.ArrayList;
import java.util.Objects;


public class ExploreFragment extends Fragment implements ItemListRequestListener, UserProfileStatusListener, fOnFocusListenable {

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


        ArrayList<Catagory> categories = new ArrayList<>();
        categories.add(new Catagory("Shoes",R.drawable.shoes_small));
        categories.add(new Catagory("Clothes",R.drawable.clothes_small));
        categories.add(new Catagory("Home",R.drawable.home_small));
        categories.add(new Catagory("All Catagories",R.drawable.all_small));
        //TODO scrolling view




        // Create adapter passing in the sample user data
        CatogoryDisplayAdapter adapter = new CatogoryDisplayAdapter(categories);
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
                /*
                int Y = scrollView.getScrollY();
                Log.v("Y", String.valueOf(Y));
                Log.v("X",String.valueOf(scrollView.getScrollX()));
                if(Y>=topHeight){
                    Log.v("here","here");
                    SearchBarLinearLayout.setVisibility(View.VISIBLE);
                }else{
                    SearchBarLinearLayout.setVisibility(View.GONE);
                }*/
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

        /*

        GridView itemList = (GridView) Objects.requireNonNull(getView()).findViewById(R.id.itemList);
        PostItemListAdapter postItemListAdapter = new PostItemListAdapter(getContext(),items);
        itemList.setAdapter(postItemListAdapter);*/
        // Create adapter passing in the sample user data
        gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        adapter2 = new ExplorePageWaterfallAdapter(items);
        // Attach the adapter to the recyclerview to populate items
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



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;//状态栏高度

        int titleBarHeight =getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//标题栏高度
        topHeight = titleBarHeight + statusBarHeight;
        Log.v("height", String.valueOf(topHeight));
    }
}