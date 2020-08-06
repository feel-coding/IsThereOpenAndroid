package com.flavorsujung.isthereopen;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView storePhotoIv;
    TextView storeNameTv;
    TextView openStateTv;
    TextView runningTimeTv;
    TextView rateTv;
    TextView latestUpdateTv;
    ImageView heartButton;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        storePhotoIv = itemView.findViewById(R.id.storeLogo);
        storeNameTv = itemView.findViewById(R.id.storeName);
        openStateTv = itemView.findViewById(R.id.openState);
        runningTimeTv = itemView.findViewById(R.id.runningTime);
        rateTv = itemView.findViewById(R.id.rate);
        latestUpdateTv = itemView.findViewById(R.id.latestUpdate);
        heartButton = itemView.findViewById(R.id.addPatron);
        storePhotoIv.setBackground(new ShapeDrawable(new OvalShape()));
        storePhotoIv.setClipToOutline(true);
    }
}
