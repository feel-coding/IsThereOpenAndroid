package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class CafeActivity extends AppCompatActivity implements CafeInfoReviewFragment.OnFragmentInteractionListener, OpenReviewFragment.OnFragmentInteractionListener {

    ImageView cafeLogoIv;
    Intent intent;
    Cafe cafe;
    ServerAPI serverAPI;
    TextView cafeTitleTv;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    String[] tabTitles= {"가게 리뷰", "오픈 리뷰"};
    CafeViewPagerAdapter cafeViewPagerAdapter;
    TextView openStateTv;
    TextView runningTimeTv;
    TextView addressTv;
    SwipeRefreshLayout swipeRefreshLayout;
    Button openBtn;
    Button breakBtn;
    Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
//        actionBar.setHomeAsUpIndicator(R.drawable.close_black);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        toolbar = findViewById(R.id.cafeToolbar);
        swipeRefreshLayout = findViewById(R.id.cafeUpdate);
        cafeViewPagerAdapter = new CafeViewPagerAdapter(this, 2);
        tabLayout = findViewById(R.id.reviewTabLayout);
        viewPager = findViewById(R.id.cafeViewpager);
        viewPager.setAdapter(cafeViewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {tab.setText(tabTitles[position]); viewPager.setCurrentItem(tab.getPosition(), true);}).attach();
        cafeTitleTv = findViewById(R.id.cafeTitleTv);
        cafeLogoIv = findViewById(R.id.cafeProfileImage);
        openStateTv = findViewById(R.id.cafeOpenStateTv);
        runningTimeTv = findViewById(R.id.cafeRunningTimeTv);
        addressTv = findViewById(R.id.cafeAddressTv);
        openBtn = findViewById(R.id.cafeOpenBtn);
        breakBtn = findViewById(R.id.cafeBreakBtn);
        closeBtn = findViewById(R.id.cafeCloseBtn);

        intent = getIntent();
        /*serverAPI.getCafe(intent.getIntExtra("seq", 0)).enqueue(new Callback<Cafe>() {
            @Override
            public void onResponse(Call<Cafe> call, Response<Cafe> response) {
                if (response.isSuccessful()) {
                    cafe = response.body();
                    cafeTitleTv.setText(cafe.getName());
                    Glide.with(CafeActivity.this).load(cafe.getPhotoURL()).into(cafeLogoIv);
//                    Log.d("서버", cafe.getCurrentState());
                    String openState;
                    if (cafe.getCurrentState() == 0)
                        openState = "CLOSE";

                    else if (cafe.getCurrentState() == 1)
                        openState = "BREAK";

                    else if (cafe.getCurrentState() == 2)
                        openState = "OPEN";
                    else
                        openState = "UNKNOWN";
                    openStateTv.setText(openState);
                    addressTv.setText(cafe.getAddress());
                    runningTimeTv.setText(cafe.getRunningTime());
                }
            }

            @Override
            public void onFailure(Call<Cafe> call, Throwable t) {

            }
        });*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*serverAPI.getCafe(cafe.getSeq()).enqueue(new Callback<Cafe>() {
                    @Override
                    public void onResponse(Call<Cafe> call, Response<Cafe> response) {
                        if (response.isSuccessful()) {
                            Integer state = response.body().getCurrentState();
                            if(state == 0) {
                                openStateTv.setText("CLOSE");
                            }
                            else if (state == 1) {
                                openStateTv.setText("BREAK");
                            }
                            else if (state == 2) {
                                openStateTv.setText("OPEN");
                            }
                            else {
                                openStateTv.setText("UNKNOWN");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Cafe> call, Throwable t) {

                    }
                });*/
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*serverAPI.putCafeOpenReview(cafe.getSeq(), 0, 2).enqueue(new Callback<Void>() {
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
                });*/
            }
        });
        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*serverAPI.putCafeOpenReview(cafe.getSeq(), 0, 1).enqueue(new Callback<Void>() {
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
                });*/
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*serverAPI.putCafeOpenReview(cafe.getSeq(), 0, 0).enqueue(new Callback<Void>() {
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
                });*/
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}