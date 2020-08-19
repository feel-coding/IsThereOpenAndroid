package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    List<BarInfoReview> barReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_review);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        intent = getIntent();
        barSeq = intent.getLongExtra("seq", 0);
        recyclerView = findViewById(R.id.cafeReviewRv);
        serverAPI.getBarInfoReviewList(barSeq).enqueue(new Callback<List<BarInfoReview>>() {
            @Override
            public void onResponse(Call<List<BarInfoReview>> call, Response<List<BarInfoReview>> response) {
                if(response.isSuccessful()) {
                    Log.d("술집 리뷰", response.body().get(0).getMood());
                    barReviewList = response.body();
                    adapter = new BarReviewAdapter(barReviewList, BarReviewActivity.this);
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
