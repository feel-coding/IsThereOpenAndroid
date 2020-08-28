package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CafeReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ServerAPI serverAPI;
    Long cafeSeq;
    String cafeName;
    Intent intent;
    CafeReviewAdapter adapter;
    Button writeReviewBtn;
    TextView reviewCount;
    SwipeRefreshLayout noReviewLayout;
    List<CafeInfoReview> reviewList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    TextView toolbarTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_review);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.cafeReviewToolbar);
        toolbarTitleTv = findViewById(R.id.cafeReviewTitleTv);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
        cafeName = intent.getStringExtra("name");
        toolbarTitleTv.setText(cafeName);
        Log.d("카페 seq", cafeSeq + "");
        recyclerView = findViewById(R.id.cafeReviewRv);
        writeReviewBtn = findViewById(R.id.writeCafeReviewBtn);
        swipeRefreshLayout = findViewById(R.id.cafeInfoReviewSwipe);
        reviewCount = findViewById(R.id.cafeReviewCount);
        noReviewLayout = findViewById(R.id.cafeNoReview);
        refreshReviewList();
        adapter = new CafeReviewAdapter(reviewList, CafeReviewActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(CafeReviewActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshReviewList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        noReviewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshReviewList();
                noReviewLayout.setRefreshing(false);
            }
        });
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CafeReviewActivity.this, WriteCafeReviewActivity.class);
                intent.putExtra("seq", cafeSeq);
                intent.putExtra("name", cafeName);
                startActivity(intent);
            }
        });
    }
    void refreshReviewList() {
        serverAPI.getCafeInfoReviewList(cafeSeq).enqueue(new Callback<List<CafeInfoReview>>() {
            @Override
            public void onResponse(Call<List<CafeInfoReview>> call, Response<List<CafeInfoReview>> response) {
                if(response.isSuccessful()) {
                    reviewList.clear();
                    reviewList = response.body();
                    reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                    if(reviewList.size() == 0) {
                        swipeRefreshLayout.setVisibility(View.GONE);
                        noReviewLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        noReviewLayout.setVisibility(View.GONE);
                    }
                    adapter = new CafeReviewAdapter(response.body(), CafeReviewActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CafeInfoReview>> call, Throwable t) {

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
}
