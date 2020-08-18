package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import javax.net.ssl.HandshakeCompletedEvent;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        boolean nicknameExist = sharedPreferences.getBoolean("exist", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(!nicknameExist) { //닉네임 설정한 적이 없으면 닉네임 설정창으로
                    intent = new Intent(SplashActivity.this, SignUpActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 500);
    }
}
