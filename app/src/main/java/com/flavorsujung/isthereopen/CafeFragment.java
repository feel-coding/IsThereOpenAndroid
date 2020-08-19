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


public class CafeFragment extends Fragment {


    RecyclerView recyclerView;
    StoreAdapter adapter;
    ArrayList<Store> cafeList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cafe, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.cafeRecyclerView);

        swipeRefreshLayout = v.findViewById(R.id.cafeSwipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCafeList();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });

        refreshCafeList();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new StoreAdapter(cafeList, mContext);
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



    void refreshCafeList() {
        serverAPI.getCafeList().enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                if (response.isSuccessful()) {
                    Log.d("서버", "성공");
                    cafeList.clear();
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
                        cafeList.add(store);
                    }
                    Log.d("서버", cafeList.toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Log.d("서버", t.getMessage());
            }
        });
    }
}
