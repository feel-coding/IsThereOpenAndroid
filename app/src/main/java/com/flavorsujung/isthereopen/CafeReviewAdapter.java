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

public class CafeReviewAdapter extends RecyclerView.Adapter<CafeReviewViewHolder>   {
    private List<CafeInfoReview> reviewList;
    private Context mContext;
    ServerAPI serverAPI;


    public CafeReviewAdapter(List<CafeInfoReview> reviewList, Context mContext) {
        this.reviewList = reviewList;
        this.mContext = mContext;
        serverAPI = RetrofitManager.getInstance().getServerAPI(mContext);
    }

    @NonNull
    @Override
    public CafeReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_review_item, parent, false);
        return new CafeReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeReviewViewHolder holder, int position) {
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
        Date date = reviewList.get(position).getCreatedAt();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormat.setTimeZone(timeZone);
        String rate = reviewList.get(position).getRate();
        if(rate.equals("WORST")) {
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_border_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_border_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_border_red);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_border_red);
        }
        else if (rate.equals("BAD")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_border_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_border_red);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_border_red);
        }
        else if (rate.equals("SOSO")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_border_red);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_border_red);
        }
        else if (rate.equals("GOOD")){
            holder.oneStarIv.setImageResource(R.drawable.ic_star_red);
            holder.twoStarIv.setImageResource(R.drawable.ic_star_red);
            holder.threeStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fourStarIv.setImageResource(R.drawable.ic_star_red);
            holder.fiveStarIv.setImageResource(R.drawable.ic_star_border_red);
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
        String customerNum = reviewList.get(position).getCustomerNum();
        if(customerNum.equals("UNCROWDED")) {
            customerNum = "적은 편이에요";
        }
        else if(customerNum.equals("NORMAL")) {
            customerNum = "보통이에요";
        }
        else if(customerNum.equals("CROWDED")) {
            customerNum = "많은 편이에요";
        }
        String lightness = reviewList.get(position).getLightness();
        if(lightness.equals("DARK")) {
            lightness = "어두운 편이에요";
        }
        else if(lightness.equals("NORMAL")) {
            lightness = "보통이에요";
        }
        else if(lightness.equals("LIGHT")) {
            lightness = "밝은 편이에요";
        }
        String plugNum = reviewList.get(position).getPlugNum();
        if(plugNum.equals("LITTLE")) {
            plugNum = "적은 편이에요";
        }
        else if(plugNum.equals("NORMAL")) {
            plugNum = "보통이에요";
        }
        else if(plugNum.equals("MANY")) {
            plugNum = "많은 편이에요";
        }
        else if(plugNum.equals("NOTABLE")) {
            plugNum = "테이블 없는 가게예요";
        }
        String stayLong = reviewList.get(position).getStayLong();
        if(stayLong.equals("POSSIBLE")) {
            stayLong = "가능해요";
        }
        else if(stayLong.equals("NORMAL")) {
            stayLong = "보통이에요";
        }
        else if(stayLong.equals("UNCOMFORTABLE")) {
            stayLong = "약간 눈치보여요";
        }
        else if(stayLong.equals("TAKEOUT")) {
            stayLong = "테이크아웃 카페예요";
        }
        String tableHeight = reviewList.get(position).getTableHeight();
        if(tableHeight.equals("LOW")) {
            tableHeight = "낮은 편이에요";
        }
        else if (tableHeight.equals("NORMAL")) {
            tableHeight = "보통이에요";
        }
        else if (tableHeight.equals("HIGH")) {
            tableHeight = "높은 편이에요";
        }
        else if (tableHeight.equals("NOTABLE")) {
            tableHeight = "테이블이 없는 카페예요";
        }
        Long userSeq = reviewList.get(position).getUserSeq();

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
        holder.waitingTimeTv.setText(waitingTime);
        holder.customerNumTv.setText(customerNum);
        holder.lightnessTv.setText(lightness);
        holder.plugNumTv.setText(plugNum);
        holder.priceTv.setText(price);
        holder.openStyleTv.setText(openStyle);
        holder.tableHeightTv.setText(tableHeight);
        holder.stayLongTv.setText(stayLong);
        holder.dateTv.setText(dateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
