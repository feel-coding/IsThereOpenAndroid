package com.flavorsujung.isthereopen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarReviewAdapter extends RecyclerView.Adapter<BarReviewViewHolder>  {

    private List<BarInfoReview> reviewList;
    private Context mContext;

    public BarReviewAdapter(List<BarInfoReview> reviewList, Context mContext) {
        this.reviewList = reviewList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BarReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_review_item, parent, false);
        return new BarReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarReviewViewHolder holder, int position) {
        String openStyle = reviewList.get(position).getOpenStyle();
        String price = reviewList.get(position).getPrice();
        String mainAlcohol = reviewList.get(position).getMainAlcohol();
        String cleanness = reviewList.get(position).getCleanness();
        String mood = reviewList.get(position).getMood();
        String toilet = reviewList.get(position).getToilet();
        String rate = reviewList.get(position).getRate();
        Long userSeq = reviewList.get(position).getUserSeq();
        holder.priceTv.setText(price);
        holder.rateTv.setText(rate);
        holder.toiletTv.setText(toilet);
        holder.cleannessTv.setText(cleanness);
        holder.userNameTv.setText(userSeq + "");
        holder.mainAlcoholTv.setText(mainAlcohol);
        holder.openStyleTv.setText(openStyle);
        holder.moodTv.setText(mood);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
