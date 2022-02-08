 package com.shivam.webscrapper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

 public class SettingsActivity extends AppCompatActivity {

    Button button;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        button=findViewById(R.id.history_button);
        sharedPreferences=getSharedPreferences("setting",Context.MODE_PRIVATE);
        String setting=sharedPreferences.getString("setting","");
//        Toast.makeText(this, setting, Toast.LENGTH_SHORT).show();
        if(setting.isEmpty() || setting.equals("allowed")){
            button.setText("Allowed");

        }
        else{
            button.setText("Disabled");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                String setting1=sharedPreferences.getString("setting","");
                if(setting1.isEmpty() || setting1.equals("allowed")){
                    button.setText("disabled");
                    editor.putString("setting","disabled");
                }else {
                    button.setText("allowed");
                    editor.putString("setting","allowed");
                }
                editor.commit();
            }
        });
    }
}