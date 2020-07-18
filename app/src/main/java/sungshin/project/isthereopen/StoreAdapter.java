package sungshin.project.isthereopen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<MyViewHolder>  {
    private List<Store> storeList;
    private Context mContext;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String storeName = storeList.get(position).name;
        String openState = storeList.get(position).openState;
        String latestUpdate = storeList.get(position).latestUpdate;
        String runningTime = storeList.get(position).runtime;
        double rate = storeList.get(position).rate;
        holder.storeNameTv.setText(storeName);
        holder.openStateTv.setText(openState);
        holder.latestUpdateTv.setText(latestUpdate.toString());
        holder.rateTv.setText(rate + "");
        holder.runningTimeTv.setText(runningTime);

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }
}
