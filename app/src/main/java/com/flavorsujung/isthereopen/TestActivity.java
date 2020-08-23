package com.flavorsujung.isthereopen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class TestActivity extends AppCompatActivity {
    Button button;
    ChangeOpenStateDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = findViewById(R.id.dialogBtn);
//        dialog = new ChangeOpenStateDialog(TestActivity.this, positiveListener, negativeListener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
    View.OnClickListener positiveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(TestActivity.this, "예", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };
    View.OnClickListener negativeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(TestActivity.this, "아니요", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    };
}
