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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ServerAPI serverAPI;
    Long restaurantSeq;
    Intent intent;
    RestaurantReviewAdapter adapter;
    Button writeReviewBtn;
    List<RestaurantInfoReview> reviewList = new ArrayList<>();
    TextView reviewCount;
    SwipeRefreshLayout swipeRefreshLayout;
    SwipeRefreshLayout noReviewLayout;
    String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_review);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        intent = getIntent();
        restaurantSeq = intent.getLongExtra("seq", 0);
        restaurantName = intent.getStringExtra("name");
        Log.d("카페 seq", restaurantSeq + "");
        recyclerView = findViewById(R.id.restaurantReviewRv);
        writeReviewBtn = findViewById(R.id.writeRestaurantReviewBtn);
        swipeRefreshLayout = findViewById(R.id.restaurantInfoReviewSwipe);
        reviewCount = findViewById(R.id.restaurantReviewCount);
        noReviewLayout = findViewById(R.id.restaurantNoReview);
        refreshReviewList();
        adapter = new RestaurantReviewAdapter(reviewList, RestaurantReviewActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(RestaurantReviewActivity.this));
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
                Intent intent = new Intent(RestaurantReviewActivity.this, WriteRestaurantReviewActivity.class);
                intent.putExtra("seq", restaurantSeq);
                intent.putExtra("name", restaurantName);
                startActivity(intent);
            }
        });
    }
    void refreshReviewList(){
        serverAPI.getRestaurantInfoReviewList(restaurantSeq).enqueue(new Callback<List<RestaurantInfoReview>>() {
            @Override
            public void onResponse(Call<List<RestaurantInfoReview>> call, Response<List<RestaurantInfoReview>> response) {
                if(response.isSuccessful()) {
                    reviewList.clear();
                    reviewList = response.body();
                    if(reviewList.size() == 0) {
                        swipeRefreshLayout.setVisibility(View.GONE);
                        noReviewLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        noReviewLayout.setVisibility(View.GONE);
                    }
                    reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                    adapter = new RestaurantReviewAdapter(reviewList, RestaurantReviewActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantInfoReview>> call, Throwable t) {
                Toast.makeText(RestaurantReviewActivity.this, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
