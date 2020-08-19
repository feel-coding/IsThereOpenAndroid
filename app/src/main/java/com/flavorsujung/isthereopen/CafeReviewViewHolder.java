package com.flavorsujung.isthereopen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CafeReviewViewHolder extends RecyclerView.ViewHolder {
    TextView nicknameTv;
    TextView rateStarImage_tv;
    TextView rateTv;
    TextView dateOfReview_tv;
    TextView priceTv;
    TextView waitingTimeTv;
    TextView openStyleTv;
    TextView customerNumTv;
    TextView plugNumTv;
    TextView tableHeightTv;
    TextView lightnessTv;
    TextView stayLongTv;
    TextView text_review;


    public CafeReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        nicknameTv = itemView.findViewById(R.id.cafeUserTv);
        rateStarImage_tv = itemView.findViewById(R.id.rateStarImage_tv);
        rateTv = itemView.findViewById(R.id.cafeRate);
        dateOfReview_tv = itemView.findViewById(R.id.dateOfReview_tv);
        priceTv = itemView.findViewById(R.id.cafePrice);
        waitingTimeTv = itemView.findViewById(R.id.cafeWaitingTime);
        openStyleTv = itemView.findViewById(R.id.cafeOpenStyle);
        customerNumTv = itemView.findViewById(R.id.cafeCustomerNum);
        plugNumTv = itemView.findViewById(R.id.plugNum);
        tableHeightTv = itemView.findViewById(R.id.tableHeight);
        lightnessTv = itemView.findViewById(R.id.lightness);
        stayLongTv = itemView.findViewById(R.id.stayLong);
        text_review = itemView.findViewById(R.id.text_review);
    }
}
