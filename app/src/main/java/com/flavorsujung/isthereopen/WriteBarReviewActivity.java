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

public class WriteBarReviewActivity extends AppCompatActivity {

    ServerAPI serverAPI;
    Long barSeq;
    Long userSeq;
    String barName;
    Intent intent;
    SharedPreferences sharedPreferences;

    Button openStyleStableBtn;
    Button openStyleNormalBtn;
    Button openStyleUnstableBtn;
    Button priceCheapBtn;
    Button priceNormalBtn;
    Button priceExpensiveBtn;

    Button moodSilentBtn;
    Button moodNormalBtn;
    Button moodLoudBtn;
    Button toiletOneBtn;
    Button toiletSeparateBtn;
    Button sojuBtn;
    Button beerBtn;
    Button makgeolliBtn;
    Button wineBtn;
    Button vodkaBtn;
    Button cleanBtn;
    Button cleannessNormalBtn;
    Button dirtyBtn;
    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;

    String rate = "";
    String mood = "";
    String toilet = "";
    String cleanness = "";
    String price = "";
    String openStyle = "";
    String mainAlcohol = "";
    Button writeReviewBtn;

    Toolbar toolbar;
    TextView toolbarTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_bar_review);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.writeBarReviewToolbar);
        toolbarTitleTv = findViewById(R.id.writeBarReviewTitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black);
        intent = getIntent();
        barSeq = intent.getLongExtra("seq", 0L);
        barName = intent.getStringExtra("name");
        toolbarTitleTv.setText(barName + " 리뷰 쓰기");
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        priceCheapBtn = findViewById(R.id.barPriceCheap);
        priceNormalBtn = findViewById(R.id.barPriceNormal);
        priceExpensiveBtn = findViewById(R.id.barPriceExpensive);
        openStyleNormalBtn = findViewById(R.id.barOpenStyleNormal);
        openStyleStableBtn = findViewById(R.id.barOpenStyleStable);
        openStyleUnstableBtn = findViewById(R.id.barOpenStyleUnstable);
        moodLoudBtn = findViewById(R.id.moodLoud);
        moodSilentBtn = findViewById(R.id.moodSilent);
        moodNormalBtn = findViewById(R.id.moodNormal);
        toiletSeparateBtn = findViewById(R.id.toiletSeparate);
        toiletOneBtn = findViewById(R.id.toiletOne);
        sojuBtn = findViewById(R.id.soju);
        beerBtn = findViewById(R.id.beer);
        makgeolliBtn = findViewById(R.id.makgeolli);
        wineBtn = findViewById(R.id.wine);
        vodkaBtn = findViewById(R.id.vodka);
        cleanBtn = findViewById(R.id.barClean);
        cleannessNormalBtn = findViewById(R.id.barCleannessNormal);
        dirtyBtn = findViewById(R.id.barDirty);





        firstStar = findViewById(R.id.barStarOne);
        secondStar = findViewById(R.id.barStarTwo);
        thirdStar = findViewById(R.id.barStarThree);
        fourthStar = findViewById(R.id.barStarFour);
        fifthStar = findViewById(R.id.barStarFive);
        writeReviewBtn = findViewById(R.id.barReviewFinish);



        View.OnClickListener priceClickListener = new PriceClickListener();
        priceExpensiveBtn.setOnClickListener(priceClickListener);
        priceCheapBtn.setOnClickListener(priceClickListener);
        priceNormalBtn.setOnClickListener(priceClickListener);

        View.OnClickListener moodClickListener = new MoodClickListener();
        moodNormalBtn.setOnClickListener(moodClickListener);
        moodSilentBtn.setOnClickListener(moodClickListener);
        moodLoudBtn.setOnClickListener(moodClickListener);

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

        View.OnClickListener toiletClickListener = new ToiletClickListener();
        toiletOneBtn.setOnClickListener(toiletClickListener);
        toiletSeparateBtn.setOnClickListener(toiletClickListener);


        View.OnClickListener alcoholClickListener = new AlcoholClickListener();
        sojuBtn.setOnClickListener(alcoholClickListener);
        beerBtn.setOnClickListener(alcoholClickListener);
        makgeolliBtn.setOnClickListener(alcoholClickListener);
        wineBtn.setOnClickListener(alcoholClickListener);
        vodkaBtn.setOnClickListener(alcoholClickListener);

        View.OnClickListener cleannessClickListener = new CleannessClickListener();
        cleannessNormalBtn.setOnClickListener(cleannessClickListener);
        cleanBtn.setOnClickListener(cleannessClickListener);
        dirtyBtn.setOnClickListener(cleannessClickListener);

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rate.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "평점을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if (openStyle.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "오픈 스타일을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(mood.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "분위기를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(price.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "가격대를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(toilet.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "화장실 타입을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(mainAlcohol.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "메인 주류를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(cleanness.equals("")) {
                    Toast.makeText(WriteBarReviewActivity.this, "청결도를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    serverAPI.putBarInfoReview(barSeq, userSeq, rate, toilet, mood, mainAlcohol, price, cleanness, openStyle).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Log.d("술집리뷰", barSeq + " " + userSeq + " " +  rate + " " + toilet + " " +  mood + " " + mainAlcohol + " " + price + " " + cleanness + " " + openStyle);
                                Toast.makeText(WriteBarReviewActivity.this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
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
                case R.id.barOpenStyleStable:
                    openStyleStableBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "STABLE";
                    break;
                case R.id.barOpenStyleNormal:
                    openStyleNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleStableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "NORMAL";
                    break;
                case R.id.barOpenStyleUnstable:
                    openStyleUnstableBtn.setBackgroundResource(R.drawable.full_red_round);
                    openStyleUnstableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    openStyleStableBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleStableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyleNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    openStyleNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    openStyle = "UNSTABLE";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
    class ToiletClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.toiletSeparate:
                    toiletSeparateBtn.setBackgroundResource(R.drawable.full_red_round);
                    toiletSeparateBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    toiletOneBtn.setBackgroundResource(R.drawable.black_round_border);
                    toiletOneBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    toilet = "SEPARATION";
                    break;
                case R.id.toiletOne:
                    toiletOneBtn.setBackgroundResource(R.drawable.full_red_round);
                    toiletOneBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    toiletSeparateBtn.setBackgroundResource(R.drawable.black_round_border);
                    toiletSeparateBtn.setTextColor(getResources().getColor(R.color.colorBlack));

                    toilet = "ONE";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
    class AlcoholClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.soju:
                    sojuBtn.setBackgroundResource(R.drawable.full_red_round);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    beerBtn.setBackgroundResource(R.drawable.black_round_border);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    makgeolliBtn.setBackgroundResource(R.drawable.black_round_border);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    wineBtn.setBackgroundResource(R.drawable.black_round_border);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    vodkaBtn.setBackgroundResource(R.drawable.black_round_border);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mainAlcohol = "SOJU";
                    break;
                case R.id.beer:
                    beerBtn.setBackgroundResource(R.drawable.full_red_round);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    sojuBtn.setBackgroundResource(R.drawable.black_round_border);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    makgeolliBtn.setBackgroundResource(R.drawable.black_round_border);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    wineBtn.setBackgroundResource(R.drawable.black_round_border);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    vodkaBtn.setBackgroundResource(R.drawable.black_round_border);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mainAlcohol = "BEER";
                    break;
                case R.id.makgeolli:
                    makgeolliBtn.setBackgroundResource(R.drawable.full_red_round);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    sojuBtn.setBackgroundResource(R.drawable.black_round_border);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    beerBtn.setBackgroundResource(R.drawable.black_round_border);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    wineBtn.setBackgroundResource(R.drawable.black_round_border);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    vodkaBtn.setBackgroundResource(R.drawable.black_round_border);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mainAlcohol = "MAKGEOLLI";
                    break;
                case R.id.wine:
                    wineBtn.setBackgroundResource(R.drawable.full_red_round);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    sojuBtn.setBackgroundResource(R.drawable.black_round_border);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    beerBtn.setBackgroundResource(R.drawable.black_round_border);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    vodkaBtn.setBackgroundResource(R.drawable.black_round_border);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    makgeolliBtn.setBackgroundResource(R.drawable.black_round_border);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mainAlcohol = "WINE";
                    break;
                case R.id.vodka:
                    vodkaBtn.setBackgroundResource(R.drawable.full_red_round);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    sojuBtn.setBackgroundResource(R.drawable.black_round_border);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    beerBtn.setBackgroundResource(R.drawable.black_round_border);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    makgeolliBtn.setBackgroundResource(R.drawable.black_round_border);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    wineBtn.setBackgroundResource(R.drawable.black_round_border);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mainAlcohol = "VODKA";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
    class CleannessClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.barClean:
                    cleanBtn.setBackgroundResource(R.drawable.full_red_round);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    cleannessNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleannessNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    dirtyBtn.setBackgroundResource(R.drawable.black_round_border);
                    dirtyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleanness = "CLEAN";
                    break;
                case R.id.barCleannessNormal:
                    cleannessNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    cleannessNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    cleanBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    dirtyBtn.setBackgroundResource(R.drawable.black_round_border);
                    dirtyBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleanness = "NORMAL";
                    break;
                case R.id.barDirty:
                    dirtyBtn.setBackgroundResource(R.drawable.full_red_round);
                    dirtyBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    cleanBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleannessNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    cleannessNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    cleanness = "DIRTY";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }


    class PriceClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.barPriceCheap:
                    priceCheapBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceExpensiveBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "CHEAP";
                    break;
                case R.id.barPriceNormal:
                    priceNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceCheapBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceExpensiveBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "NORMAL";
                    break;
                case R.id.barPriceExpensive:
                    priceExpensiveBtn.setBackgroundResource(R.drawable.full_red_round);
                    priceExpensiveBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    priceCheapBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceCheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    priceNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    priceNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    price = "EXPENSIVE";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }

    class MoodClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.moodLoud:
                    moodLoudBtn.setBackgroundResource(R.drawable.full_red_round);
                    moodLoudBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    moodNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    moodNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    moodSilentBtn.setBackgroundResource(R.drawable.black_round_border);
                    moodSilentBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mood = "LOUD";
                    break;
                case R.id.moodNormal:
                    moodNormalBtn.setBackgroundResource(R.drawable.full_red_round);
                    moodNormalBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    moodLoudBtn.setBackgroundResource(R.drawable.black_round_border);
                    moodLoudBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    moodSilentBtn.setBackgroundResource(R.drawable.black_round_border);
                    moodSilentBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mood = "NORMAL";
                    break;
                case R.id.moodSilent:
                    moodSilentBtn.setBackgroundResource(R.drawable.full_red_round);
                    moodSilentBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    moodLoudBtn.setBackgroundResource(R.drawable.black_round_border);
                    moodLoudBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    moodNormalBtn.setBackgroundResource(R.drawable.black_round_border);
                    moodNormalBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                    mood = "SILENT";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }

    class StarClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.barStarOne:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_gray);
                    thirdStar.setImageResource(R.drawable.ic_star_gray);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "WORST";
                    break;
                case R.id.barStarTwo:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_gray);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "BAD";
                    break;
                case R.id.barStarThree:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_gray);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "SOSO";
                    break;
                case R.id.barStarFour:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_red);
                    fifthStar.setImageResource(R.drawable.ic_star_gray);
                    rate = "GOOD";
                    break;
                case R.id.barStarFive:
                    firstStar.setImageResource(R.drawable.ic_star_red);
                    secondStar.setImageResource(R.drawable.ic_star_red);
                    thirdStar.setImageResource(R.drawable.ic_star_red);
                    fourthStar.setImageResource(R.drawable.ic_star_red);
                    fifthStar.setImageResource(R.drawable.ic_star_red);
                    rate = "BEST";
                    break;
            }
            if(!rate.equals("") && !mood.equals("") && !openStyle.equals("") && !price.equals("") && !toilet.equals("") && !mainAlcohol.equals("") && !cleanness.equals("")) {
                writeReviewBtn.setBackgroundResource(R.drawable.full_red_square);
            }
        }
    }
}