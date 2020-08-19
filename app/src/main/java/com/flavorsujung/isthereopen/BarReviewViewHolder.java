package com.flavorsujung.isthereopen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BarReviewViewHolder extends RecyclerView.ViewHolder {
    TextView rateTv;
    TextView openStyleTv;
    TextView userNameTv;
    TextView cleannessTv;
    TextView mainAlcoholTv;
    TextView toiletTv;
    TextView priceTv;
    TextView moodTv;
    TextView dateTv;
    public BarReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        rateTv = itemView.findViewById(R.id.barRate);
        openStyleTv = itemView.findViewById(R.id.barOpenStyle);
        userNameTv = itemView.findViewById(R.id.barUser);
        cleannessTv = itemView.findViewById(R.id.barCleanness);
        toiletTv = itemView.findViewById(R.id.toilet);
        mainAlcoholTv = itemView.findViewById(R.id.mainAlcohol);
        moodTv = itemView.findViewById(R.id.mood);
        priceTv = itemView.findViewById(R.id.barPrice);
        dateTv = itemView.findViewById(R.id.barReviewDate);
    }
}
