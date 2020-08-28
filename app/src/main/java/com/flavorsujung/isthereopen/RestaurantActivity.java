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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity implements RestaurantInfoReviewFragment.OnFragmentInteractionListener, OpenReviewFragment.OnFragmentInteractionListener {

    RecyclerView recyclerView;
    RestaurantReviewAdapter adapter;
    RestaurantViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    TabLayout tabLayout;
    private String[] tabTitles= {"가게리뷰", "오픈리뷰"};

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
//    Button toRestaurantReviewBtn;
//    Button toOpenReviewBtn;
    Long restaurantSeq;
    ImageView callBtn;
    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;
    TextView rateTv;
    TextView openStyleTv;
    TextView priceTv;
    TextView takeoutTv;
    TextView eatAloneTv;
    TextView waitingTimeTv;
    TextView cleannessTv;

    ChangeOpenStateDialog dialog;
    View.OnClickListener openListener;
    View.OnClickListener breakListener;
    View.OnClickListener closeListener;
    View.OnClickListener cancelListener;

    ImageView heartButton;
    boolean isPatron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        viewPagerAdapter = new RestaurantViewPagerAdapter(this, 2);
        viewPager = findViewById(R.id.restaurantViewPager);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.restaurantReviewTab);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {tab.setText(tabTitles[position]);
                    viewPager.setCurrentItem(tab.getPosition(), true);}).attach();
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
        rateTv = findViewById(R.id.restaurantAvgRate);
        openStyleTv = findViewById(R.id.restaurantAvgOpenStyle);
        priceTv = findViewById(R.id.restaurantAvgPrice);
        takeoutTv = findViewById(R.id.restaurantAvgTakeout);
        eatAloneTv = findViewById(R.id.avgEatAlone);
        waitingTimeTv = findViewById(R.id.restaurantAvgWaitingTime);
        cleannessTv = findViewById(R.id.restaurantAvgCleanness);
        heartButton = findViewById(R.id.restaurantAddPatron);
        intent = getIntent();
        restaurantSeq = intent.getLongExtra("seq", 0);
        cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        };
        refreshInfo();

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
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.getPatronRestaurantList(userSeq).enqueue(new Callback<List<PatronRestaurant>>() {
                    @Override
                    public void onResponse(Call<List<PatronRestaurant>> call, Response<List<PatronRestaurant>> response) {
                        boolean isAlreadyPatron = false;
                        for(PatronRestaurant patronRestaurant : response.body()) {
                            if(patronRestaurant.getRestaurantSeq().equals(restaurantSeq)) {
                                isAlreadyPatron = true;
                                break;
                            }
                        }
                        if(isAlreadyPatron) {
                            serverAPI.deletePatronRestaurant(userSeq, restaurantSeq).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()) {
                                        heartButton.setImageResource(R.drawable.ic_heart);
                                        Toast.makeText(RestaurantActivity.this, "단골 가게에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(RestaurantActivity.this, "네트워크 연결상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            serverAPI.putPatronRestaurant(userSeq, restaurantSeq).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()) {
                                        heartButton.setImageResource(R.drawable.ic_heart_red);
                                        Toast.makeText(RestaurantActivity.this, "단골 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PatronRestaurant>> call, Throwable t) {

                    }
                });
            }
        });
        openListener = v -> changeState("OPEN");
        breakListener = v -> changeState("BREAK");
        closeListener = v-> changeState("CLOSE");
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(RestaurantActivity.this, openListener, cancelListener, restaurant.getName() + "의 상태를 OPEN으로 변경하시겠습니까?");
                dialog.show();
            }
        });
        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(RestaurantActivity.this, breakListener, cancelListener, restaurant.getName() + "의 상태를  BREAK로 변경하시겠습니까?");
                dialog.show();

            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(RestaurantActivity.this, closeListener, cancelListener, restaurant.getName() + "의 상태를 CLOSE로 변경하시겠습니까?");
                dialog.show();
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
        serverAPI.getRestaurant(restaurantSeq).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.isSuccessful()) {
                    restaurant = response.body();
                    restaurantTitleTv.setText(restaurant.getName());
                    Glide.with(RestaurantActivity.this).load(restaurant.getPhotoURL()).into(restaurantLogoIv);
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
                    if(restaurant.getAvgOpenStyle() == null || restaurant.getAvgOpenStyle().equals("UNKNOWN"))
                        openStyleTv.setText("정보 없음");
                    else
                        openStyleTv.setText(restaurant.getAvgOpenStyle());
                    if(restaurant.getAvgPrice() == null)
                        priceTv.setText("정보 없음");
                    else
                        priceTv.setText(restaurant.getAvgPrice());
                    if(restaurant.getAvgOpenStyle() == null)
                        waitingTimeTv.setText("정보 없음");
                    else
                        waitingTimeTv.setText(restaurant.getAvgWaitingTime());
                    if(restaurant.getAvgCleanness() == null)
                        cleannessTv.setText("정보 없음");
                    else
                        cleannessTv.setText(restaurant.getAvgCleanness());
                    if(restaurant.getAvgEatAlone() == null)
                        eatAloneTv.setText("정보 없음");
                    else
                        eatAloneTv.setText(restaurant.getAvgEatAlone());
                    if(restaurant.getAvgTakeOut() == null)
                        takeoutTv.setText("정보 없음");
                    else
                        takeoutTv.setText(restaurant.getAvgTakeOut());
                    serverAPI.getPatronRestaurantList(userSeq).enqueue(new Callback<List<PatronRestaurant>>() {
                        @Override
                        public void onResponse(Call<List<PatronRestaurant>> call, Response<List<PatronRestaurant>> response) {
                            if(response.isSuccessful()) {
                                List<PatronRestaurant> patronRestaurantList = response.body();
                                for(PatronRestaurant patronRestaurant : patronRestaurantList) {
                                    if(patronRestaurant.getRestaurantSeq().equals(restaurantSeq)) {
                                        isPatron = true;
                                        break;
                                    }
                                }
                                if(isPatron)
                                    heartButton.setImageResource(R.drawable.ic_heart_red);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PatronRestaurant>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });

        serverAPI.getRestaurantAvgRate(restaurantSeq).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    Double rate = response.body();
                    rateTv.setText(String.format("%.1f", rate));
                    if (rate <= 0.1) {
                        firstStar.setImageResource(R.drawable.ic_star_gray);
                        secondStar.setImageResource(R.drawable.ic_star_gray);
                        thirdStar.setImageResource(R.drawable.ic_star_gray);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                        rateTv.setText("");
                    }
                    else if (rate < 1.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_border_red);
                        thirdStar.setImageResource(R.drawable.ic_star_border_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);

                    }
                    else if(rate < 1.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_half_red);
                        thirdStar.setImageResource(R.drawable.ic_star_border_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);

                    }
                    else if (rate < 2.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_border_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);

                    }
                    else if (rate < 2.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_half_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 3.25) {

                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 3.75) {

                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_half_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 4.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
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
    void changeState(String openState) {
        RestaurantOpenReview restaurantOpenReview = new RestaurantOpenReview();
        restaurantOpenReview.setRestaurantSeq(restaurant.getSeq());
        restaurantOpenReview.setUserSeq(userSeq);
        restaurantOpenReview.setOpenState(openState);
        serverAPI.putRestaurantOpenReview(restaurantOpenReview/*restaurant.getSeq(), userSeq, openState*/).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if(openState.equals("OPEN")) {
                        Toast.makeText(RestaurantActivity.this, restaurant.getName() + "의 오픈 정보가 " + openState + "으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(RestaurantActivity.this, restaurant.getName() + "의 오픈 정보가 " + openState + "로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    openStateTv.setText(openState);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}