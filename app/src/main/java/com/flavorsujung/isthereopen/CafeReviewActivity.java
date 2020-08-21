package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_review);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
        cafeName = intent.getStringExtra("name");
        Log.d("카페 seq", cafeSeq + "");
        recyclerView = findViewById(R.id.cafeReviewRv);
        writeReviewBtn = findViewById(R.id.writeCafeReviewBtn);
        swipeRefreshLayout = findViewById(R.id.cafeInfoReviewSwipe);
        reviewCount = findViewById(R.id.cafeReviewCount);
        noReviewLayout = findViewById(R.id.cafeNoReview);
        refreshReviewList();
        adapter = new CafeReviewAdapter(reviewList, CafeReviewActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(CafeReviewActivity.this));
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
}
