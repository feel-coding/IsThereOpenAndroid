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
        serverAPI.getCafeList().enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                textView.setText(response.body().get(0).getName() + " " + response.body().get(0).getCurrentState());
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Log.d("실패원인", t.getMessage());
            }
        });
    }
}
