package com.flavorsujung.isthereopen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 mainViewPager;
    MainViewPagerAdapter mainViewPagerAdapter;
    long pressedTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent i = new Intent(this, TestActivity.class);
//        startActivity(i);
        getWindow().setStatusBarColor(0xFFF7F7F7);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mainViewPagerAdapter = new MainViewPagerAdapter(this, 4);
        mainViewPager = findViewById(R.id.main_viewpager);
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainViewPager.setOffscreenPageLimit(4);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
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

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - pressedTime < 20000) {
            finishAffinity();
            return;
        }
        Toast.makeText(this, "종료하시려면 한 번 더 눌러주세요.", Toast.LENGTH_SHORT).show();
        pressedTime = System.currentTimeMillis();
    }

}
