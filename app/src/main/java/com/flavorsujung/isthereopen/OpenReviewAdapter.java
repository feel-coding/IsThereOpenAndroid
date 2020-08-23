package com.flavorsujung.isthereopen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class OpenReviewAdapter extends RecyclerView.Adapter<OpenReviewViewHolder> {
    private List<OpenReview> openReviewList;
    private Context mContext;
    private ServerAPI serverAPI;

    public OpenReviewAdapter(List<OpenReview> openReviewList, Context mContext) {
        this.openReviewList = openReviewList;
        this.mContext = mContext;
        serverAPI = RetrofitManager.getInstance().getServerAPI(mContext);
    }

    @NonNull
    @Override
    public OpenReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_review_item, parent, false);
        return new OpenReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpenReviewViewHolder holder, int position) {
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormat.setTimeZone(timeZone);
        holder.dateTv.setText(dateFormat.format(openReviewList.get(position).date));
        if (openReviewList.get(position).openState.equals("OPEN")) {
            holder.closeTv.setVisibility(View.GONE);
            holder.breakTv.setVisibility(View.GONE);
            holder.openTv.setVisibility(View.VISIBLE);
            holder.wordTv.setText("을");
        }
        else if (openReviewList.get(position).openState.equals("CLOSE")) {
            holder.openTv.setVisibility(View.GONE);
            holder.breakTv.setVisibility(View.GONE);
            holder.closeTv.setVisibility(View.VISIBLE);
            holder.wordTv.setText("를");
        }
        else if (openReviewList.get(position).openState.equals("BREAK")) {
            holder.closeTv.setVisibility(View.GONE);
            holder.openTv.setVisibility(View.GONE);
            holder.breakTv.setVisibility(View.VISIBLE);
            holder.wordTv.setText("을");
        }
        holder.userTv.setText(openReviewList.get(position).userName);
    }

    @Override
    public int getItemCount() {
        return openReviewList.size();
    }
}
