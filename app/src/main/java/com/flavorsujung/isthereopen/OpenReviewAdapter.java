package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class OpenReviewAdapter extends BaseAdapter {

    Activity context;
    int layout;
    List<OpenReview> openReviewList;

    public OpenReviewAdapter(Activity context, List<OpenReview> openReviewList) {
        this.context = context;
        layout = R.layout.open_review_item;
        this.openReviewList = openReviewList;
    }

    @Override
    public int getCount() {
        return openReviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return openReviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = View.inflate(context, layout, null);
        }
        TextView dateTv = view.findViewById(R.id.openReviewDateTv);
        TextView openTv = view.findViewById(R.id.openOpenReview);
        TextView closeTv = view.findViewById(R.id.closeOpenReview);
        TextView breakTv = view.findViewById(R.id.breakOpenReview);
        TextView userTv = view.findViewById(R.id.openReviewWriter);
        TextView wordTv = view.findViewById(R.id.postPosition);
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormat.setTimeZone(timeZone);
        dateTv.setText(dateFormat.format(openReviewList.get(i).date));
        if (openReviewList.get(i).openState.equals("OPEN")) {
            closeTv.setVisibility(View.GONE);
            breakTv.setVisibility(View.GONE);
            openTv.setVisibility(View.VISIBLE);
            wordTv.setText("을");
        }
        else if (openReviewList.get(i).openState.equals("CLOSE")) {
            openTv.setVisibility(View.GONE);
            breakTv.setVisibility(View.GONE);
            closeTv.setVisibility(View.VISIBLE);
            wordTv.setText("를");
        }
        else if (openReviewList.get(i).openState.equals("BREAK")) {
            closeTv.setVisibility(View.GONE);
            openTv.setVisibility(View.GONE);
            breakTv.setVisibility(View.VISIBLE);
            wordTv.setText("을");
        }
        userTv.setText(openReviewList.get(i).userName);
        return view;
    }
}
