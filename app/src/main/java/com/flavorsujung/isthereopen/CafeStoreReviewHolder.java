package com.flavorsujung.isthereopen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CafeStoreReviewHolder extends RecyclerView.ViewHolder {
    TextView userID_tv;
    TextView rateStarImage_tv;
    TextView rateNumber_tv;
    TextView dateOfReview_tv;
    TextView price;
    TextView waiting_time;
    TextView open_style;
    TextView customer_num;
    TextView plug_num;
    TextView table_height;
    TextView lightness;
    TextView long_possibility;
    TextView text_review;


    public CafeStoreReviewHolder(@NonNull View itemView) {
        super(itemView);
        userID_tv = itemView.findViewById(R.id.userID_tv);
        rateStarImage_tv = itemView.findViewById(R.id.rateStarImage_tv);
        rateNumber_tv = itemView.findViewById(R.id.rateNumber_tv);
        dateOfReview_tv = itemView.findViewById(R.id.dateOfReview_tv);
        price = itemView.findViewById(R.id.price);
        waiting_time = itemView.findViewById(R.id.waiting_time);
        open_style = itemView.findViewById(R.id.open_style);
        customer_num = itemView.findViewById(R.id.customer_num);
        plug_num = itemView.findViewById(R.id.plug_num);
        table_height = itemView.findViewById(R.id.table_height);
        lightness = itemView.findViewById(R.id.lightness);
        long_possibility = itemView.findViewById(R.id.long_possibility);
        text_review = itemView.findViewById(R.id.text_review);
    }
}
