package com.flavorsujung.isthereopen;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    Fragment openReviewFragment, infoReviewFragment;
    private int mPageCount;

    public ViewPagerAdapter(@NonNull FragmentActivity activity,int mPageCount) {
        super(activity);
        this.mPageCount = mPageCount;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                openReviewFragment = new OpenReviewFragment();
                return openReviewFragment;
            case 1:
                infoReviewFragment = new CafeInfoReviewFragment();
                return infoReviewFragment;
            default:
                return null;
        }

    }


    @Override
    public int getItemCount() {
        return mPageCount;
    }
}
