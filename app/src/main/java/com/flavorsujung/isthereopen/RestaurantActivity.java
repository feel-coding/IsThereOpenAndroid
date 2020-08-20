package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
    TextView phoneNumberTv;
    SwipeRefreshLayout swipeRefreshLayout;
    Button openBtn;
    Button breakBtn;
    Button closeBtn;
    SharedPreferences sharedPreferences;
    Long userSeq;
    Button toRestaurantReviewBtn;
    Button toOpenReviewBtn;
    Long restaurantSeq;
    ImageView callBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.restaurantToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
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
        phoneNumberTv = findViewById(R.id.restaurantPhoneTv);
        openBtn = findViewById(R.id.restaurantOpenBtn);
        breakBtn = findViewById(R.id.restaurantBreakBtn);
        closeBtn = findViewById(R.id.restaurantCloseBtn);
        callBtn = findViewById(R.id.restaurantCall);
        toRestaurantReviewBtn = findViewById(R.id.toRestaurantReviewBtn);
        toOpenReviewBtn = findViewById(R.id.toRestaurantOpenReviewBtn);
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
                    if(openState.equals("UNKNOWN")) {
                        openState = "등록된 오픈 정보 없음";
                        openStateTv.setText(openState);
                    }
                    else {
                        openStateTv.setText(openState);
                    }
                    addressTv.setText(restaurant.getAddress());
                    runningTimeTv.setText(restaurant.getRunningTime());
                    phoneNumberTv.setText(restaurant.getPhoneNum());
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
                            String openState = response.body().getCurrentState();
                            if(openState.equals("UNKNOWN")) {
                                openState = "등록된 오픈 정보 없음";
                                openStateTv.setText(openState);
                            }
                            else {
                                openStateTv.setText(openState);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + restaurant.getPhoneNum()));
                startActivity(intent);
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
                intent.putExtra("name", restaurant.getName());
                startActivity(intent);
            }
        });
        toOpenReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantActivity.this, OpenReviewActivity.class);
                intent.putExtra("seq", restaurantSeq);
                intent.putExtra("type", "restaurant");
                intent.putExtra("name", restaurant.getName());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}