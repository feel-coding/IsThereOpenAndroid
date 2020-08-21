package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class CafeActivity extends AppCompatActivity  {

    ImageView cafeLogoIv;
    Intent intent;
    Cafe cafe;
    Double rate;
    ServerAPI serverAPI;
    TextView cafeTitleTv;
    TextView phoneNumberTv;
    Toolbar toolbar;
    CafeViewPagerAdapter cafeViewPagerAdapter;
    TextView openStateTv;
    TextView runningTimeTv;
    TextView addressTv;
    SwipeRefreshLayout swipeRefreshLayout;
    Button openBtn;
    Button breakBtn;
    Button closeBtn;
    SharedPreferences sharedPreferences;
    Long userSeq;
    Button toCafeReviewBtn;
    Button toOpenReviewBtn;
    Long cafeSeq;
    ImageView callBtn;

    TextView rateTv;
    TextView openStyleTv;
    TextView waitingTimeTv;
    TextView priceTv;
    TextView customerNumTv;
    TextView stayLongTv;
    TextView plugNumTv;
    TextView tableHeightTv;
    TextView lightnessTv;
    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.cafeToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        toolbar = findViewById(R.id.cafeToolbar);
        swipeRefreshLayout = findViewById(R.id.cafeUpdate);
        cafeViewPagerAdapter = new CafeViewPagerAdapter(this, 2);cafeTitleTv = findViewById(R.id.cafeTitleTv);
        cafeLogoIv = findViewById(R.id.cafeProfileImage);
        phoneNumberTv = findViewById(R.id.cafePhoneTv);
        openStateTv = findViewById(R.id.cafeOpenStateTv);
        runningTimeTv = findViewById(R.id.cafeRunningTimeTv);
        addressTv = findViewById(R.id.cafeAddressTv);
        openBtn = findViewById(R.id.cafeOpenBtn);
        breakBtn = findViewById(R.id.cafeBreakBtn);
        closeBtn = findViewById(R.id.cafeCloseBtn);
        callBtn = findViewById(R.id.cafeCall);
        rateTv = findViewById(R.id.cafeAvgRateTv);
        openStyleTv = findViewById(R.id.cafeAvgOpenStyle);

        lightnessTv = findViewById(R.id.avgLightness);
        tableHeightTv = findViewById(R.id.avgTableHeight);
        waitingTimeTv = findViewById(R.id.cafeAvgWaitingTime);
        plugNumTv = findViewById(R.id.avgPlugNum);
        priceTv = findViewById(R.id.cafeAvgPrice);
        stayLongTv = findViewById(R.id.avgStayLong);
        customerNumTv = findViewById(R.id.cafeAvgCustomerNum);

        firstStar = findViewById(R.id.cafeAvgStarOne);
        secondStar = findViewById(R.id.cafeAvgStarTwo);
        thirdStar = findViewById(R.id.cafeAvgStarThree);
        fourthStar = findViewById(R.id.cafeAvgStarFour);
        fifthStar = findViewById(R.id.cafeAvgStarFive);

        toCafeReviewBtn = findViewById(R.id.toCafeReviewBtn);
        toOpenReviewBtn = findViewById(R.id.toCafeOpenReviewBtn);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
        rate = intent.getDoubleExtra("rate", -1.0);
        serverAPI.getCafe(cafeSeq).enqueue(new Callback<Cafe>() {
            @Override
            public void onResponse(Call<Cafe> call, Response<Cafe> response) {
                if (response.isSuccessful()) {
                    cafe = response.body();
                    cafeTitleTv.setText(cafe.getName());
                    Glide.with(CafeActivity.this).load(cafe.getPhotoURL()).into(cafeLogoIv);
//                    Log.d("서버", bar.getCurrentState());
                    String openState = cafe.getCurrentState();
                    if(openState.equals("UNKNOWN")) {
                        openState = "등록된 오픈 정보 없음";
                        openStateTv.setText(openState);
                    }
                    else {
                        openStateTv.setText(openState);
                    }
                    addressTv.setText(cafe.getAddress());
                    runningTimeTv.setText(cafe.getRunningTime());
                    phoneNumberTv.setText(cafe.getPhoneNum());
                }
            }

            @Override
            public void onFailure(Call<Cafe> call, Throwable t) {

            }
        });
        refreshInfo();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverAPI.getCafe(cafe.getSeq()).enqueue(new Callback<Cafe>() {
                    @Override
                    public void onResponse(Call<Cafe> call, Response<Cafe> response) {
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
                    public void onFailure(Call<Cafe> call, Throwable t) {

                    }
                });
                refreshInfo();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cafe.getPhoneNum()));
                startActivity(intent);
            }
        });
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.putCafeOpenReview(cafe.getSeq(), userSeq, "OPEN").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CafeActivity.this, cafe.getName() + "의 오픈 정보가 OPEN으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
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
                serverAPI.putCafeOpenReview(cafe.getSeq(), userSeq, "BREAK").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CafeActivity.this, cafe.getName() + "의 오픈 정보가 BREAK로 변경되었습니다.", Toast.LENGTH_SHORT).show();
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
                serverAPI.putCafeOpenReview(cafe.getSeq(), userSeq, "CLOSE").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CafeActivity.this, cafe.getName() + "의 오픈 정보가 CLOSE로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            openStateTv.setText("CLOSE");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        toCafeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CafeActivity.this, CafeReviewActivity.class);
                intent.putExtra("seq", cafeSeq);
                intent.putExtra("name", cafe.getName());
                startActivity(intent);
            }
        });
        toOpenReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CafeActivity.this, OpenReviewActivity.class);
                intent.putExtra("seq", cafeSeq);
                intent.putExtra("type", "cafe");
                intent.putExtra("name", cafe.getName());
                startActivity(intent);
            }
        });

        cafeLogoIv.setBackground(new ShapeDrawable(new OvalShape()));
        cafeLogoIv.setClipToOutline(true);
        cafeLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CafeActivity.this, ImageActivity.class);
                i.putExtra("imageURL", cafe.getPhotoURL());
                startActivity(i);
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

    void refreshInfo() {

        serverAPI.getCafeAvgLightness(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> lightnessList = new ArrayList<>();
                    for(String str : response.body()) {
                        if(str.equals("LIGHT")) {
                            lightnessList.add("밝은 편");
                        }
                        else if (str.equals("NORMAL")) {
                            lightnessList.add("보통");
                        }
                        else if (str.equals("DARK")) {
                            lightnessList.add("어두운 편");
                        }
                    }
                    if(lightnessList.size() == 0) {
                        lightnessTv.setText("정보 없음");
                    }
                    else if (lightnessList.size() == 1) {
                        lightnessTv.setText(lightnessList.get(0));
                    }
                    else if (lightnessList.size() == 2) {
                        lightnessTv.setText(lightnessList.get(0) + "~" + lightnessList.get(1));
                    }
                    else if (lightnessList.size() == 3) {
                        lightnessTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getCafeAvgTableHeight(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> tableHeightList = new ArrayList<>();
                    for(String s : response.body()) {
                        if(s.equals("LOW")) {
                            tableHeightList.add("낮은 편");
                        }
                        else if (s.equals("NORMAL")) {
                            tableHeightList.add("보통");
                        }
                        else if (s.equals("HIGH")) {
                            tableHeightList.add("높은 편");
                        }
                        else if (s.equals("NOTABLE")) {
                            tableHeightList.add("테이블 없음");
                        }
                    }

                    if(tableHeightList.size() == 0) {
                        tableHeightTv.setText("정보 없음");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getAvgPlugNum(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Log.d("플러그 ", "들어옴");
                if (response.isSuccessful()) {
                    Log.d("플러그 ", "성공");
                    List<String> plugNumList = new ArrayList<>();
                    for (String s : response.body()) {
                        if(s.equals("LITTLE")) {
                            plugNumList.add("적은 편");
                        }
                        else if (s.equals("NORMAL")) {
                            plugNumList.add("보통");
                        }
                        else if (s.equals("MANY")) {
                            plugNumList.add("많은 편");
                        }
                        else if (s.equals("NOTABLE")) {
                            plugNumList.add("테이블 없음");
                        }
                    }
                    if(plugNumList.size() == 0) {
                        plugNumTv.setText("정보 없음");
                    }
                    else if (plugNumList.size() == 1) {
                        plugNumTv.setText(plugNumList.get(0));
                    }
                    else if (plugNumList.size() == 2) {
                        plugNumTv.setText(plugNumList.get(0) + "~" + plugNumList.get(1));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("플러그", t.getMessage());
            }
        });

        serverAPI.getCafeAvgPrice(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> priceList = new ArrayList<>();
                    for(String s : response.body()) {
                        if (s.equals("EXPENSIVE")) {
                            priceList.add("비싼 편");
                        }
                        else if (s.equals("NORMAL")) {
                            priceList.add("보통");
                        }
                        else if (s.equals("CHEAP")) {
                            priceList.add("싼 편");
                        }
                    }
                    if(priceList.size() == 0) {
                        priceTv.setText("정보 없음");
                    }
                    else if (priceList.size() == 1) {
                        priceTv.setText(priceList.get(0));
                    }
                    else if (priceList.size() == 2) {
                        priceTv.setText(priceList.get(0) + "~" + priceList.get(1));
                    }
                    else if (priceList.size() == 3) {
                        priceTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getCafeAvgOpenStyle(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> openStyleList = new ArrayList<>();
                    for (String s : response.body()) {
                        if(s.equals("STABLE")) {
                            openStyleList.add("잘 지키는 편");
                        }
                        else if (s.equals("NORMAL")) {
                            openStyleList.add("보통");
                        }
                        else if (s.equals("UNSTABLE")) {
                            openStyleList.add("마음대로 여는 편");
                        }
                    }
                    if(openStyleList.size() == 0) {
                        openStyleTv.setText("정보 없음");
                    }
                    else if (openStyleList.size() == 1) {
                        openStyleTv.setText(openStyleList.get(0));
                    }
                    else if (openStyleList.size() == 2) {
                        openStyleTv.setText(openStyleList.get(0) + "~" + openStyleList.get(1));
                    }
                    else {
                        priceTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

        serverAPI.getAvgCustomerNum(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> customerNumList = new ArrayList<>();
                    for (String s : response.body()) {
                        if(s.equals("UNCROWDED")) {
                            customerNumList.add("적은 편");
                        }
                        else if (s.equals("NORMAL")) {
                            customerNumList.add("보통");
                        }
                        else if (s.equals("CROWDED")) {
                            customerNumList.add("많은 편");
                        }
                    }
                    if(customerNumList.size() == 0) {
                        customerNumTv.setText("정보 없음");
                    }
                    else if (customerNumList.size() == 1) {
                        customerNumTv.setText(customerNumList.get(0));
                    }
                    else if (customerNumList.size() == 2) {
                        customerNumTv.setText(customerNumList.get(0) + "~" + customerNumList.get(1));
                    }
                    else {
                        priceTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getCafeAvgWaitingTime(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> timeList = new ArrayList<>();
                    for (String s : response.body()) {
                        if(s.equals("SHORT")) {
                            timeList.add("짧은 편");
                        } else if (s.equals("NORMAL")) {
                            timeList.add("보통");
                        }
                        else if (s.equals("LONG")) {
                            timeList.add("오래 걸리는 편");
                        }
                    }
                    if(timeList.size() == 0) {
                        waitingTimeTv.setText("정보 없음");
                    }
                    else if (timeList.size() == 1) {
                        waitingTimeTv.setText(timeList.get(0));
                    }
                    else if (timeList.size() == 2) {
                        waitingTimeTv.setText(timeList.get(0) + "~" + timeList.get(1));
                    }
                    else {
                        waitingTimeTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getCafeAvgLightness(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getCafeAvgTableHeight(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> heightList = new ArrayList<>();
                    for (String s : response.body()) {
                        if(s.equals("LOW")) {
                            heightList.add("낮은 편");
                        }
                        else if (s.equals("NORMAL")) {
                            heightList.add("보통");
                        }
                        else if (s.equals("HIGH")) {
                            heightList.add("높은 편");
                        }
                        else if (s.equals("NOTABLE")) {
                            heightList.add("테이블 없음");
                        }
                    }
                    if (heightList.size() == 0) {
                        tableHeightTv.setText("정보 없음");
                    }
                    else if (heightList.size() == 1) {
                        tableHeightTv.setText(heightList.get(0));
                    }
                    else if (heightList.size() == 2) {
                        tableHeightTv.setText(heightList.get(0) + "~" + heightList.get(1));
                    }
                    else {
                        tableHeightTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getAvgStayLong(cafeSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (String s : response.body()) {
                        if(s.equals("POSSIBLE")) {
                            list.add("가능");
                        }
                        else if (s.equals("NORMAL")) {
                            list.add("보통");
                        }
                        else if (s.equals("UNCOMFORTABLE")) {
                            list.add("눈치보여요");
                        }
                        else if (s.equals("TAKEOUT")) {
                            list.add("테이블 없음");
                        }
                    }
                    if(list.size() == 0) {
                        stayLongTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        stayLongTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        stayLongTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else {
                        stayLongTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

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