package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    ArrayList<Store> storeList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    Button restaurantBtn;
    Button cafeBtn;
    Button barBtn;
    Button allBtn;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;
    int selectedStoreType = 3; //0 카페, 1 식당, 2 술집, 3 전부, 4 단골
    SharedPreferences sharedPreferences;
    Long userSeq;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        sharedPreferences = activity.getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        recyclerView = v.findViewById(R.id.storeRecyclerView);
        restaurantBtn = v.findViewById(R.id.restaurantBtn);
        cafeBtn = v.findViewById(R.id.cafeBtn);
        barBtn = v.findViewById(R.id.barBtn);
        allBtn = v.findViewById(R.id.allBtn);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (selectedStoreType == 0) {
                    storeList.clear();
                    refreshCafeList();
                } else if (selectedStoreType == 1) {
                    storeList.clear();
                    refreshRestaurantList();
                }
                else if (selectedStoreType == 2){
                    storeList.clear();
                    refreshBarList();
                }
                else {
                    storeList.clear();
                    refreshBarList();
                    refreshCafeList();
                    refreshRestaurantList();
                }
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        restaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedStoreType = 1;
                storeList.clear();
                refreshRestaurantList();
            }
        });
        cafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedStoreType = 0;
                storeList.clear();
                refreshCafeList();
            }
        });
        barBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedStoreType = 2;
                storeList.clear();
                refreshBarList();
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedStoreType = 3;
                storeList.clear();
                refreshCafeList();
                refreshRestaurantList();
                refreshRestaurantList();
            }
        });

        /*cafeList.add(new Store("본크레페", "open", "20:11 기준", "오후 2시~오후 8시", 3.8));
        cafeList.add(new Store("카페온더플랜", "open", "20:11 기준", "오전 10시~오전 5시", 4.5));
        cafeList.add(new Store("최고당 돈까스", "open", "17:05 기준", "오후 10시~오후 8시", 3.8));
        cafeList.add(new Store("카페비", "open", "21:00 기준", "오전 10시~오후 12시", 4.8));
        cafeList.add(new Store("도시포차", "open", "23:00 기준", "오후 4시~오전 2시", 4.1));
        cafeList.add(new Store("홀슈", "close", "20:38 기준", "오후 1시~오후 8시", 3.8));
        cafeList.add(new Store("봉봉", "open", "20:53 기준", "오전 10시~오후 8시", 3.8));
        cafeList.add(new Store("아리랑노점", "close", "20:07 기준", "오전 9시~오후 8시", 3.8));*/
        storeList.clear();
        refreshCafeList();
        refreshRestaurantList();
        refreshBarList();
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new StoreAdapter(storeList, mContext);
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
                        serverAPI.getPatronRestaurantList(userSeq).enqueue(new Callback<List<PatronRestaurant>>() {
                            @Override
                            public void onResponse(Call<List<PatronRestaurant>> call, Response<List<PatronRestaurant>> response) {
                                if (response.isSuccessful()) {
                                    for (PatronRestaurant patronRestaurant : response.body()) {
                                        if(patronRestaurant.getRestaurantSeq().equals(restaurant.getSeq())) {
                                            store.setPatron(true);
                                        }
                                        storeList.add(store);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PatronRestaurant>> call, Throwable t) {

                            }
                        });
                        storeList.add(store);
                    }
                    Log.d("서버", storeList.toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("서버", t.getMessage());
            }
        });
    }

    void refreshCafeList() {
        serverAPI.getCafeList().enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                if (response.isSuccessful()) {
                    Log.d("카페이름", response.body().get(0).getName());
                    for (Cafe cafe : response.body()) {
                        String openState = cafe.getCurrentState();
                        if (openState.equals("UNKNOWN"))
                            openState = "등록된 오픈 정보 없음";
                            Date date;
                        if (cafe.getLastUpdate() == null)
                            date = null;
                        else date = cafe.getLastUpdate();
                        Log.d("서버", cafe.getName());
                        Store store = new Store();
                        store.setType(0);
                        store.setSeq(cafe.getSeq());
                        store.setPhotoUrl(cafe.getPhotoURL());
                        store.setName(cafe.getName());
                        store.setOpenState(openState);
                        store.setRuntime(cafe.getRunningTime());
                        if(cafe.getAvgRate() != null)
                            store.setAvgRate(cafe.getAvgRate());
                        else store.setAvgRate(-1.0);
                        serverAPI.getPatronCafeList(userSeq).enqueue(new Callback<List<PatronCafe>>() {
                            @Override
                            public void onResponse(Call<List<PatronCafe>> call, Response<List<PatronCafe>> response) {
                                if(response.isSuccessful()) {
                                    Log.d("카페", "userSeq: " + userSeq);
                                    for(PatronCafe patronCafe : response.body()) {
                                        if(patronCafe.getCafeSeq().equals(cafe.getSeq())) {
                                            store.setPatron(true);
                                        }
                                    }
                                    storeList.add(store);
                                    Log.d("카페", "스토어리스트에 집어넣음");
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PatronCafe>> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Log.d("서버", t.getMessage());
            }
        });
    }

    void refreshBarList() {
        serverAPI.getBarList().enqueue(new Callback<List<Bar>>() {
            @Override
            public void onResponse(Call<List<Bar>> call, Response<List<Bar>> response) {
                if (response.isSuccessful()) {
                    Log.d("타입: ", "" + selectedStoreType);
                    for (Bar bar : response.body()) {
                        String openState = bar.getCurrentState();
                        if (openState.equals("UNKNOWN"))
                            openState = "등록된 오픈 정보 없음";
                        Date date;
                        if (bar.getLastUpdate() == null)
                            date = null;
                        else date = bar.getLastUpdate();
                        Log.d("서버", bar.getName());
                        Store store = new Store();
                        store.setType(2);
                        store.setSeq(bar.getSeq());
                        store.setPhotoUrl(bar.getPhotoURL());
                        store.setName(bar.getName());
                        store.setOpenState(openState);
                        store.setRuntime(bar.getRunningTime());
                        if(bar.getAvgRate() != null)
                            store.setAvgRate(bar.getAvgRate());
                        else store.setAvgRate(-1.0);
                        serverAPI.getPatronBarList(userSeq).enqueue(new Callback<List<PatronBar>>() {
                            @Override
                            public void onResponse(Call<List<PatronBar>> call, Response<List<PatronBar>> response) {
                                if(response.isSuccessful()) {
                                    for(PatronBar patronBar : response.body()) {
                                        if(patronBar.getBarSeq().equals(bar.getSeq())) {
                                            store.setPatron(true);
                                        }
                                    }
                                    storeList.add(store);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<PatronBar>> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bar>> call, Throwable t) {
                Log.d("서버", t.getMessage());
            }
        });
    }
}
