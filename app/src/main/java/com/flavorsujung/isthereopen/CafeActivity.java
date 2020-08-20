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

public class CafeActivity extends AppCompatActivity implements CafeInfoReviewFragment.OnFragmentInteractionListener, OpenReviewFragment.OnFragmentInteractionListener {

    ImageView cafeLogoIv;
    Intent intent;
    Cafe cafe;
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
        toCafeReviewBtn = findViewById(R.id.toCafeReviewBtn);
        toOpenReviewBtn = findViewById(R.id.toCafeOpenReviewBtn);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
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