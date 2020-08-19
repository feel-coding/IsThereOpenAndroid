package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        serverAPI.getBarInfoReviewList(barSeq).enqueue(new Callback<List<BarInfoReview>>() {
            @Override
            public void onResponse(Call<List<BarInfoReview>> call, Response<List<BarInfoReview>> response) {
                if(response.isSuccessful()) {
                    adapter = new BarReviewAdapter(response.body(), BarReviewActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(BarReviewActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new MyItemDecorator());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BarInfoReview>> call, Throwable t) {
                Log.d("술집 리뷰", "실패");
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
}
