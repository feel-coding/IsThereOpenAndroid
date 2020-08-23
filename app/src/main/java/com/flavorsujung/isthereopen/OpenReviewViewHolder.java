package com.flavorsujung.isthereopen;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OpenReviewViewHolder extends RecyclerView.ViewHolder {

    TextView dateTv;
    TextView openTv;
    TextView closeTv;
    TextView breakTv;
    TextView userTv;
    TextView wordTv;
    public OpenReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTv = itemView.findViewById(R.id.openReviewDateTv);
        openTv = itemView.findViewById(R.id.openOpenReview);
        closeTv = itemView.findViewById(R.id.closeOpenReview);
        breakTv = itemView.findViewById(R.id.breakOpenReview);
        userTv = itemView.findViewById(R.id.openReviewWriter);
        wordTv = itemView.findViewById(R.id.postPosition);
    }
}
