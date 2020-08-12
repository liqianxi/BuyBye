package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.buybye.R;
import com.example.buybye.activities.PostsDisplayActivity;
import com.example.buybye.activities.SettingActivity;
import com.example.buybye.activities.UserSellingItemsDisplayActivity;
import com.example.buybye.activities.UserSoldOutItemsDisplayActivity;
import com.example.buybye.database.UserDatabaseAccessor;
import com.example.buybye.entities.User;
import com.example.buybye.listeners.UserProfileStatusListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ramotion.foldingcell.FoldingCell;


public class AccountFragment extends Fragment implements UserProfileStatusListener {
    private TextView userNameProfile;
    private TextView RegionProfile;
    private ImageView settingImage;
    private ImageView userPhoto;
    private RatingBar ratingBar;
    private User currentUser;
    private UserDatabaseAccessor userDatabaseAccessor;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        userDatabaseAccessor = new UserDatabaseAccessor();
        userNameProfile = view.findViewById(R.id.userNameProfile);
        RegionProfile = view.findViewById(R.id.RegionProfile);
        settingImage = view.findViewById(R.id.settingImage);
        ratingBar = view.findViewById(R.id.ratingBar);
        userPhoto = view.findViewById(R.id.userPhoto);
        BottomNavigationView menu = view.findViewById(R.id.profileFirstMenu);
        userPhoto.setImageResource(R.drawable.ic_launcher_foreground);
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.yourPost:
                        Intent intent = new Intent(getActivity(), PostsDisplayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("User",currentUser);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        return true;
                    case R.id.ItemsOnSale:
                        Intent intent2 = new Intent(getActivity(), UserSellingItemsDisplayActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.soldItemHistory:
                        Intent intent3 = new Intent(getActivity(), UserSoldOutItemsDisplayActivity.class);
                        startActivity(intent3);
                        return true;
                }
                return false;
            };

        });


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        userDatabaseAccessor.getUserProfile(this);
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
        RegionProfile.setText(String.format("%s, %s", currentUser.getUserCity(), currentUser.getUserProvince()));
        userNameProfile.setText(currentUser.getUserName());
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