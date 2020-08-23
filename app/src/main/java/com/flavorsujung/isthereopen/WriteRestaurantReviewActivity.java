package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteRestaurantReviewActivity extends AppCompatActivity {

    ServerAPI serverAPI;
    Long restaurantSeq;
    Long userSeq;
    String restaurantName;
    String userName;
    SharedPreferences sharedPreferences;

    Button openStyleStableBtn;
    Button openStyleNormalBtn;
    Button openStyleUnstableBtn;
    Button priceCheapBtn;
    Button priceNormalBtn;
    Button priceExpensiveBtn;

    Button waitingTimeLongBtn;
    Button waitingTimeShortBtn;
    Button waitingTimeNormalBtn;
    Button cleanBtn;
    Button cleannessNormalBtn;
    Button dirtyBtn;
    Button takeoutBtn;
    Button noTakeoutBtn;
    Button eatAlonePossibleBtn;
    Button eatAloneUnableBtn;
    Button eatAloneNormalBtn;
    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;

    String rate = "";
    String waitingTime = "";
    String cleanness = "";
    String price = "";
    String openStyle = "";
    String takeout = "";
    String eatAlone = "";
    Button writeReviewBtn;
    Toolbar toolbar;
    TextView toolbarTitleTv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_restaurant_review);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.writeRestaurantReviewToolbar);
        toolbarTitleTv = findViewById(R.id.writeRestaurantReviewTitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black);
        intent = getIntent();
        restaurantSeq = intent.getLongExtra("seq", 0L);
        restaurantName = intent.getStringExtra("name");
        toolbarTitleTv.setText(restaurantName + " 리뷰 쓰기");
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        userName = sharedPreferences.getString("name", "");
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        priceCheapBtn = findViewById(R.id.restaurantPriceCheap);
        priceNormalBtn = findViewById(R.id.restaurantPriceNormal);
        priceExpensiveBtn = findViewById(R.id.restaurantPriceExpensive);
        openStyleNormalBtn = findViewById(R.id.restaurantOpenStyleNormal);
        openStyleStableBtn = findViewById(R.id.restaurantOpenStyleStable);
        openStyleUnstableBtn = findViewById(R.id.restaurantOpenStyleUnstable);
        waitingTimeLongBtn = findViewById(R.id.restaurantWaitingLong);
        waitingTimeShortBtn = findViewById(R.id.restaurantWaitingShort);
        waitingTimeNormalBtn = findViewById(R.id.restaurantWaitingNormal);
        cleanBtn = findViewById(R.id.restaurantClean);
        cleannessNormalBtn = findViewById(R.id.restaurantCleannessNormal);
        dirtyBtn = findViewById(R.id.restaurantDirty);
        takeoutBtn = findViewById(R.id.takeout);
        noTakeoutBtn = findViewById(R.id.noTakeout);
        eatAlonePossibleBtn = findViewById(R.id.eatAlonePossible);
        eatAloneNormalBtn = findViewById(R.id.eatAloneNormal);
        eatAloneUnableBtn = findViewById(R.id.eatAloneUnable);




        firstStar = findViewById(R.id.restaurantStarOne);
        secondStar = findViewById(R.id.restaurantStarTwo);
        thirdStar = findViewById(R.id.restaurantStarThree);
        fourthStar = findViewById(R.id.restaurantStarFour);
        fifthStar = findViewById(R.id.restaurantStarFive);
        writeReviewBtn = findViewById(R.id.restaurantReviewFinish);



        View.OnClickListener priceClickListener = new PriceClickListener();
        priceExpensiveBtn.setOnClickListener(priceClickListener);
        priceCheapBtn.setOnClickListener(priceClickListener);
        priceNormalBtn.setOnClickListener(priceClickListener);

        View.OnClickListener waitingTimeClickListener = new WaitingTimeClickListener();
        waitingTimeLongBtn.setOnClickListener(waitingTimeClickListener);
        waitingTimeNormalBtn.setOnClickListener(waitingTimeClickListener);
        waitingTimeShortBtn.setOnClickListener(waitingTimeClickListener);

        View.OnClickListener starClickListener = new StarClickListener();
        firstStar.setOnClickListener(starClickListener);
        secondStar.setOnClickListener(starClickListener);
        thirdStar.setOnClickListener(starClickListener);
        fourthStar.setOnClickListener(starClickListener);
        fifthStar.setOnClickListener(starClickListener);

        View.OnClickListener openStyleClickListener = new OpenStyleClickListener();
        openStyleUnstableBtn.setOnClickListener(openStyleClickListener);
        openStyleStableBtn.setOnClickListener(openStyleClickListener);
        openStyleNormalBtn.setOnClickListener(openStyleClickListener);

        View.OnClickListener eatAloneClickListener = new EatAloneClickListener();
        eatAloneUnableBtn.setOnClickListener(eatAloneClickListener);
        eatAloneNormalBtn.setOnClickListener(eatAloneClickListener);
        eatAlonePossibleBtn.setOnClickListener(eatAloneClickListener);


        View.OnClickListener takeoutClickListener = new TakeoutClickListener();
        takeoutBtn.setOnClickListener(takeoutClickListener);
        noTakeoutBtn.setOnClickListener(takeoutClickListener);

        View.OnClickListener cleannessClickListener = new CleannessClickListener();
        cleannessNormalBtn.setOnClickListener(cleannessClickListener);
        cleanBtn.setOnClickListener(cleannessClickListener);
        dirtyBtn.setOnClickListener(cleannessClickListener);

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rate.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "평점을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if (openStyle.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "오픈 스타일을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(price.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "가격대를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(takeout.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "포장 가능 여부를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(eatAlone.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "혼밥 가능 여부를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(waitingTime.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "음식 나오는 시간을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(cleanness.equals("")) {
                    Toast.makeText(WriteRestaurantReviewActivity.this, "청결도를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    serverAPI.putRestaurantInfoReview(restaurantSeq, userSeq, rate, waitingTime, cleanness, price, takeout, eatAlone, openStyle).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(WriteRestaurantReviewActivity.this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("술집리뷰 실패 원인", t.getMessage());
                        }
                    });
                }
            }
        });
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


    class OpenStyleClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.restaurantOpenStyleStable:
                    openStyleStableBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "STABLE";
                    break;
                case R.id.restaurantOpenStyleNormal:
                    openStyleNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleStableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "NORMAL";
                    break;
                case R.id.restaurantOpenStyleUnstable:
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleStableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "UNSTABLE";
                    break;
            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
    class WaitingTimeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.restaurantWaitingLong:
                    waitingTimeLongBtn.setBackgroundResource(R.drawable.full_red_round);
                    waitingTimeLongBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    waitingTimeNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTimeShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTime = "LONG";
                    break;
                case R.id.restaurantWaitingNormal:
                    waitingTimeNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    waitingTimeNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    waitingTimeLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTimeShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTime = "NORMAL";
                    break;
                case R.id.restaurantWaitingShort:
                    waitingTimeShortBtn.setBackgroundResource(R.drawable.full_red_round);
                    waitingTimeShortBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    waitingTimeNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTimeLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTime = "SHORT";
                    break;
            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
    class TakeoutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.takeout:
                    takeoutBtn.setBackgroundResource(R.drawable.full_red_round);
                    takeoutBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    noTakeoutBtn.setBackgroundResource(R.drawable.black_round_border);
                    noTakeoutBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    takeout = "POSSIBLE";
                    break;
                case R.id.noTakeout:
                    noTakeoutBtn.setBackgroundResource(R.drawable.full_red_round);
                    noTakeoutBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    takeoutBtn.setBackgroundResource(R.drawable.black_round_border);
                    takeoutBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    takeout = "UNABLE";
                    break;

            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
    class CleannessClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.restaurantClean:
                    cleanBtn.setBackgroundResource(R.drawable.full_red_round);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    cleannessNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleannessNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    dirtyBtn.setBackgroundResource(R.drawable.black_round_border);
                    dirtyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleanness = "CLEAN";
                    break;
                case R.id.restaurantCleannessNormal:
                    cleannessNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    cleannessNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    cleanBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    dirtyBtn.setBackgroundResource(R.drawable.black_round_border);
                    dirtyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleanness = "NORMAL";
                    break;
                case R.id.restaurantDirty:
                    dirtyBtn.setBackgroundResource(R.drawable.full_red_round);
                    dirtyBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    cleanBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleannessNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleannessNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleanness = "DIRTY";
                    break;
            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }


    class PriceClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.restaurantPriceCheap:
                    priceCheapBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceExpensiveBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "CHEAP";
                    break;
                case R.id.restaurantPriceNormal:
                    priceNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceCheapBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceExpensiveBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "NORMAL";
                    break;
                case R.id.restaurantPriceExpensive:
                    priceExpensiveBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceCheapBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "EXPENSIVE";
                    break;
            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }

    class EatAloneClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.eatAlonePossible:
                    eatAlonePossibleBtn.setBackgroundResource(R.drawable.full_red_round);
                    eatAlonePossibleBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    eatAloneNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    eatAloneNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    eatAloneUnableBtn.setBackgroundResource(R.drawable.black_round_border);
                    eatAloneUnableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    eatAlone = "POSSIBLE";
                    break;
                case R.id.eatAloneNormal:
                    eatAloneNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    eatAloneNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    eatAlonePossibleBtn.setBackgroundResource(R.drawable.black_round_border);
                    eatAlonePossibleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    eatAloneUnableBtn.setBackgroundResource(R.drawable.black_round_border);
                    eatAloneUnableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    eatAlone = "UNCOMFORTABLE";
                    break;
                case R.id.eatAloneUnable:
                    eatAloneUnableBtn.setBackgroundResource(R.drawable.full_red_round);
                    eatAloneUnableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    eatAloneNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    eatAloneNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    eatAlonePossibleBtn.setBackgroundResource(R.drawable.black_round_border);
                    eatAlonePossibleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    eatAlone = "UNABLE";
                    break;
            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }

    class StarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.restaurantStarOne:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_gray);
                    thirdStar.setImageResource(R.drawable.ic_star_gray);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "WORST";
                    break;
                case R.id.restaurantStarTwo:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_gray);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "BAD";
                    break;
                case R.id.restaurantStarThree:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "SOSO";
                    break;
                case R.id.restaurantStarFour:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_red);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "GOOD";
                    break;
                case R.id.restaurantStarFive:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_red);
                    fifthStar.setImageResource(R.drawable.ic_star_red);
                    rate = "BEST";
                    break;
            }
            if(!rate.equals("") && !openStyle.equals("") && !waitingTime.equals("") && !price.equals("") && !cleanness.equals("") && !takeout.equals("") && !eatAlone.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
}