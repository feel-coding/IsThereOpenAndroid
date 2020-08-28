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

public class BarFragment extends Fragment {

    RecyclerView recyclerView;
    StoreAdapter adapter;
    List<Store> barList = new ArrayList<>();
    List<Store> searchList = new ArrayList<>();
    List<Store> selectedList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;
    SearchView searchView;
    TextView toolbarTitleTv;
    Button stableBtn;
    Button notLoudBtn;
    Button cheapBtn;
    Button separateBtn;
    Button sojuBtn;
    Button beerBtn;
    Button makgeolliBtn;
    Button wineBtn;
    Button vodkaBtn;
    Button cleanBtn;
    Map<String, Boolean> selected = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.barRecyclerView);
        searchView = v.findViewById(R.id.barSearchView);
        toolbarTitleTv = v.findViewById(R.id.barToolbarTitleTv);
        swipeRefreshLayout = v.findViewById(R.id.barSwipe);
        separateBtn = v.findViewById(R.id.barSeparateBtn);
        notLoudBtn = v.findViewById(R.id.barNotLoudBtn);
        cheapBtn = v.findViewById(R.id.barCheapBtn);
        sojuBtn = v.findViewById(R.id.barSojuBtn);
        beerBtn = v.findViewById(R.id.barBeerBtn);
        makgeolliBtn = v.findViewById(R.id.barMakgeolliBtn);
        wineBtn = v.findViewById(R.id.barWineBtn);
        vodkaBtn = v.findViewById(R.id.barVodkaBtn);
        cleanBtn = v.findViewById(R.id.barCleanBtn);
        stableBtn = v.findViewById(R.id.barStableBtn);
        selected.put("separate", false);
        selected.put("notLoud", false);
        selected.put("cheap", false);
        selected.put("soju", false);
        selected.put("beer", false);
        selected.put("makgeolli", false);
        selected.put("wine", false);
        selected.put("vodka", false);
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
                adapter = new StoreAdapter(barList, mContext);
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
                for (Store store : barList) {
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
                refreshBarList();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("클릭", "클릭됨");
                switch (view.getId()) {
                    case R.id.barSeparateBtn:
                        selected.put("separate", !selected.get("separate"));
                        break;
                    case R.id.barNotLoudBtn:
                        selected.put("notLoud", !selected.get("notLoud"));
                        break;
                    case R.id.barCheapBtn:
                        selected.put("cheap", !selected.get("cheap"));
                        break;
                    case R.id.barSojuBtn:
                        selected.put("soju", !selected.get("soju"));
                        break;
                    case R.id.barBeerBtn:
                        selected.put("beer", !selected.get("beer"));
                        break;
                    case R.id.barMakgeolliBtn:
                        selected.put("makgeolli", !selected.get("makgeolli"));
                        break;
                    case R.id.barWineBtn:
                        selected.put("wine", !selected.get("wine"));
                        break;
                    case R.id.barVodkaBtn:
                        selected.put("vodka", !selected.get("vodka"));
                        break;
                    case R.id.barCleanBtn:
                        selected.put("clean", !selected.get("clean"));
                        break;
                    case R.id.barStableBtn:
                        selected.put("stable", !selected.get("stable"));
                        break;
                }
                boolean nothingFlag = true; // 아무것도 선택 안 됐다
                selectedList.clear();
                selectedList.addAll(barList);
                if(selected.get("separate")) {
                    nothingFlag = false;
                    separateBtn.setBackgroundResource(R.drawable.full_red_round);
                    separateBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarSeparate() == null || store.getBarSeparate() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }

                }
                else {
                    separateBtn.setBackgroundResource(R.drawable.black_round_square);
                    separateBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("notLoud")) {
                    nothingFlag = false;
                    notLoudBtn.setBackgroundResource(R.drawable.full_red_round);
                    notLoudBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarNotLoud() == null || store.getBarNotLoud() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    notLoudBtn.setBackgroundResource(R.drawable.black_round_square);
                    notLoudBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("cheap")) {
                    nothingFlag = false;
                    cheapBtn.setBackgroundResource(R.drawable.full_red_round);
                    cheapBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarCheap() == null || store.getBarCheap() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }

                }
                else {
                    cheapBtn.setBackgroundResource(R.drawable.black_round_square);
                    cheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("soju")) {
                    nothingFlag = false;
                    sojuBtn.setBackgroundResource(R.drawable.full_red_round);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarSoju() == null || store.getBarSoju() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    sojuBtn.setBackgroundResource(R.drawable.black_round_square);
                    sojuBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("beer")) {
                    nothingFlag = false;
                    beerBtn.setBackgroundResource(R.drawable.full_red_round);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarBeer() == null || store.getBarBeer() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    beerBtn.setBackgroundResource(R.drawable.black_round_square);
                    beerBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("makgeolli")) {
                    nothingFlag = false;
                    makgeolliBtn.setBackgroundResource(R.drawable.full_red_round);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarMakgeolli() == null || store.getBarMakgeolli() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    makgeolliBtn.setBackgroundResource(R.drawable.black_round_square);
                    makgeolliBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("wine")) {
                    nothingFlag = false;
                    wineBtn.setBackgroundResource(R.drawable.full_red_round);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarWine() == null || store.getBarWine() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    wineBtn.setBackgroundResource(R.drawable.black_round_square);
                    wineBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("vodka")) {
                    nothingFlag = false;
                    vodkaBtn.setBackgroundResource(R.drawable.full_red_round);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarVodka() == null || store.getBarVodka() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    vodkaBtn.setBackgroundResource(R.drawable.black_round_square);
                    vodkaBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("clean")) {
                    nothingFlag = false;
                    cleanBtn.setBackgroundResource(R.drawable.full_red_round);
                    cleanBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarClean() == null || store.getBarClean() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
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
                    int size = selectedList.size();
                    for (int i = 0; i < size; i++) {
                        Store store = selectedList.get(i);
                        if(store.getBarStable() == null || store.getBarStable() == 0) {
                            selectedList.remove(store);
                            size--;
                            i--;
                        }
                    }
                }
                else {
                    stableBtn.setBackgroundResource(R.drawable.black_round_square);
                    stableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                if(nothingFlag) {
                    adapter = new StoreAdapter(barList, mContext);
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

        refreshBarList();
        cleanBtn.setOnClickListener(onClickListener);
        stableBtn.setOnClickListener(onClickListener);
        sojuBtn.setOnClickListener(onClickListener);
        beerBtn.setOnClickListener(onClickListener);
        makgeolliBtn.setOnClickListener(onClickListener);
        wineBtn.setOnClickListener(onClickListener);
        vodkaBtn.setOnClickListener(onClickListener);
        notLoudBtn.setOnClickListener(onClickListener);
        cheapBtn.setOnClickListener(onClickListener);
        separateBtn.setOnClickListener(onClickListener);

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
                        store.setType(2);
                        store.setSeq(bar.getSeq());
                        store.setPhotoUrl(bar.getPhotoURL());
                        store.setName(bar.getName());
                        store.setOpenState(openState);
                        store.setRuntime(bar.getRunningTime());
                        if(bar.getAvgRate() != null)
                            store.setAvgRate(bar.getAvgRate());
                        else store.setAvgRate(-1.0);
                        store.setBarBeer(bar.getBeer());
                        store.setBarSoju(bar.getSoju());
                        store.setBarWine(bar.getWine());
                        store.setBarMakgeolli(bar.getMakgeolli());
                        store.setBarVodka(bar.getVodka());
                        store.setBarStable(bar.getStable());
                        store.setBarClean(bar.getClean());
                        store.setBarNotLoud(bar.getNotLoud());
                        store.setBarSeparate(bar.getSeparate());
                        store.setBarCheap(bar.getCheap());
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
