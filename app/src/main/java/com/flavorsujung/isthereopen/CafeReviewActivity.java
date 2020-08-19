package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CafeReviewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ServerAPI serverAPI;
    Long cafeSeq;
    Intent intent;
    CafeReviewAdapter adapter;
    Button writeReviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_review);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        intent = getIntent();
        cafeSeq = intent.getLongExtra("seq", 0);
        Log.d("카페 seq", cafeSeq + "");
        recyclerView = findViewById(R.id.cafeReviewRv);
        writeReviewBtn = findViewById(R.id.writeCafeReviewBtn);
        serverAPI.getCafeInfoReviewList(cafeSeq).enqueue(new Callback<List<CafeInfoReview>>() {
            @Override
            public void onResponse(Call<List<CafeInfoReview>> call, Response<List<CafeInfoReview>> response) {
                if(response.isSuccessful()) {
                    adapter = new CafeReviewAdapter(response.body(), CafeReviewActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CafeReviewActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new MyItemDecorator());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CafeInfoReview>> call, Throwable t) {

            }
        });
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CafeReviewActivity.this, WriteCafeReviewActivity.class);
                intent.putExtra("seq", cafeSeq);
                startActivity(intent);
            }
        });
    }
}
