package com.flavorsujung.isthereopen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CafeOpenReviewAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<CafeOpenReview> array;

    public CafeOpenReviewAdapter(Context context, ArrayList<CafeOpenReview> data) {
        mContext = context;
        array = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CafeOpenReview getItem(int position) {
        return array.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.open_review_item, null);

        EditText userID_tv1 = (EditText)view.findViewById(R.id.userID_tv1);
        TextView userID_tv2 = (TextView)view.findViewById(R.id.userID_tv2);
        TextView id_tv = (TextView)view.findViewById(R.id.id_tv);
        TextView id_tv2 = (TextView)view.findViewById(R.id.id_tv2);
        EditText ㅐopenReview_OPEN_tv = (EditText)view.findViewById(R.id.ㅐopenReview_OPEN_tv);
        TextView dateOfReview_tv1 = (TextView)view.findViewById(R.id.dateOfReview_tv1);
        TextView dateOfReview_tv2 = (TextView)view.findViewById(R.id.dateOfReview_tv2);


        //userID_tv1.setText(array.get(position).getUserName());
        ㅐopenReview_OPEN_tv.setText(array.get(position).getOpenState());
        //dateOfReview_tv1.setText(array.get(position).getUpdatedAt());

        return view;
    }

}
