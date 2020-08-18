package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerTestActivity extends AppCompatActivity {

    ServerAPI serverAPI;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_test);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        textView = findViewById(R.id.serverTestTv);
        serverAPI.putCafe("카페1", "성북구", "오전 10시 ~ 오후 8시", "0101234", "url").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Log.d("서버통신", "성공");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("실패원인", t.getMessage());
            }
        });
        serverAPI.getCafeCurrentState(1L).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    textView.setText(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
