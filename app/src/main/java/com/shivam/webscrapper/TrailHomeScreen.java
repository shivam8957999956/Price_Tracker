package com.shivam.webscrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.shivam.webscrapper.Fragments.Second_Schedule;
import com.shivam.webscrapper.Fragments.Third_About;
import com.shivam.webscrapper.Fragments.first_history;

public class TrailHomeScreen extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_home_screen);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_history_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_edit_calendar_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_shopping_cart_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new first_history();
                        break;
                    case 2:
                        fragment = new Second_Schedule();
                        break;
                    case 3:
                        fragment = new Third_About();
                        break;
                }
                loadFragment(fragment);
            }
        });
        bottomNavigation.setCount(1, String.valueOf(10));
        bottomNavigation.show(2, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                LayoutInflater inflater = getLayoutInflater();

                View toastLayout = inflater.inflate(R.layout.content_custom_toast, (ViewGroup) findViewById(R.id.llCustom));
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(toastLayout);
                toast.setGravity(Gravity.BOTTOM,0,150);
                toast.show();   }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(TrailHomeScreen.this, "Reselected"+item.getId(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).
                commit();


    }
}