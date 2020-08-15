package com.example.buybye.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buybye.R;

public class ChatFragment extends Fragment {
    private TextView topBar;
    private RecyclerView chatDisplayRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        topBar = view.findViewById(R.id.chatPageTopBarTitle);
        chatDisplayRecyclerView = view.findViewById(R.id.chatDisplayRecyclerView);
















        return view;

    }
}
