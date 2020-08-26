package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantFragment extends Fragment {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    List<Store> restaurantList = new ArrayList<>();
    List<Store> searchList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;
    SearchView searchView;
    TextView toolbarTitleTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.restaurantRecyclerView);
        swipeRefreshLayout = v.findViewById(R.id.restaurantSwipe);
        searchView = v.findViewById(R.id.restaurantSearchView);
        toolbarTitleTv = v.findViewById(R.id.restaurantToolbarTitleTv);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbarTitleTv.setText("");
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbarTitleTv.setText("술집 열었나?");
                adapter = new StoreAdapter(restaurantList, mContext);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d("쿼리서브밋", query);
//                for (Store store : cafeList) {
//                    if (store.getName().contains(query)) {
//                        searchList.add(store);
//                    }
//                }
//                adapter = new StoreAdapter(searchList, mContext);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList.clear();
                for (Store store : restaurantList) {
                    if (store.getName().contains(newText)) {
                        searchList.add(store);
                    }
                }
                adapter = new StoreAdapter(searchList, mContext);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRestaurantList();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });

        refreshRestaurantList();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new StoreAdapter(restaurantList, mContext);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }



    void refreshRestaurantList() {
        serverAPI.getRestaurantList().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful()) {
                    Log.d("서버", "성공");
                    restaurantList.clear();
                    for (Restaurant restaurant : response.body()) {
                        String openState = restaurant.getCurrentState();
                        if (openState.equals("UNKNOWN"))
                            openState = "등록된 오픈 정보 없음";
                        Date date;
                        if (restaurant.getLastUpdate() == null)
                            date = null;
                        else date = restaurant.getLastUpdate();
                        Log.d("서버", restaurant.getName());
                        Store store = new Store();
                        store.setType(1);
                        store.setSeq(restaurant.getSeq());
                        store.setPhotoUrl(restaurant.getPhotoURL());
                        store.setName(restaurant.getName());
                        store.setOpenState(openState);
                        store.setRuntime(restaurant.getRunningTime());
                        if(restaurant.getAvgRate() != null)
                            store.setAvgRate(restaurant.getAvgRate());
                        else store.setAvgRate(-1.0);
                        restaurantList.add(store);
                    }
                    Log.d("서버", restaurantList.toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("서버", t.getMessage());
            }
        });
    }
}
