package com.flavorsujung.isthereopen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class RestaurantViewPagerAdapter extends FragmentStateAdapter {
    Fragment openReviewFragment, infoReviewFragment;
    private int mPageCount;

    public RestaurantViewPagerAdapter(@NonNull FragmentActivity activity, int mPageCount) {
        super(activity);
        this.mPageCount = mPageCount;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                infoReviewFragment = new RestaurantInfoReviewFragment();
                return infoReviewFragment;
            case 1:
                openReviewFragment = new OpenReviewFragment();
                return openReviewFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return mPageCount;
    }
}