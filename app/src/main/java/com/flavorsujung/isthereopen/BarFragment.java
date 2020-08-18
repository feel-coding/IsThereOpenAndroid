package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarFragment extends Fragment {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    ArrayList<Store> barList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.barRecyclerView);

        swipeRefreshLayout = v.findViewById(R.id.barSwipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBarList();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });

        refreshBarList();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new StoreAdapter(barList, mContext);
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



    void refreshBarList() {
        serverAPI.getBarList().enqueue(new Callback<List<Bar>>() {
            @Override
            public void onResponse(Call<List<Bar>> call, Response<List<Bar>> response) {
                if (response.isSuccessful()) {
                    Log.d("서버", "성공");
                    barList.clear();
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
                        store.setSeq(bar.getSeq());
                        store.setPhotoUrl(bar.getPhotoURL());
                        store.setName(bar.getName());
                        store.setOpenState(openState);
                        store.setRuntime(bar.getRunningTime());
                        if(bar.getAvgRate() != null)
                            store.setAvgRate(bar.getAvgRate());
                        else store.setAvgRate(-1.0);
                        barList.add(store);
                    }
                    Log.d("서버", barList.toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Bar>> call, Throwable t) {
                Log.d("서버", t.getMessage());
            }
        });
    }
}
