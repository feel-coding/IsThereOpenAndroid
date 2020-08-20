package com.flavorsujung.isthereopen;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CafeReviewViewHolder extends RecyclerView.ViewHolder {
    TextView nicknameTv;
    TextView dateTv;
    TextView priceTv;
    TextView waitingTimeTv;
    TextView openStyleTv;
    TextView customerNumTv;
    TextView plugNumTv;
    TextView tableHeightTv;
    TextView lightnessTv;
    TextView stayLongTv;
    ImageView oneStarIv;
    ImageView twoStarIv;
    ImageView threeStarIv;
    ImageView fourStarIv;
    ImageView fiveStarIv;


    public CafeReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        nicknameTv = itemView.findViewById(R.id.cafeUserTv);
        oneStarIv = itemView.findViewById(R.id.cafeUserStarOne);
        twoStarIv = itemView.findViewById(R.id.cafeUserStarTwo);
        threeStarIv = itemView.findViewById(R.id.cafeUserStarThree);
        fourStarIv = itemView.findViewById(R.id.cafeUserStarFour);
        fiveStarIv = itemView.findViewById(R.id.cafeUserStarFive);
        dateTv = itemView.findViewById(R.id.cafeUserReviewDate);
        priceTv = itemView.findViewById(R.id.cafePrice);
        waitingTimeTv = itemView.findViewById(R.id.cafeWaitingTime);
        openStyleTv = itemView.findViewById(R.id.cafeOpenStyle);
        customerNumTv = itemView.findViewById(R.id.cafeCustomerNum);
        plugNumTv = itemView.findViewById(R.id.plugNum);
        tableHeightTv = itemView.findViewById(R.id.tableHeight);
        lightnessTv = itemView.findViewById(R.id.lightness);
        stayLongTv = itemView.findViewById(R.id.stayLong);
    }
}
