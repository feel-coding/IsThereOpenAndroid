package sungshin.project.isthereopen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    Fragment homeFragment, restaurantFragment, cafeFragment, barFragment;
    private int mPageCount;

    public MainViewPagerAdapter(FragmentActivity fa, int pageCount) {
        super(fa);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                restaurantFragment = new RestaurantFragment();
                return restaurantFragment;
            case 2:
                cafeFragment = new CafeFragment();
                return cafeFragment;
            case 3:
                barFragment = new BarFragment();
                return barFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return mPageCount;
    }
}
