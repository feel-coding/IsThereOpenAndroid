package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CafeActivity extends AppCompatActivity implements CafeInfoReviewFragment.OnFragmentInteractionListener, OpenReviewFragment.OnFragmentInteractionListener {



////////////////////////////////////////////////////////////////
    private CafeViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String[] tabTitles= {"가게리뷰", "오픈리뷰"};
////////////////////////////////////////////////////////////////






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
//    Button toCafeReviewBtn;
//    Button toOpenReviewBtn;
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

    ChangeOpenStateDialog dialog;
    View.OnClickListener openListener;
    View.OnClickListener breakListener;
    View.OnClickListener closeListener;
    View.OnClickListener cancelListener;

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

        viewPagerAdapter = new CafeViewPagerAdapter(this, 2);
        viewPager = findViewById(R.id.cafeViewPager);
//        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setUserInputEnabled(false);
        tabLayout = findViewById(R.id.cafeReviewTab);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {tab.setText(tabTitles[position]);
                    viewPager.setCurrentItem(tab.getPosition(), true);}).attach();

       toolbar = findViewById(R.id.cafeToolbar);
        swipeRefreshLayout = findViewById(R.id.cafeUpdate);
        cafeViewPagerAdapter = new CafeViewPagerAdapter(this, 2);
        cafeTitleTv = findViewById(R.id.cafeTitleTv);
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


//        toCafeReviewBtn = findViewById(R.id.toCafeReviewBtn);
//        toOpenReviewBtn = findViewById(R.id.toCafeOpenReviewBtn);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
        rate = intent.getDoubleExtra("rate", -1.0);

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
                refreshInfo();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("뷰페이저", "들어옴 " + position);
                if(position == 0) {
                    viewPager.requestLayout();
                    cafeViewPagerAdapter.notifyDataSetChanged();
                }
                else if (position == 1) {
                    viewPager.requestLayout();
                    cafeViewPagerAdapter.notifyDataSetChanged();
                }
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cafe.getPhoneNum()));
                startActivity(intent);
            }
        });

        openListener = v -> changeState("OPEN");
        breakListener = v -> changeState("BREAK");
        closeListener = v-> changeState("CLOSE");
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(CafeActivity.this, openListener, cancelListener, cafe.getName() + "의 상태를 OPEN으로 변경하시겠습니까?");
                dialog.show();
            }
        });
        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(CafeActivity.this, breakListener, cancelListener, cafe.getName() + "의 상태를  BREAK로 변경하시겠습니까?");
                dialog.show();

            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(CafeActivity.this, closeListener, cancelListener, cafe.getName() + "의 상태를 CLOSE로 변경하시겠습니까?");
                dialog.show();
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
        serverAPI.getCafe(cafeSeq).enqueue(new Callback<Cafe>() {
            @Override
            public void onResponse(Call<Cafe> call, Response<Cafe> response) {
                if (response.isSuccessful()) {
                    cafe = response.body();
                    cafeTitleTv.setText(cafe.getName());
                    Glide.with(CafeActivity.this).load(cafe.getPhotoURL()).into(cafeLogoIv);
                    String openState = cafe.getCurrentState();
                    if(openState.equals("UNKNOWN") || openState == null) {
                        openState = "등록된 오픈 정보 없음";
                        openStateTv.setText(openState);
                    }
                    else {
                        openStateTv.setText(openState);
                    }
                    addressTv.setText(cafe.getAddress());
                    runningTimeTv.setText(cafe.getRunningTime());
                    phoneNumberTv.setText(cafe.getPhoneNum());
                    if(cafe.getAvgOpenStyle() == null || cafe.getAvgOpenStyle().equals("UNKNOWN"))
                        openStyleTv.setText("정보 없음");
                    else
                        openStyleTv.setText(cafe.getAvgOpenStyle());
                    if(cafe.getAvgOpenStyle() == null)
                        priceTv.setText("정보 없음");
                    else
                        priceTv.setText(cafe.getAvgPrice());
                    if(cafe.getAvgOpenStyle() == null)
                        waitingTimeTv.setText("정보 없음");
                    else
                        waitingTimeTv.setText(cafe.getAvgWaitingTime());
                    if(cafe.getAvgOpenStyle() == null)
                        customerNumTv.setText("정보 없음");
                    else
                        customerNumTv.setText(cafe.getAvgCustomerNum());
                    if(cafe.getAvgOpenStyle() == null)
                        plugNumTv.setText("정보 없음");
                    else
                        plugNumTv.setText(cafe.getAvgPlugNum());
                    if(cafe.getAvgOpenStyle() == null)
                        tableHeightTv.setText("정보 없음");
                    else
                        tableHeightTv.setText(cafe.getAvgTableHeight());
                    if(cafe.getAvgOpenStyle() == null)
                        lightnessTv.setText("정보 없음");
                    else
                        lightnessTv.setText(cafe.getAvgLightness());
                    if(cafe.getAvgStayLong() == null)
                        stayLongTv.setText("정보 없음");
                    else
                        stayLongTv.setText(cafe.getAvgStayLong());
                }
            }

            @Override
            public void onFailure(Call<Cafe> call, Throwable t) {

            }
        });

        serverAPI.getCafeAvgRate(cafeSeq).enqueue(new Callback<Double>() {
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
                Log.d("서버 통신 실패 원인", t.getMessage());
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    void changeState(String openState) {
        CafeOpenReview cafeOpenReview = new CafeOpenReview();
        cafeOpenReview.setCafeSeq(cafe.getSeq());
        cafeOpenReview.setUserSeq(userSeq);
        cafeOpenReview.setOpenState(openState);
        serverAPI.putCafeOpenReview(cafeOpenReview/*cafe.getSeq(), userSeq, openState*/).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if(openState.equals("OPEN")) {
                        Toast.makeText(CafeActivity.this, cafe.getName() + "의 오픈 정보가 " + openState + "으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(CafeActivity.this, cafe.getName() + "의 오픈 정보가 " + openState + "로 변경되었습니다.", Toast.LENGTH_SHORT).show();
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