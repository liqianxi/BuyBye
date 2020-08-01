package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.activities.PostsDisplayActivity;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ramotion.foldingcell.FoldingCell;


public class AccountFragment extends Fragment {
    private TextView userNameProfile;
    private TextView RegionProfile;
    private ImageView settingImage;
    private ImageView userPhoto;
    private RatingBar ratingBar;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        userNameProfile = view.findViewById(R.id.userNameProfile);
        RegionProfile = view.findViewById(R.id.RegionProfile);
        settingImage = view.findViewById(R.id.settingImage);
        ratingBar = view.findViewById(R.id.ratingBar);
        userPhoto = view.findViewById(R.id.userPhoto);
        BottomNavigationView menu = view.findViewById(R.id.profileFirstMenu);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.yourPost:
                        //switchFragment(R.layout.fragment_explore);
                        Intent intent = new Intent(getActivity(), PostsDisplayActivity.class);
                        startActivity(intent);

                        return true;
                    case R.id.ItemsOnSale:
                        //switchFragment(R.layout.fragment_wish_to_get);
                        return true;
                    case R.id.soldItemHistory:
                        //switchFragment(R.layout.fragment_account);
                        return true;
                }
                return false;
            };

        });

/*
*         bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                item -> {
                    switch (item.getItemId()) {
                        case R.id.home_page:
                            switchFragment(R.layout.fragment_explore);

                            return true;
                        case R.id.check_buy_demand:
                            switchFragment(R.layout.fragment_wish_to_get);
                            return true;
                        case R.id.my_account:
                            switchFragment(R.layout.fragment_account);
                            return true;
                    }
                    return false;
                };
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
*
*
* */




        return view;


    }
}