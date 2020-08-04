package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    ArrayList<Store> storeList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    Button restaurantBtn;
    Button cafeBtn;
    Button barBtn;
    TextView thereTv;
    ServerAPI serverAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.storeRecyclerView);
        restaurantBtn = v.findViewById(R.id.restaurantBtn);
        cafeBtn = v.findViewById(R.id.cafeBtn);
        barBtn = v.findViewById(R.id.barBtn);
        thereTv = v.findViewById(R.id.thereTv);
        restaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thereTv.setText("음식점");
                storeList.clear();
                storeList.add(new Store("홀슈", "close", "20:38 기준", "오후 1시~오후 8시", 3.8));
                storeList.add(new Store("최고당 돈까스", "open", "17:05 기준", "오후 10시~오후 8시", 3.8));
                storeList.add(new Store("봉봉", "open", "20:53 기준", "오전 10시~오후 8시", 3.8));
                storeList.add(new Store("아리랑노점", "close", "20:07 기준", "오전 9시~오후 8시", 3.8));
                adapter.notifyDataSetChanged();
            }
        });
        cafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thereTv.setText("카페");
                storeList.clear();
                storeList.add(new Store("본크레페", "open", "20:11 기준", "오후 2시~오후 8시", 3.8));
                storeList.add(new Store("카페온더플랜", "open", "20:11 기준", "오전 10시~오전 5시", 4.5));
                storeList.add(new Store("카페비", "open", "21:00 기준", "오전 10시~오후 12시", 4.8));
                storeList.add(new Store("홀슈", "close", "20:38 기준", "오후 1시~오후 8시", 3.8));
                adapter.notifyDataSetChanged();
            }
        });
        barBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thereTv.setText("술집");
                storeList.clear();
                storeList.add(new Store("도시포차", "open", "23:00 기준", "오후 4시~오전 2시", 4.1));
                adapter.notifyDataSetChanged();
            }
        });

        /*storeList.add(new Store("본크레페", "open", "20:11 기준", "오후 2시~오후 8시", 3.8));
        storeList.add(new Store("카페온더플랜", "open", "20:11 기준", "오전 10시~오전 5시", 4.5));
        storeList.add(new Store("최고당 돈까스", "open", "17:05 기준", "오후 10시~오후 8시", 3.8));
        storeList.add(new Store("카페비", "open", "21:00 기준", "오전 10시~오후 12시", 4.8));
        storeList.add(new Store("도시포차", "open", "23:00 기준", "오후 4시~오전 2시", 4.1));
        storeList.add(new Store("홀슈", "close", "20:38 기준", "오후 1시~오후 8시", 3.8));
        storeList.add(new Store("봉봉", "open", "20:53 기준", "오전 10시~오후 8시", 3.8));
        storeList.add(new Store("아리랑노점", "close", "20:07 기준", "오전 9시~오후 8시", 3.8));*/
        serverAPI.getCafeList().enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                if(response.isSuccessful()) {
                    Log.d("서버", "성공");
                    for(Cafe cafe : response.body()) {
                        String openState;
                        if(cafe.getCurrentState() == 0) {
                            openState = "CLOSE";
                        }
                        else if (cafe.getCurrentState() == 1) {
                            openState = "BREAKTIME";
                        }
                        else if (cafe.getCurrentState() == 2) {
                            openState = "OPEN";
                        }
                        else {
                            openState = "UNKNOWN";
                        }
                        String date;
                        if(cafe.getLastUpdate() == null)
                            date = "";
                        else date = cafe.getLastUpdate().toString();
                        Log.d("서버", cafe.getName());
                        storeList.add(new Store(cafe.getName(), openState, date, cafe.getRunningTime(), cafe.getRate()));
                    }
                    Log.d("서버", storeList.toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {

            }
        });
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
}
