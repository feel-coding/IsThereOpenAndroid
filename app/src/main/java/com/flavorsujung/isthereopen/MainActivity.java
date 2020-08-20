package com.flavorsujung.isthereopen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 mainViewPager;
    MainViewPagerAdapter mainViewPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(0xFFF7F7F7);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mainViewPagerAdapter = new MainViewPagerAdapter(this, 4);
        mainViewPager = findViewById(R.id.main_viewpager);
        mainViewPager.setAdapter(mainViewPagerAdapter);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {
                    case R.id.navi_home:
                        mainViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navi_restaurant:
                        mainViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navi_cafe:
                        mainViewPager.setCurrentItem(2);
                        return true;
                    case R.id.navi_bar:
                        mainViewPager.setCurrentItem(3);
                        return true;
                    default:
                        return false;
                }
            }

        });

    }
}
