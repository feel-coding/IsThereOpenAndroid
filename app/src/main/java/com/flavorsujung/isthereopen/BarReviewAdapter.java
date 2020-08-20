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
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarReviewAdapter extends RecyclerView.Adapter<BarReviewViewHolder>  {

    private List<BarInfoReview> reviewList;
    private Context mContext;
    ServerAPI serverAPI;

    public BarReviewAdapter(List<BarInfoReview> reviewList, Context mContext) {
        this.reviewList = reviewList;
        this.mContext = mContext;
        serverAPI = RetrofitManager.getInstance().getServerAPI(mContext);
    }

    @NonNull
    @Override
    public BarReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_review_item, parent, false);
        return new BarReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarReviewViewHolder holder, int position) {
        serverAPI.getUser(reviewList.get(position).getUserSeq()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    holder.userNameTv.setText(response.body().getName());
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
        String mainAlcohol = reviewList.get(position).getMainAlcohol();
        if(mainAlcohol.equals("SOJU")) {
            mainAlcohol = "소주";
        }
        else if (mainAlcohol.equals("BEER")) {
            mainAlcohol = "맥주";
        }
        else if (mainAlcohol.equals("MAKGEOLLI")) {
            mainAlcohol = "막걸리";
        }
        else if (mainAlcohol.equals("WINE")) {
            mainAlcohol = "와인";
        }
        else if (mainAlcohol.equals("VODKA")) {
            mainAlcohol = "보드카";
        }
        String mood = reviewList.get(position).getMood();
        if(mood.equals("SILENT")) {
            mood = "조용한 편이에요";
        }
        else if (mood.equals("NORMAL")) {
            mood = "보통이에요";
        }
        else if (mood.equals("LOUD")) {
            mood = "시끌벅적한 편이에요";
        }
        String toilet = reviewList.get(position).getToilet();
        if(toilet.equals("SEPARATION")) {
            toilet = "남녀 분리되어 있어요";
        }
        else if (toilet.equals("ONE")) {
            toilet = "남녀 공용이에요";
        }
        Long userSeq = reviewList.get(position).getUserSeq();

        holder.priceTv.setText(price);
        holder.toiletTv.setText(toilet);
        holder.cleannessTv.setText(cleanness);
        holder.mainAlcoholTv.setText(mainAlcohol);
        holder.openStyleTv.setText(openStyle);
        holder.moodTv.setText(mood);
        holder.dateTv.setText(dateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
