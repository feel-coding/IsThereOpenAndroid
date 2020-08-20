package com.flavorsujung.isthereopen;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BarReviewViewHolder extends RecyclerView.ViewHolder {
    TextView openStyleTv;
    TextView userNameTv;
    TextView cleannessTv;
    TextView mainAlcoholTv;
    TextView toiletTv;
    TextView priceTv;
    TextView moodTv;
    TextView dateTv;
    ImageView oneStarIv;
    ImageView twoStarIv;
    ImageView threeStarIv;
    ImageView fourStarIv;
    ImageView fiveStarIv;
    public BarReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        oneStarIv = itemView.findViewById(R.id.barUserStarOne);
        twoStarIv = itemView.findViewById(R.id.barUserStarTwo);
        threeStarIv = itemView.findViewById(R.id.barUserStarThree);
        fourStarIv = itemView.findViewById(R.id.barUserStarFour);
        fiveStarIv = itemView.findViewById(R.id.barUserStarFive);
        openStyleTv = itemView.findViewById(R.id.barUserOpenStyle);
        userNameTv = itemView.findViewById(R.id.barUserTv);
        cleannessTv = itemView.findViewById(R.id.barUserCleanness);
        toiletTv = itemView.findViewById(R.id.barUserToilet);
        mainAlcoholTv = itemView.findViewById(R.id.barUserAlcohol);
        moodTv = itemView.findViewById(R.id.barUserMood);
        priceTv = itemView.findViewById(R.id.barUserPrice);
        dateTv = itemView.findViewById(R.id.barUserReviewDate);
    }
}
