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

public class BarReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ServerAPI serverAPI;
    Long barSeq;
    Intent intent;
    BarReviewAdapter adapter;
    Button writeReviewBtn;
    TextView reviewCount;
    LinearLayout noReviewLayout;
    List<BarInfoReview> reviewList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_review);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        intent = getIntent();
        barSeq = intent.getLongExtra("seq", 0);
        Log.d("술집 seq", barSeq + "");
        recyclerView = findViewById(R.id.barReviewRv);
        writeReviewBtn = findViewById(R.id.writeBarReviewBtn);
        swipeRefreshLayout = findViewById(R.id.barInfoReviewSwipe);
        reviewCount = findViewById(R.id.barReviewCount);
        noReviewLayout = findViewById(R.id.barNoReview);
        refreshReviewList();
        adapter = new BarReviewAdapter(reviewList, BarReviewActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(BarReviewActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshReviewList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BarReviewActivity.this, WriteBarReviewActivity.class);
                intent.putExtra("seq", barSeq);
                startActivity(intent);
            }
        });
    }

    void refreshReviewList() {
        serverAPI.getBarInfoReviewList(barSeq).enqueue(new Callback<List<BarInfoReview>>() {
            @Override
            public void onResponse(Call<List<BarInfoReview>> call, Response<List<BarInfoReview>> response) {
                if (response.isSuccessful()) {
                    reviewList.clear();
                    reviewList = response.body();
                    reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                    if (reviewList.size() == 0) {
                        swipeRefreshLayout.setVisibility(View.GONE);
                        noReviewLayout.setVisibility(View.VISIBLE);
                    }

                    adapter = new BarReviewAdapter(response.body(), BarReviewActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<BarInfoReview>> call, Throwable t) {
                Log.d("술집 리뷰", "실패");
            }
        });
    }
}
