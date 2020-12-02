package com.example.buybye.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Objects;


public class AccountFragment extends Fragment {
    private TextView userNameProfile;
    private TextView RegionProfile;
    private ImageView settingImage;
    private ImageView userPhoto;
    private RatingBar ratingBar;
    private User currentUser;

    private Button postButton;
    private Button cancelButton;
    private Button confirmButton;
    private CardView inputCard;
    private EditText descriptionInput;
    private EditText contactInfoInput;
    private EditText regionInput;



    public AccountFragment() {
        // Required empty public constructor
    }
    private void initializeViews(View view){
        userNameProfile = view.findViewById(R.id.userNameProfile);
        RegionProfile = view.findViewById(R.id.RegionProfile);
        settingImage = view.findViewById(R.id.settingImage);
        ratingBar = view.findViewById(R.id.ratingBar);
        userPhoto = view.findViewById(R.id.userPhoto);

        postButton = view.findViewById(R.id.fragment_account_post_button);
        cancelButton = view.findViewById(R.id.fragment_account_cancel_button);
        confirmButton = view.findViewById(R.id.fragment_account_confirm_button);
        inputCard = view.findViewById(R.id.fragment_account_input_card);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        contactInfoInput = view.findViewById(R.id.contactInfoInput);
        regionInput = view.findViewById(R.id.regionInput);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        currentUser = Objects.requireNonNull(getArguments()).getParcelable("User");
        initializeViews(view);
        inputCard.setVisibility(View.GONE);

        // set post button
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCard.setVisibility(View.VISIBLE);
                postButton.setVisibility(View.INVISIBLE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCard.setVisibility(View.GONE);
                postButton.setVisibility(View.VISIBLE);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descriptionInput.getText().toString();
                //String contactInfo = con
            }
        });
        BottomNavigationView menu = view.findViewById(R.id.profileFirstMenu);
        userPhoto.setImageResource(R.drawable.ic_launcher_foreground);
        RegionProfile.setText(String.format("%s, %s", currentUser.getUserCity(), currentUser.getUserProvince()));
        userNameProfile.setText(currentUser.getUserName());
        settingImage.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });
        menu.setOnNavigationItemSelectedListener(item -> {
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
        });


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();

    }

}