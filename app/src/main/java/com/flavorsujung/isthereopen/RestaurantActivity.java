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

import java.util.ArrayList;
import java.util.List;

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
    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;
    TextView openStyleTv;
    TextView priceTv;
    TextView takeoutTv;
    TextView eatAloneTv;
    TextView waitingTimeTv;
    TextView cleannessTv;

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
        firstStar = findViewById(R.id.restaurantAvgStarOne);
        secondStar = findViewById(R.id.restaurantAvgStarTwo);
        thirdStar = findViewById(R.id.restaurantAvgStarThree);
        fourthStar = findViewById(R.id.restaurantAvgStarFour);
        fifthStar = findViewById(R.id.restaurantAvgStarFive);
        openStyleTv = findViewById(R.id.restaurantAvgOpenStyle);
        priceTv = findViewById(R.id.restaurantAvgPrice);
        takeoutTv = findViewById(R.id.restaurantAvgTakeout);
        eatAloneTv = findViewById(R.id.avgEatAlone);
        waitingTimeTv = findViewById(R.id.restaurantAvgWaitingTime);
        cleannessTv = findViewById(R.id.restaurantAvgCleanness);
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
                    refreshInfo();
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
                            refreshInfo();
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
    void refreshInfo() {
        serverAPI.getAvgRestaurantWaitingTime(restaurantSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> list = response.body();

                    if (list.size() == 0) {
                        waitingTimeTv.setText("정보 없음");
                    } else if (list.size() == 1) {
                        waitingTimeTv.setText(list.get(0));
                    } else if (list.size() == 2) {
                        waitingTimeTv.setText(list.get(0) + "~" + list.get(1));
                    } else {
                        waitingTimeTv.setText("의견이 많이 갈려요");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getRestaurantAvgOpenStyle(restaurantSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        openStyleTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        openStyleTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        openStyleTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else if (list.size() == 3) {
                        openStyleTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getAvgRestaurantPrice(restaurantSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        priceTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        priceTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        priceTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else if (list.size() == 3) {
                        priceTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getRestaurantAvgEatAlone(restaurantSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        eatAloneTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        eatAloneTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        eatAloneTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else if (list.size() == 3) {
                        eatAloneTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getAvgTakeOut(restaurantSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        takeoutTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        takeoutTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        takeoutTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else if (list.size() == 3) {
                        takeoutTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getAvgRestaurantCleanness(restaurantSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        cleannessTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        cleannessTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        cleannessTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else if (list.size() == 3) {
                        cleannessTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getRestaurantAvgRate(restaurantSeq).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    Double rate = response.body();
                    if (rate < 0) {
                        firstStar.setImageResource(R.drawable.ic_star_gray);
                        secondStar.setImageResource(R.drawable.ic_star_gray);
                        thirdStar.setImageResource(R.drawable.ic_star_gray);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                    }
                    else if (rate < 1.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_gray);
                        thirdStar.setImageResource(R.drawable.ic_star_gray);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);

                    }
                    else if(rate < 1.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_half_red);
                        thirdStar.setImageResource(R.drawable.ic_star_gray);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);

                    }
                    else if (rate < 2.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_gray);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);

                    }
                    else if (rate < 2.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_half_red);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                    }
                    else if (rate < 3.25) {

                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                    }
                    else if (rate < 3.75) {

                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_half_red);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                    }
                    else if (rate < 4.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                    }
                    else if (rate < 4.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_half_red);
                    }
                    else {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_red);
                    }

                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }
}