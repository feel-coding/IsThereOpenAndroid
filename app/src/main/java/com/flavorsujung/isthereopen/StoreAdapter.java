package com.flavorsujung.isthereopen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<MyViewHolder>  {
    private List<Store> storeList;
    private Context mContext;
    boolean isPatron = false;

    public StoreAdapter(List<Store> storeList, Context mContext) {
        this.storeList = storeList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        int type = storeList.get(position).type;
        int seq = storeList.get(position).seq;
        String storePhotoUrl = storeList.get(position).photoUrl;
        String storeName = storeList.get(position).name;
        String openState = storeList.get(position).openState;
        Date latestUpdate = storeList.get(position).latestUpdate;
        String runningTime = storeList.get(position).runtime;
        double rate = storeList.get(position).rate;
        Glide.with(mContext).load(storePhotoUrl).into(holder.storePhotoIv);
        holder.storeNameTv.setText(storeName);
        holder.openStateTv.setText(openState);
        if(latestUpdate != null)
            holder.latestUpdateTv.setText(" ("+latestUpdate.getHours() + "시 " + latestUpdate.getMinutes() + "분 기준)");
        else
            holder.latestUpdateTv.setText("");
        if(rate == -1.0) holder.rateTv.setText("등록된 평점 없음");
        else holder.rateTv.setText(rate + "");
        holder.runningTimeTv.setText(runningTime);
        holder.heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPatron)
                    holder.heartButton.setImageResource(R.drawable.ic_heart_red);
                else
                    holder.heartButton.setImageResource(R.drawable.ic_heart);
                isPatron = !isPatron;
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (type) {
                    case 0:
                        intent = new Intent(mContext, CafeActivity.class);
                        break;
                    case 1:
                        intent = new Intent(mContext, RestaurantActivity.class);
                        break;
                    default:
                        intent = new Intent(mContext, BarActivity.class);
                }
                intent.putExtra("seq", seq);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}
