package com.flavorsujung.isthereopen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class StoreAdapter extends RecyclerView.Adapter<MyViewHolder>  {
    private List<Store> storeList;
    private Context mContext;
    ServerAPI serverAPI;
    SharedPreferences sharedPreferences;
    Long userSeq;

    public StoreAdapter(List<Store> storeList, Context mContext) {
        this.storeList = storeList;
        this.mContext = mContext;
        serverAPI = RetrofitManager.getInstance().getServerAPI(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        sharedPreferences = mContext.getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        int type = storeList.get(position).type;
        Long seq = storeList.get(position).seq;
        String storePhotoUrl = storeList.get(position).photoUrl;
        Log.d("사진url", storePhotoUrl + ", 타입: " + type);
        String storeName = storeList.get(position).name;
        String openState = storeList.get(position).openState;
        Date latestUpdate = storeList.get(position).latestUpdate;
        String runningTime = storeList.get(position).runtime;
        double rate = storeList.get(position).avgRate;
        boolean isPatron = storeList.get(position).isPatron;
        Glide.with(mContext).load(storePhotoUrl).into(holder.storePhotoIv);
        holder.storeNameTv.setText(storeName);
        holder.openStateTv.setText(openState);
        if(latestUpdate != null) {
            holder.latestUpdateTv.setText(" (" + (latestUpdate.getMonth() + 1) + "/" + latestUpdate.getDate() + " " + latestUpdate.getHours() + ":" + latestUpdate.getMinutes() + " 기준)");
            Log.d("시간", storeName + " " + latestUpdate.toString());
        }
        else
            holder.latestUpdateTv.setText("");
        if(rate == -1.0) holder.rateTv.setText("등록된 평점 없음");
        else holder.rateTv.setText(rate + "");
        holder.runningTimeTv.setText(runningTime);
        if(isPatron) {
            holder.heartButton.setImageResource(R.drawable.ic_heart_red);
        }
        else {
            holder.heartButton.setImageResource(R.drawable.ic_heart);
        }
        holder.heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 0) { //카페
                    serverAPI.getPatronCafeList(userSeq).enqueue(new Callback<List<PatronCafe>>() {
                        @Override
                        public void onResponse(Call<List<PatronCafe>> call, Response<List<PatronCafe>> response) {
                            boolean isAlreadyPatron = false;
                            for(PatronCafe patronCafe : response.body()) {
                                if(patronCafe.getCafeSeq().equals(seq)) {
                                    isAlreadyPatron = true;
                                    break;
                                }
                            }
                            if(isAlreadyPatron) {
                                serverAPI.deletePatronCafe(userSeq, seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            holder.heartButton.setImageResource(R.drawable.ic_heart);
                                            Toast.makeText(mContext, "단골 가게에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(mContext, "네트워크 연결상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                serverAPI.putPatronCafe(userSeq, seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            holder.heartButton.setImageResource(R.drawable.ic_heart_red);
                                            Toast.makeText(mContext, "단골 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PatronCafe>> call, Throwable t) {

                        }
                    });
                }
                else if (type == 1) { //음식점
                    serverAPI.getPatronRestaurantList(userSeq).enqueue(new Callback<List<PatronRestaurant>>() {
                        @Override
                        public void onResponse(Call<List<PatronRestaurant>> call, Response<List<PatronRestaurant>> response) {
                            boolean isAlreadyPatron = false;
                            for(PatronRestaurant patronRestaurant : response.body()) {
                                if(patronRestaurant.getRestaurantSeq().equals(seq)) {
                                    isAlreadyPatron = true;
                                    break;
                                }
                            }
                            if(isAlreadyPatron) {
                                serverAPI.deletePatronRestaurant(userSeq, seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            holder.heartButton.setImageResource(R.drawable.ic_heart);
                                            Toast.makeText(mContext, "단골 가게에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(mContext, "네트워크 연결상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                serverAPI.putPatronRestaurant(userSeq, seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            holder.heartButton.setImageResource(R.drawable.ic_heart_red);
                                            Toast.makeText(mContext, "단골 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PatronRestaurant>> call, Throwable t) {

                        }
                    });
                }
                else if (type == 2) { //술집
                    serverAPI.getPatronBarList(userSeq).enqueue(new Callback<List<PatronBar>>() {
                        @Override
                        public void onResponse(Call<List<PatronBar>> call, Response<List<PatronBar>> response) {
                            boolean isAlreadyPatron = false;
                            for(PatronBar patronBar : response.body()) {
                                if(patronBar.getBarSeq().equals(seq)) {
                                    isAlreadyPatron = true;
                                    break;
                                }
                            }
                            if(isAlreadyPatron) {
                                serverAPI.deletePatronBar(userSeq, seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            holder.heartButton.setImageResource(R.drawable.ic_heart);
                                            Toast.makeText(mContext, "단골 가게에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(mContext, "네트워크 연결상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                serverAPI.putPatronBar(userSeq, seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()) {
                                            holder.heartButton.setImageResource(R.drawable.ic_heart_red);
                                            Toast.makeText(mContext, "단골 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PatronBar>> call, Throwable t) {

                        }
                    });
                }
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
