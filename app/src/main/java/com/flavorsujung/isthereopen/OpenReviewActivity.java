package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenReviewActivity extends AppCompatActivity {

    ServerAPI serverAPI;
    Intent intent;
    String type;
    Long seq;
    TextView toolbarTitleTv;
    Toolbar toolbar;
    List<OpenReview> openReviewList = new ArrayList<>();
    ListView listView;
    OpenReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_review);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        toolbar = findViewById(R.id.openReviewToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        listView = findViewById(R.id.openReviewLv);
        adapter = new OpenReviewAdapter(this, openReviewList);
        listView.setAdapter(adapter);
        intent = getIntent();
        seq = intent.getLongExtra("seq", 0);
        type = intent.getStringExtra("type");
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        if(type.equals("cafe")) {
            Log.d("여기는 OpenReviewActivity", "타입은 " + type + ", seq는 " + seq);
            serverAPI.getCafeOpenReviewList(seq).enqueue(new Callback<List<CafeOpenReview>>() {
                @Override
                public void onResponse(Call<List<CafeOpenReview>> call, Response<List<CafeOpenReview>> response) {
                    if(response.isSuccessful()) {
                        Log.d("카페", "첫번째 성공");
                        for (CafeOpenReview openReview : response.body()) {
                            serverAPI.getUser(openReview.getUserSeq()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("카페", "두번째 성공");
                                        Date date = openReview.getCreatedAt();
                                        openReviewList.add(new OpenReview(date, response.body().getName(), openReview.getOpenState()));
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<CafeOpenReview>> call, Throwable t) {

                }
            });
        }
        else if (type.equals("restaurant")) {
            Log.d("여기는 OpenReviewActivity", "타입은 " + type + ", seq는 " + seq);
            serverAPI.getRestaurantOpenReviewList(seq).enqueue(new Callback<List<RestaurantOpenReview>>() {
                @Override
                public void onResponse(Call<List<RestaurantOpenReview>> call, Response<List<RestaurantOpenReview>> response) {
                    if(response.isSuccessful()) {
                        Log.d("카페", "첫번째 성공");
                        for (RestaurantOpenReview openReview : response.body()) {
                            serverAPI.getUser(openReview.getUserSeq()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("카페", "두번째 성공");
                                        Date date = openReview.getCreatedAt();
                                        openReviewList.add(new OpenReview(date, response.body().getName(), openReview.getOpenState()));
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<RestaurantOpenReview>> call, Throwable t) {

                }
            });
        }
        else if (type.equals("bar")) {
            Log.d("여기는 OpenReviewActivity", "타입은 " + type + ", seq는 " + seq);
            serverAPI.getBarOpenReviewList(seq).enqueue(new Callback<List<BarOpenReview>>() {
                @Override
                public void onResponse(Call<List<BarOpenReview>> call, Response<List<BarOpenReview>> response) {
                    if(response.isSuccessful()) {
                        Log.d("카페", "첫번째 성공");
                        for (BarOpenReview openReview : response.body()) {
                            serverAPI.getUser(openReview.getUserSeq()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("카페", "두번째 성공");
                                        Date date = openReview.getCreatedAt();
                                        openReviewList.add(new OpenReview(date, response.body().getName(), openReview.getOpenState()));
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<BarOpenReview>> call, Throwable t) {

                }
            });
        }
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
