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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantFragment extends Fragment {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    List<Store> restaurantList = new ArrayList<>();
    List<Store> searchList = new ArrayList<>();
    List<Store> selectedList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;
    SearchView searchView;
    TextView toolbarTitleTv;
    Button stableBtn;
    Button cheapBtn;
    Button takeoutBtn;
    Button eatAloneBtn;
    Button fastBtn;
    Button cleanBtn;
    Map<String, Boolean> selected = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.restaurantRecyclerView);
        swipeRefreshLayout = v.findViewById(R.id.restaurantSwipe);
        searchView = v.findViewById(R.id.restaurantSearchView);
        toolbarTitleTv = v.findViewById(R.id.restaurantToolbarTitleTv);
        stableBtn = v.findViewById(R.id.restaurantStableBtn);
        cheapBtn = v.findViewById(R.id.restaurantCheapBtn);
        takeoutBtn = v.findViewById(R.id.restaurantTakeoutBtn);
        eatAloneBtn = v.findViewById(R.id.restaurantEatAloneBtn);
        fastBtn = v.findViewById(R.id.restaurantFastBtn);
        cleanBtn = v.findViewById(R.id.restaurantCleanBtn);
        selected.put("fast", false);
        selected.put("cheap", false);
        selected.put("takeout", false);
        selected.put("eatAlone", false);
        selected.put("clean", false);
        selected.put("stable", false);
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
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("클릭", "클릭됨");
                switch (view.getId()) {
                    case R.id.restaurantFastBtn:
                        selected.put("fast", !selected.get("fast"));
                        break;
                    case R.id.restaurantCheapBtn:
                        selected.put("cheap", !selected.get("cheap"));
                        break;
                    case R.id.restaurantTakeoutBtn:
                        selected.put("takeout", !selected.get("takeout"));
                        break;
                    case R.id.restaurantEatAloneBtn:
                        selected.put("eatAlone", !selected.get("eatAlone"));
                        break;
                    case R.id.restaurantCleanBtn:
                        selected.put("clean", !selected.get("clean"));
                        break;
                    case R.id.restaurantStableBtn:
                        selected.put("stable", !selected.get("stable"));
                        break;
                }
                boolean nothingFlag = true; // 아무것도 선택 안 됐다
                selectedList.clear();
                selectedList.addAll(restaurantList);
                if(selected.get("fast")) {
                    nothingFlag = false;
                    fastBtn.setBackgroundResource(R.drawable.full_red_round);
                    fastBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getRestaurantShortWaiting() == null || store.getRestaurantShortWaiting() == 0) {
                            selectedList.remove(store);
                        }
                    }

                }
                else {
                    fastBtn.setBackgroundResource(R.drawable.black_round_square);
                    fastBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("cheap")) {
                    nothingFlag = false;
                    cheapBtn.setBackgroundResource(R.drawable.full_red_round);
                    cheapBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getRestaurantCheap() == null || store.getRestaurantCheap() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    cheapBtn.setBackgroundResource(R.drawable.black_round_square);
                    cheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("takeout")) {
                    nothingFlag = false;
                    takeoutBtn.setBackgroundResource(R.drawable.full_red_round);
                    takeoutBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getRestaurantTakeout() == null || store.getRestaurantTakeout() == 0) {
                            selectedList.remove(store);
                        }
                    }

                }
                else {
                    takeoutBtn.setBackgroundResource(R.drawable.black_round_square);
                    takeoutBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("eatAlone")) {
                    nothingFlag = false;
                    eatAloneBtn.setBackgroundResource(R.drawable.full_red_round);
                    eatAloneBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getRestaurantEatAlone() == null || store.getRestaurantEatAlone() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    eatAloneBtn.setBackgroundResource(R.drawable.black_round_square);
                    eatAloneBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("clean")) {
                    nothingFlag = false;
                    cleanBtn.setBackgroundResource(R.drawable.full_red_round);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getRestaurantClean() == null || store.getRestaurantClean() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    cleanBtn.setBackgroundResource(R.drawable.black_round_square);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                if(selected.get("stable")) {
                    nothingFlag = false;
                    stableBtn.setBackgroundResource(R.drawable.full_red_round);
                    stableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getRestaurantStable() == null || store.getRestaurantStable() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    stableBtn.setBackgroundResource(R.drawable.black_round_square);
                    stableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                if(nothingFlag) {
                    adapter = new StoreAdapter(restaurantList, mContext);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    adapter = new StoreAdapter(selectedList, mContext);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        refreshRestaurantList();

        cleanBtn.setOnClickListener(onClickListener);
        fastBtn.setOnClickListener(onClickListener);
        takeoutBtn.setOnClickListener(onClickListener);
        eatAloneBtn.setOnClickListener(onClickListener);
        cheapBtn.setOnClickListener(onClickListener);
        stableBtn.setOnClickListener(onClickListener);

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
                        store.setRestaurantCheap(restaurant.getCheap());
                        store.setRestaurantClean(restaurant.getClean());
                        store.setRestaurantEatAlone(restaurant.getEatAlone());
                        store.setRestaurantShortWaiting(restaurant.getShortWaiting());
                        store.setRestaurantStable(restaurant.getStable());
                        store.setRestaurantTakeout(restaurant.getTakeout());
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
