package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameEdit;
    ServerAPI serverAPI;
    Button nameButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userNameEdit = findViewById(R.id.nicknameEdit);
        nameButton = findViewById(R.id.nameButton);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNameEdit.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "최소 한 글자는 입력해야합니다.", Toast.LENGTH_SHORT).show();
                }
                serverAPI.putUser(userNameEdit.getText().toString()).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if(response.isSuccessful()) {
                            if(response.body() != -1L) { //사용가능
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("exist", true); //1은 닉네임 있다는거
                                editor.putLong("userSeq", response.body());
                                editor.putString("name", userNameEdit.getText().toString());
                                editor.apply();
                                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else  { //이미 존재
                                Toast.makeText(SignUpActivity.this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
            }
        });
    }
}
