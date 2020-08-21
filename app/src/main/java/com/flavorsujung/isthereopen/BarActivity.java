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

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarActivity extends AppCompatActivity {

    ImageView barLogoIv;
    Intent intent;
    Bar bar;
    Double rate;
    ServerAPI serverAPI;
    TextView barTitleTv;
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
    Button toBarReviewBtn;
    Button toOpenReviewBtn;
    Long barSeq;
    ImageView callBtn;

    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;
    TextView rateTv;

    TextView openStyleTv;
    TextView moodTv;
    TextView priceTv;
    TextView toiletTv;
    TextView alcoholTv;
    TextView cleannessTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.barToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        toolbar = findViewById(R.id.barToolbar);
        swipeRefreshLayout = findViewById(R.id.barUpdate);
        barTitleTv = findViewById(R.id.barTitleTv);
        barLogoIv = findViewById(R.id.barProfileImage);
        openStateTv = findViewById(R.id.barOpenStateTv);
        runningTimeTv = findViewById(R.id.barRunningTimeTv);
        addressTv = findViewById(R.id.barAddressTv);
        phoneNumberTv = findViewById(R.id.barPhoneTv);
        openBtn = findViewById(R.id.barOpenBtn);
        breakBtn = findViewById(R.id.barBreakBtn);
        closeBtn = findViewById(R.id.barCloseBtn);
        callBtn = findViewById(R.id.barCall);
        firstStar = findViewById(R.id.barAvgStarOne);
        secondStar = findViewById(R.id.barAvgStarTwo);
        thirdStar = findViewById(R.id.barAvgStarThree);
        fourthStar = findViewById(R.id.barAvgStarFour);
        fifthStar = findViewById(R.id.barAvgStarFive);
        rateTv = findViewById(R.id.barAvgRate);
        openStyleTv = findViewById(R.id.barAvgOpenStyle);
        moodTv = findViewById(R.id.barAvgMood);
        priceTv = findViewById(R.id.barAvgPrice);
        toiletTv = findViewById(R.id.barAvgToilet);
        alcoholTv = findViewById(R.id.barAvgAlcohol);
        cleannessTv = findViewById(R.id.barAvgCleanness);
        toBarReviewBtn = findViewById(R.id.toBarReviewBtn);
        toOpenReviewBtn = findViewById(R.id.toBarOpenReviewBtn);
        intent = getIntent();
        barSeq = intent.getLongExtra("seq", 0);
        rate = intent.getDoubleExtra("rate", -1.0);
        serverAPI.getBar(barSeq).enqueue(new Callback<Bar>() {
            @Override
            public void onResponse(Call<Bar> call, Response<Bar> response) {
                if (response.isSuccessful()) {
                    bar = response.body();
                    barTitleTv.setText(bar.getName());
                    Glide.with(BarActivity.this).load(bar.getPhotoURL()).into(barLogoIv);
                    String openState = bar.getCurrentState();
                    if(openState.equals("UNKNOWN")) {
                        openState = "등록된 오픈 정보 없음";
                        openStateTv.setText(openState);
                    }
                    else {
                        openStateTv.setText(openState);
                    }
                    addressTv.setText(bar.getAddress());
                    runningTimeTv.setText(bar.getRunningTime());
                    phoneNumberTv.setText(bar.getPhoneNum());
                    refreshInfo();
                }
            }

            @Override
            public void onFailure(Call<Bar> call, Throwable t) {

            }
        });
        refreshInfo();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverAPI.getBar(bar.getSeq()).enqueue(new Callback<Bar>() {
                    @Override
                    public void onResponse(Call<Bar> call, Response<Bar> response) {
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
                    public void onFailure(Call<Bar> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bar.getPhoneNum()));
                startActivity(intent);
            }
        });
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.putBarOpenReview(bar.getSeq(), userSeq, "OPEN").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BarActivity.this, bar.getName() + "의 오픈 정보가 OPEN으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
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
                serverAPI.putBarOpenReview(bar.getSeq(), userSeq, "BREAK").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BarActivity.this, bar.getName() + "의 오픈 정보가 BREAK로 변경되었습니다.", Toast.LENGTH_SHORT).show();
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
                serverAPI.putBarOpenReview(bar.getSeq(), userSeq, "CLOSE").enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BarActivity.this, bar.getName() + "의 오픈 정보가 CLOSE로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            openStateTv.setText("CLOSE");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });

        toBarReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BarActivity.this, BarReviewActivity.class);
                Log.d("술집리뷰", "술집 리뷰 버튼 눌림. seq: " + barSeq);
                intent.putExtra("seq", barSeq);
                intent.putExtra("name", bar.getName());
                startActivity(intent);
            }
        });

        toOpenReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BarActivity.this, OpenReviewActivity.class);
                intent.putExtra("seq", barSeq);
                intent.putExtra("type", "bar");
                intent.putExtra("name", bar.getName());
                startActivity(intent);
            }
        });

        barLogoIv.setBackground(new ShapeDrawable(new OvalShape()));
        barLogoIv.setClipToOutline(true);
        barLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarActivity.this, ImageActivity.class);
                i.putExtra("imageURL", bar.getPhotoURL());
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
        serverAPI.getBarAvgOpenStyle(barSeq).enqueue(new Callback<List<String>>() {
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
        serverAPI.getAvgMood(barSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        moodTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        moodTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        moodTv.setText(list.get(0) + "~" + list.get(1));
                    }
                    else if (list.size() == 3) {
                        moodTv.setText("의견이 많이 갈려요");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getBarAvgPrice(barSeq).enqueue(new Callback<List<String>>() {
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
        serverAPI.getAvgToilet(barSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        toiletTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        toiletTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        toiletTv.setText(list.get(0) + ", " + list.get(1));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getAvgAlcohol(barSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()) {
                    List<String> list = response.body();
                    if(list.size() == 0) {
                        alcoholTv.setText("정보 없음");
                    }
                    else if (list.size() == 1) {
                        alcoholTv.setText(list.get(0));
                    }
                    else if (list.size() == 2) {
                        alcoholTv.setText(list.get(0) + ", " + list.get(1));
                    }
                    else if (list.size() == 3) {
                        alcoholTv.setText(list.get(0) + ", " + list.get(1) + ", " +  list.get(2));
                    }
                    else if (list.size() >= 4) {
                        alcoholTv.setText("다양함");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        serverAPI.getBarAvgCleanness(barSeq).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
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
        serverAPI.getBarAvgRate(barSeq).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful()) {
                    Double rate = response.body();
                    rateTv.setText(String.format("%.1f", rate));
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