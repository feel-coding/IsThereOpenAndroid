package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity implements RestaurantInfoReviewFragment.OnFragmentInteractionListener, OpenReviewFragment.OnFragmentInteractionListener {

    ImageView restaurantLogoIv;
    Intent intent;
    Restaurant restaurant;
    ServerAPI serverAPI;
    TextView restaurantTitleTv;
    Toolbar toolbar;
    TextView openStateTv;
    TextView runningTimeTv;
    TextView addressTv;
    SwipeRefreshLayout swipeRefreshLayout;
    Button openBtn;
    Button breakBtn;
    Button closeBtn;
    SharedPreferences sharedPreferences;
    Long userSeq;
    Button toRestaurantReviewBtn;
    Long restaurantSeq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
//        actionBar.setHomeAsUpIndicator(R.drawable.close_black);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        toolbar = findViewById(R.id.restaurantToolbar);
        swipeRefreshLayout = findViewById(R.id.restaurantUpdate);
        restaurantTitleTv = findViewById(R.id.restaurantTitleTv);
        restaurantLogoIv = findViewById(R.id.restaurantProfileImage);
        openStateTv = findViewById(R.id.restaurantOpenStateTv);
        runningTimeTv = findViewById(R.id.restaurantRunningTimeTv);
        addressTv = findViewById(R.id.restaurantAddressTv);
        openBtn = findViewById(R.id.restaurantOpenBtn);
        breakBtn = findViewById(R.id.restaurantBreakBtn);
        closeBtn = findViewById(R.id.restaurantCloseBtn);
        toRestaurantReviewBtn = findViewById(R.id.toRestaurantReviewBtn);
        intent = getIntent();
        restaurantSeq = intent.getLongExtra("seq", 0);
        serverAPI.getRestaurant(restaurantSeq).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.isSuccessful()) {
                    restaurant = response.body();
                    restaurantTitleTv.setText(restaurant.getName());
                    Glide.with(RestaurantActivity.this).load(restaurant.getPhotoURL()).into(restaurantLogoIv);
//                    Log.d("서버", bar.getCurrentState());
                    String openState = restaurant.getCurrentState();
                    openStateTv.setText(openState);
                    addressTv.setText(restaurant.getAddress());
                    runningTimeTv.setText(restaurant.getRunningTime());
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverAPI.getRestaurant(restaurant.getSeq()).enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        if (response.isSuccessful()) {
                            openStateTv.setText(response.body().getCurrentState());
                        }
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.putRestaurantOpenReview(restaurant.getSeq(), userSeq, "OPEN").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RestaurantActivity.this, restaurant.getName() + "의 오픈 정보가 OPEN으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            openStateTv.setText("OPEN");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.putRestaurantOpenReview(restaurant.getSeq(), userSeq, "BREAK").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RestaurantActivity.this, restaurant.getName() + "의 오픈 정보가 BREAK로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            openStateTv.setText("BREAK");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.putRestaurantOpenReview(restaurant.getSeq(), userSeq, "CLOSE").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RestaurantActivity.this, restaurant.getName() + "의 오픈 정보가 CLOSE로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            openStateTv.setText("CLOSE");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        toRestaurantReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantActivity.this, RestaurantReviewActivity.class);
                intent.putExtra("seq", restaurantSeq);
                startActivity(intent);
            }
        });

        restaurantLogoIv.setBackground(new ShapeDrawable(new OvalShape()));
        restaurantLogoIv.setClipToOutline(true);
        restaurantLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RestaurantActivity.this, ImageActivity.class);
                i.putExtra("imageURL", restaurant.getPhotoURL());
                startActivity(i);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}