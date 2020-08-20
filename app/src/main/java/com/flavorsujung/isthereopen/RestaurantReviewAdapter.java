package com.flavorsujung.isthereopen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantReviewAdapter extends RecyclerView.Adapter<RestaurantReviewViewHolder>   {
    private List<RestaurantInfoReview> reviewList;
    private Context mContext;
    ServerAPI serverAPI;

    public RestaurantReviewAdapter(List<RestaurantInfoReview> reviewList, Context mContext) {
        this.reviewList = reviewList;
        this.mContext = mContext;
        serverAPI = RetrofitManager.getInstance().getServerAPI(mContext);
    }

    @NonNull
    @Override
    public RestaurantReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_review_item, parent, false);
        return new RestaurantReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantReviewViewHolder holder, int position) {
        serverAPI.getUser(reviewList.get(position).getUserSeq()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    holder.nicknameTv.setText(response.body().getName());
                    Log.d("이름", response.body().getName());
                }
                else {
                    Log.d("이름", "실패");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("이름", t.getMessage());
            }
        });
        String rate = reviewList.get(position).getRate();
        if(rate.equals("WORST")) {
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_gray);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_gray);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_gray);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_gray);
        }
        else if (rate.equals("BAD")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_gray);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_gray);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_gray);
        }
        else if (rate.equals("SOSO")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_gray);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_gray);
        }
        else if (rate.equals("GOOD")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_gray);
        }
        else if (rate.equals("BEST")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_red);
        }
        String openStyle = reviewList.get(position).getOpenStyle();
        if(openStyle.equals("STABLE")) {
            openStyle = "잘 지키는 편이에요";
        }
        else if (openStyle.equals("NORMAL")) {
            openStyle = "보통으로 지켜요";
        }
        else if (openStyle.equals("UNSTABLE")) {
            openStyle = "사장님 마음대로 열어요";
        }
        String price = reviewList.get(position).getPrice();
        if (price.equals("CHEAP")) {
            price = "싼 편이에요";
        }
        else if (price.equals("NORMAL")){
            price = "보통이에요";
        }
        else if (price.equals("EXPENSIVE")) {
            price = "비싼 편이에요";
        }
        String takeout = reviewList.get(position).getTakeOut();
        if(takeout.equals("UNABLE")) {
            takeout = "불가능해요";
        }
        else if (takeout.equals("POSSIBLE")) {
            takeout = "가능해요";
        }
        String eatAlone = reviewList.get(position).getEatAlone();
        if(eatAlone.equals("POSSIBLE")) {
            eatAlone = "완전 가능해요";
        }
        else if (eatAlone.equals("UNCOMFORTABLE")) {
            eatAlone = "사람 많은 시간 아니면 가능해요";
        }
        else if (eatAlone.equals("UNABLE")) {
            eatAlone = "불가능해요";
        }
        String waitingTime = reviewList.get(position).getWaitingTime();
        if(waitingTime.equals("SHORT")) {
            waitingTime = "금방 나오는 편이에요";
        }
        else if (waitingTime.equals("NORMAL")) {
            waitingTime = "보통이에요";
        }
        else if (waitingTime.equals("LONG")) {
            waitingTime = "오래 걸리는 편이에요";
        }
        String cleanness = reviewList.get(position).getCleanness();
        if (cleanness.equals("CLEAN")) {
            cleanness = "청결해요";
        }
        else if (cleanness.equals("NORMAL")) {
            cleanness = "보통이에요";
        }
        else if (cleanness.equals("DIRTY")) {
            cleanness = "청결하지 않아요";
        }
        Date date = reviewList.get(position).getCreatedAt();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormat.setTimeZone(timeZone);
        holder.dateTv.setText(dateFormat.format(date));
        holder.openStyleTv.setText(openStyle);
        holder.takeoutTv.setText(takeout);
        holder.eatAloneTv.setText(eatAlone);
        holder.priceTv.setText(price);
        holder.waitingTimeTv.setText(waitingTime);
        holder.cleannessTv.setText(cleanness);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
