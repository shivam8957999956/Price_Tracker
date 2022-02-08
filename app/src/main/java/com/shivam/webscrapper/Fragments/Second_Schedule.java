package com.shivam.webscrapper.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.shivam.webscrapper.MainActivity;
import com.shivam.webscrapper.R;
import com.shivam.webscrapper.TopDealsHelperClass;
import com.shivam.webscrapper.topDealAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class Second_Schedule extends Fragment {

    public Second_Schedule() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Adding Task to the List (Async)
//        leading2(getContext());
    }

    private void leading2(Context context) {

//        LoadingUrl = "https://www.amazon.in/";
        String LoadingUrl = "https://www.amazon.in/";
        new Thread(new Runnable() {
            private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36";

            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(LoadingUrl).
                            userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
                            .cookie("auth", "token")
                            .timeout(10000).get();

                } catch (IOException e) {
//                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                    }
                });
            }
        }).start();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second__schedule, container, false);
        EditText editText = view.findViewById(R.id.url);
//        leading2(getContext());
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editText.requestFocusFromTouch();
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                return false;
            }
        });


        String LoadingUrl = "https://www.amazon.in/";
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    Document doc = Jsoup.connect(LoadingUrl).
                            userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
                            .cookie("auth", "token")
                            .timeout(10000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
        return view;
    }


}
