package com.shivam.webscrapper.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shivam.webscrapper.R;

public class Third_About extends Fragment {
    public Third_About(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_third__about, container, false);

        EditText userNameEditText;
        userNameEditText=view.findViewById(R.id.text);
        userNameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userNameEditText.requestFocusFromTouch();
                return false;
            }
        });
        return  view;

    }
}