package com.flavorsujung.isthereopen;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantReviewViewHolder extends RecyclerView.ViewHolder {
    TextView nicknameTv;
    TextView openStyleTv;
    TextView priceTv;
    TextView takeoutTv;
    TextView eatAloneTv;
    TextView waitingTimeTv;
    TextView cleannessTv;
    TextView dateTv;
    ImageView oneStarIv;
    ImageView twoStarIv;
    ImageView threeStarIv;
    ImageView fourStarIv;
    ImageView fiveStarIv;


    public RestaurantReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        nicknameTv = itemView.findViewById(R.id.restaurantUserTv);
        oneStarIv = itemView.findViewById(R.id.restaurantUserStarOne);
        twoStarIv = itemView.findViewById(R.id.restaurantUserStarTwo);
        threeStarIv = itemView.findViewById(R.id.restaurantUserStarThree);
        fourStarIv = itemView.findViewById(R.id.restaurantUserStarFour);
        fiveStarIv = itemView.findViewById(R.id.restaurantUserStarFive);
        dateTv = itemView.findViewById(R.id.restaurantUserReviewDate);
        priceTv = itemView.findViewById(R.id.restaurantUserPrice);
        waitingTimeTv = itemView.findViewById(R.id.restaurantUserWaitingTime);
        openStyleTv = itemView.findViewById(R.id.restaurantUserOpenStyle);
        takeoutTv = itemView.findViewById(R.id.restaurantUserTakeout);
        cleannessTv = itemView.findViewById(R.id.restaurantUserCleanness);
        eatAloneTv = itemView.findViewById(R.id.userEatAlone);
    }
}
