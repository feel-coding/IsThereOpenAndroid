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

public class WriteCafeReviewActivity extends AppCompatActivity {

    ServerAPI serverAPI;
    Long cafeSeq;
    Long userSeq;
    String cafeName;
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
    Button plugNumNormalBtn;
    Button plugNumManyBtn;
    Button plugNumLittleBtn;
    Button plugNumNoBtn;
    Button tableHighBtn;
    Button tableNormalBtn;
    Button tableLowBtn;
    Button tableNoBtn;
    Button stayLongBtn;
    Button stayNormalBtn;
    Button stayShortBtn;
    Button stayNoBtn;
    Button lightBtn;
    Button lightNormalBtn;
    Button lightDarkBtn;
    Button customerManyBtn;
    Button customerNormalBtn;
    Button customerLittleBtn;
    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;

    String rate = "";
    String customerNum = "";
    String plugNum = "";
    String waitingTime = "";
    String stayLong = "";
    String tableHeight = "";
    String lightness = "";
    String price = "";
    String openStyle = "";

    Button writeReviewBtn;
    Toolbar toolbar;
    TextView toolbarTitleTv;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_cafe_review);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.writeCafeReviewToolbar);
        toolbarTitleTv = findViewById(R.id.writeCafeReviewTitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
        cafeName = intent.getStringExtra("name");
        toolbarTitleTv.setText(cafeName + " 리뷰 쓰기");
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userName = sharedPreferences.getString("name", "");
        if(userName.equals("")) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        serverAPI.getUserSeq(userName).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(response.isSuccessful()) {
                    userSeq = response.body();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
        openStyleNormalBtn = findViewById(R.id.cafeOpenStyleNormal);
        openStyleStableBtn = findViewById(R.id.cafeOpenStyleStable);
        openStyleUnstableBtn = findViewById(R.id.cafeOpenStyleUnstable);
        priceCheapBtn = findViewById(R.id.cafePriceCheap);
        priceNormalBtn = findViewById(R.id.cafePriceNormal);
        priceExpensiveBtn = findViewById(R.id.cafePriceExpensive);
        waitingTimeLongBtn = findViewById(R.id.cafeWaitingLong);
        waitingTimeShortBtn = findViewById(R.id.cafeWaitingShort);
        waitingTimeNormalBtn = findViewById(R.id.cafeWaitingNormal);
        plugNumLittleBtn = findViewById(R.id.cafePlugLittle);
        plugNumManyBtn = findViewById(R.id.cafePlugMany);
        plugNumNormalBtn = findViewById(R.id.cafePlugNormal);
        plugNumNoBtn = findViewById(R.id.cafePlugNo);
        tableHighBtn = findViewById(R.id.cafeTableHigh);
        tableLowBtn = findViewById(R.id.cafeTableLow);
        tableNormalBtn = findViewById(R.id.cafeTableNormal);
        tableNoBtn = findViewById(R.id.cafeTableNo);
        stayLongBtn = findViewById(R.id.cafeStayLong);
        stayNormalBtn = findViewById(R.id.cafeStayNormal);
        stayShortBtn = findViewById(R.id.cafeStayShort);
        stayNoBtn = findViewById(R.id.cafeStayNo);
        lightNormalBtn = findViewById(R.id.cafeLightnessNormal);
        lightBtn = findViewById(R.id.cafeLightnessLight);
        lightDarkBtn = findViewById(R.id.cafeLightnessDark);
        customerLittleBtn = findViewById(R.id.cafeCustomerLittle);
        customerManyBtn = findViewById(R.id.cafeCustomerMany);
        customerNormalBtn = findViewById(R.id.cafeCustomerNormal);
        firstStar = findViewById(R.id.cafeStarOne);
        secondStar = findViewById(R.id.cafeStarTwo);
        thirdStar = findViewById(R.id.cafeStarThree);
        fourthStar = findViewById(R.id.cafeStarFour);
        fifthStar = findViewById(R.id.cafeStarFive);
        writeReviewBtn = findViewById(R.id.cafeReviewFinish);


        View.OnClickListener openStyleClickListener = new OpenStyleClickListener();
        openStyleUnstableBtn.setOnClickListener(openStyleClickListener);
        openStyleStableBtn.setOnClickListener(openStyleClickListener);
        openStyleNormalBtn.setOnClickListener(openStyleClickListener);

        View.OnClickListener tableHeightClickListener = new TableHeightClickListener();
        tableNoBtn.setOnClickListener(tableHeightClickListener);
        tableNormalBtn.setOnClickListener(tableHeightClickListener);
        tableHighBtn.setOnClickListener(tableHeightClickListener);
        tableLowBtn.setOnClickListener(tableHeightClickListener);

        View.OnClickListener plugNumClickListener = new PlugNumClickListener();
        plugNumNoBtn.setOnClickListener(plugNumClickListener);
        plugNumNormalBtn.setOnClickListener(plugNumClickListener);
        plugNumManyBtn.setOnClickListener(plugNumClickListener);
        plugNumLittleBtn.setOnClickListener(plugNumClickListener);

        View.OnClickListener waitingTimeClickListener = new WaitingTimeClickListener();
        waitingTimeLongBtn.setOnClickListener(waitingTimeClickListener);
        waitingTimeNormalBtn.setOnClickListener(waitingTimeClickListener);
        waitingTimeShortBtn.setOnClickListener(waitingTimeClickListener);

        View.OnClickListener stayLongClickListener = new StayLongClickListener();
        stayLongBtn.setOnClickListener(stayLongClickListener);
        stayShortBtn.setOnClickListener(stayLongClickListener);
        stayNoBtn.setOnClickListener(stayLongClickListener);
        stayNormalBtn.setOnClickListener(stayLongClickListener);

        View.OnClickListener lightnessClickListener = new LightnessClickListener();
        lightBtn.setOnClickListener(lightnessClickListener);
        lightDarkBtn.setOnClickListener(lightnessClickListener);
        lightNormalBtn.setOnClickListener(lightnessClickListener);

        View.OnClickListener priceClickListener = new PriceClickListener();
        priceExpensiveBtn.setOnClickListener(priceClickListener);
        priceCheapBtn.setOnClickListener(priceClickListener);
        priceNormalBtn.setOnClickListener(priceClickListener);

        View.OnClickListener customerClickListener = new CustomerClickListener();
        customerNormalBtn.setOnClickListener(customerClickListener);
        customerManyBtn.setOnClickListener(customerClickListener);
        customerLittleBtn.setOnClickListener(customerClickListener);

        View.OnClickListener starClickListener = new StarClickListener();
        firstStar.setOnClickListener(starClickListener);
        secondStar.setOnClickListener(starClickListener);
        thirdStar.setOnClickListener(starClickListener);
        fourthStar.setOnClickListener(starClickListener);
        fifthStar.setOnClickListener(starClickListener);

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rate.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "평점을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if (openStyle.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "오픈 스타일을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(waitingTime.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "음료 제조 시간을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(price.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "가격대를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(customerNum.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "손님 수를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(stayLong.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "오래 있어도 되는지 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(plugNum.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "콘센트 수를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(tableHeight.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "테이블 높이를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(lightness.equals("")) {
                    Toast.makeText(WriteCafeReviewActivity.this, "조명을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    serverAPI.putCafeInfoReview(cafeSeq, userSeq, openStyle, waitingTime, price, customerNum, plugNum, rate, tableHeight, lightness, stayLong).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(WriteCafeReviewActivity.this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("카페리뷰 실패 원인", t.getMessage());
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
                case R.id.cafeOpenStyleStable:
                    openStyleStableBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "STABLE";
                    break;
                case R.id.cafeOpenStyleNormal:
                    openStyleNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleStableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "NORMAL";
                    break;
                case R.id.cafeOpenStyleUnstable:
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleStableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "UNSTABLE";
                    break;
            }
        }
    }
    class TableHeightClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafeTableHigh:
                    tableHighBtn.setBackgroundResource(R.drawable.full_red_round);
                    tableHighBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    tableLowBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableLowBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHeight = "HIGH";
                    break;
                case R.id.cafeTableNormal:
                    tableNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    tableNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    tableLowBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableLowBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHighBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableHighBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHeight = "NORMAL";
                    break;
                case R.id.cafeTableLow:
                    tableLowBtn.setBackgroundResource(R.drawable.full_red_round);
                    tableLowBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    tableNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHighBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableHighBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHeight = "LOW";
                    break;
                case R.id.cafeTableNo:
                    tableNoBtn.setBackgroundResource(R.drawable.full_red_round);
                    tableNoBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    tableNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHighBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableHighBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableLowBtn.setBackgroundResource(R.drawable.black_round_border);
                    tableLowBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    tableHeight = "NOTABLE";

            }
        }
    }
    class PlugNumClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafePlugLittle:
                    plugNumLittleBtn.setBackgroundResource(R.drawable.full_red_round);
                    plugNumLittleBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    plugNumManyBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumManyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNum = "LITTLE";
                    break;
                case R.id.cafePlugMany:
                    plugNumManyBtn.setBackgroundResource(R.drawable.full_red_round);
                    plugNumManyBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    plugNumNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumLittleBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumLittleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNum = "MANY";
                    break;
                case R.id.cafePlugNormal:
                    plugNumNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    plugNumNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    plugNumNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumLittleBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumLittleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumManyBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumManyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNum = "NORMAL";
                    break;
                case R.id.cafePlugNo:
                    plugNumNoBtn.setBackgroundResource(R.drawable.full_red_round);
                    plugNumNoBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    plugNumManyBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumManyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumLittleBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumLittleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNumNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    plugNumNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    plugNum = "NOTABLE";
                    break;
            }
        }
    }
    class WaitingTimeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafeWaitingLong:
                    waitingTimeLongBtn.setBackgroundResource(R.drawable.full_red_round);
                    waitingTimeLongBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    waitingTimeNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTimeShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTime = "LONG";
                    break;
                case R.id.cafeWaitingNormal:
                    waitingTimeNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    waitingTimeNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    waitingTimeLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTimeShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTime = "NORMAL";
                    break;
                case R.id.cafeWaitingShort:
                    waitingTimeShortBtn.setBackgroundResource(R.drawable.full_red_round);
                    waitingTimeShortBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    waitingTimeNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTimeLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    waitingTimeLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    waitingTime = "SHORT";
                    break;
            }
        }
    }
    class StayLongClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafeStayLong:
                    stayLongBtn.setBackgroundResource(R.drawable.full_red_round);
                    stayLongBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    stayNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayLong = "POSSIBLE";
                    break;
                case R.id.cafeStayNormal:
                    stayNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    stayNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    stayLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayLong = "NORMAL";
                    break;
                case R.id.cafeStayShort:
                    stayShortBtn.setBackgroundResource(R.drawable.full_red_round);
                    stayShortBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    stayLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayNoBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayNoBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayLong = "UNCOMFORTABLE";
                    break;
                case R.id.cafeStayNo:
                    stayNoBtn.setBackgroundResource(R.drawable.full_red_round);
                    stayNoBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    stayLongBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayShortBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayShortBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    stayNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    stayLong = "TAKEOUT";
                    break;
            }
        }
    }
    class LightnessClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafeLightnessLight:
                    lightBtn.setBackgroundResource(R.drawable.full_red_round);
                    lightBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    lightDarkBtn.setBackgroundResource(R.drawable.black_round_border);
                    lightDarkBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    lightNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    lightNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    lightness = "LIGHT";
                    break;
                case R.id.cafeLightnessDark:
                    lightDarkBtn.setBackgroundResource(R.drawable.full_red_round);
                    lightDarkBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    lightBtn.setBackgroundResource(R.drawable.black_round_border);
                    lightBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    lightNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    lightNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    lightness = "DARK";
                    break;
                case R.id.cafeLightnessNormal:
                    lightNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    lightNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    lightDarkBtn.setBackgroundResource(R.drawable.black_round_border);
                    lightDarkBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    lightBtn.setBackgroundResource(R.drawable.black_round_border);
                    lightBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    lightness = "NORMAL";
                    break;
            }
        }
    }

    class PriceClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafePriceCheap:
                    priceCheapBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceExpensiveBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "CHEAP";
                    break;
                case R.id.cafePriceNormal:
                    priceNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceCheapBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceExpensiveBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "NORMAL";
                    break;
                case R.id.cafePriceExpensive:
                    priceExpensiveBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceCheapBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "EXPENSIVE";
                    break;
            }
        }
    }

    class CustomerClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafeCustomerLittle:
                    customerLittleBtn.setBackgroundResource(R.drawable.full_red_round);
                    customerLittleBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    customerManyBtn.setBackgroundResource(R.drawable.black_round_border);
                    customerManyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    customerNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    customerNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    customerNum = "UNCROWDED";
                    break;
                case R.id.cafeCustomerMany:
                    customerManyBtn.setBackgroundResource(R.drawable.full_red_round);
                    customerManyBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    customerLittleBtn.setBackgroundResource(R.drawable.black_round_border);
                    customerLittleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    customerNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    customerNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    customerNum = "CROWDED";
                    break;
                case R.id.cafeCustomerNormal:
                    customerNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    customerNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    customerLittleBtn.setBackgroundResource(R.drawable.black_round_border);
                    customerLittleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    customerManyBtn.setBackgroundResource(R.drawable.black_round_border);
                    customerManyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    customerNum = "NORMAL";
                    break;
            }
        }
    }

    class StarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cafeStarOne:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_gray);
                    thirdStar.setImageResource(R.drawable.ic_star_gray);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "WORST";
                    break;
                case R.id.cafeStarTwo:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_gray);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "BAD";
                    break;
                case R.id.cafeStarThree:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "SOSO";
                    break;
                case R.id.cafeStarFour:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_red);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "GOOD";
                    break;
                case R.id.cafeStarFive:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_red);
                    fifthStar.setImageResource(R.drawable.ic_star_red);
                    rate = "BEST";
                    break;
            }
        }
    }
}