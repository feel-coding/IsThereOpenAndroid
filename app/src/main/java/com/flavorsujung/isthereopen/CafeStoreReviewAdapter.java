package com.flavorsujung.isthereopen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class CafeStoreReviewAdapter extends RecyclerView.Adapter<CafeStoreReviewHolder> {
    private List<CafeStoreReview> cafeStoreReviewList;
    private Context mContext;

    public CafeStoreReviewAdapter(List<CafeStoreReview> cafeStoreReviewList, Context mContext) {
        this.cafeStoreReviewList = cafeStoreReviewList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CafeStoreReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cafe_store_review_item, parent, false);
        return new CafeStoreReviewHolder(view);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos =position;
        final Context context = parent.getContext();

        //store_review_item 레이아웃을 inflate하여 convertView 참조획득
        if(convertView == null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate((R.layout.cafe_store_review_item, parent, false));
        }
        //화면에 표시될 View(Layout이 inflate된) 으로부터 위젯에 대한 참조 획득

        String userName = cafeStoreReviewList.get(position).getUserName();
        Double rate = cafeStoreReviewList.get(position).getRate();
        Date date = cafeStoreReviewList.get(position).getDate();
        String waiting_time = cafeStoreReviewList.get(position).getWaiting_time();
        String price = cafeStoreReviewList.get(position).getPrice();
        String customer_num = cafeStoreReviewList.get(position).getCustomer_num();
        String plug_num = cafeStoreReviewList.get(position).getPlug_num();
        String table_height = cafeStoreReviewList.get(position).getTable_height();
        String lightness = cafeStoreReviewList.get(position).getLightness();
        String long_possibility = cafeStoreReviewList.get(position).getLong_possibility();
        String open_style = cafeStoreReviewList.get(position).getOpen_style();

        CafeStoreReview listViewItem = cafeStoreReviewList.get(position);

        //아이템 내 각 위젯에 데이터 반영
        userName
        rateStarImage_tv.setText(rate + "");
        holder.rateNumber_tv.setText(rate + "");
        holder.dateOfReview_tv.setText(date + "");
        holder.waiting_time.setText(waiting_time);
        holder.price.setText(price);
        holder.customer_num.setText(customer_num);
        holder.plug_num.setText(plug_num);
        holder.table_height.setText(table_height);
        holder.lightness.setText(lightness);
        holder.long_possibility.setText(long_possibility);
    }

    @Override
    public void onBindViewHolder(@NonNull final CafeStoreReviewHolder holder, int position) {
        String userName = cafeStoreReviewList.get(position).getUserName();
        Double rate = cafeStoreReviewList.get(position).getRate();
        Date date = cafeStoreReviewList.get(position).getDate();
        String waiting_time = cafeStoreReviewList.get(position).getWaiting_time();
        String price = cafeStoreReviewList.get(position).getPrice();
        String customer_num = cafeStoreReviewList.get(position).getCustomer_num();
        String plug_num = cafeStoreReviewList.get(position).getPlug_num();
        String table_height = cafeStoreReviewList.get(position).getTable_height();
        String lightness = cafeStoreReviewList.get(position).getLightness();
        String long_possibility = cafeStoreReviewList.get(position).getLong_possibility();
        String open_style = cafeStoreReviewList.get(position).getOpen_style();


        holder.userID_tv.setText(userName);
        holder.rateStarImage_tv.setText(rate + "");
        holder.rateNumber_tv.setText(rate + "");
        holder.dateOfReview_tv.setText(date + "");
        holder.waiting_time.setText(waiting_time);
        holder.price.setText(price);
        holder.customer_num.setText(customer_num);
        holder.plug_num.setText(plug_num);
        holder.table_height.setText(table_height);
        holder.lightness.setText(lightness);
        holder.long_possibility.setText(long_possibility);

    }

    @Override
    public int getItemCount() {
        return cafeStoreReviewList.size();
    }
}
