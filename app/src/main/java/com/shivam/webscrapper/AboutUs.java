package com.shivam.webscrapper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutUs extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        final TypeWriter tw = (TypeWriter) findViewById(R.id.typeWriter);

                tw.setText("*All your privacy rights are secured.");
                tw.setCharacterDelay(100);
                tw.animateText("*All your privacy rights are secured.");


    }
}