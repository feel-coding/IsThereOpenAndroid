package com.flavorsujung.isthereopen;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CafeStoreReviewActivity extends AppCompatActivity {
    ArrayList<CafeStoreReview> reviewStoreDataList;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.store_review);
            ListView listView = (ListView) findViewById(R.id.listView);
            final CafeStoreReviewAdapter myAdapter = new CafeStoreReviewAdapter(reviewStoreDataList, this);
            listView.setAdapter((ListAdapter) myAdapter);
        }


    public void InitializeData()
    {
        reviewStoreDataList = new ArrayList<CafeStoreReview>();



    }

}
