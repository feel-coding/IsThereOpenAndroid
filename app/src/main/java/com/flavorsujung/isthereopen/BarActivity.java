package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarActivity extends AppCompatActivity {

    ImageView barLogoIv;
    Intent intent;
    Bar bar;
    ServerAPI serverAPI;
    TextView barTitleTv;
    Toolbar toolbar;

    TextView openStateTv;
    TextView runningTimeTv;
    TextView addressTv;
    SwipeRefreshLayout swipeRefreshLayout;
    Button openBtn;
    Button breakBtn;
    Button closeBtn;
    SharedPreferences sharedPreferences;
    Long userSeq;
    Button toBarReviewBtn;
    Long barSeq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
//        actionBar.setHomeAsUpIndicator(R.drawable.close_black);
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
        openBtn = findViewById(R.id.barOpenBtn);
        breakBtn = findViewById(R.id.barBreakBtn);
        closeBtn = findViewById(R.id.barCloseBtn);
        toBarReviewBtn = findViewById(R.id.toBarReviewBtn);
        intent = getIntent();
        barSeq = intent.getLongExtra("seq", 0);
        serverAPI.getBar(barSeq).enqueue(new Callback<Bar>() {
            @Override
            public void onResponse(Call<Bar> call, Response<Bar> response) {
                if (response.isSuccessful()) {
                    bar = response.body();
                    barTitleTv.setText(bar.getName());
                    Glide.with(BarActivity.this).load(bar.getPhotoURL()).into(barLogoIv);
                    String openState = bar.getCurrentState();
                    openStateTv.setText(openState);
                    addressTv.setText(bar.getAddress());
                    runningTimeTv.setText(bar.getRunningTime());
                }
            }

            @Override
            public void onFailure(Call<Bar> call, Throwable t) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverAPI.getBar(bar.getSeq()).enqueue(new Callback<Bar>() {
                    @Override
                    public void onResponse(Call<Bar> call, Response<Bar> response) {
                        if (response.isSuccessful()) {
                            openStateTv.setText(response.body().getCurrentState());
                        }
                    }

                    @Override
                    public void onFailure(Call<Bar> call, Throwable t) {

                    }
                });
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
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

}