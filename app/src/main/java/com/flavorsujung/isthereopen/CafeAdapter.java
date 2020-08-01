package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CafeAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Cafe> cafeList;
    int layout;

    public CafeAdapter(Activity context, ArrayList<Cafe> cafeList, int layout) {
        this.context = context;
        this.cafeList = cafeList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
