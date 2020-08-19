package com.flavorsujung.isthereopen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CafeReviewAdapter extends RecyclerView.Adapter<CafeReviewViewHolder>   {
    private List<CafeInfoReview> reviewList;
    private Context mContext;

    public CafeReviewAdapter(List<CafeInfoReview> reviewList, Context mContext) {
        this.reviewList = reviewList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CafeReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_review_item, parent, false);
        return new CafeReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeReviewViewHolder holder, int position) {
        String openStyle = reviewList.get(position).getOpenStyle();
        String price = reviewList.get(position).getOpenStyle();
        String customerNum = reviewList.get(position).getCustomerNum();
        String lightness = reviewList.get(position).getLightness();
        String plugNum = reviewList.get(position).getPlugNum();
        String rate = reviewList.get(position).getRate();
        String stayLong = reviewList.get(position).getStayLong();
        String tableHeight = reviewList.get(position).getTableHeight();
        Long userSeq = reviewList.get(position).getUserSeq();
        holder.customerNumTv.setText(customerNum);
        holder.lightnessTv.setText(lightness);
        holder.plugNumTv.setText(plugNum);
        holder.priceTv.setText(price);
        holder.openStyleTv.setText(openStyle);
        holder.tableHeightTv.setText(tableHeight);
        holder.rateTv.setText(rate);
        holder.stayLongTv.setText(stayLong);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
