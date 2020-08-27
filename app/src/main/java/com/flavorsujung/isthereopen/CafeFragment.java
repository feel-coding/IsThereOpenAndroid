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


public class CafeFragment extends Fragment {


    RecyclerView recyclerView;
    StoreAdapter adapter;
    List<Store> cafeList = new ArrayList<>();
    List<Store> searchList = new ArrayList<>();
    List<Store> selectedList = new ArrayList<>();
    private Context mContext;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;
    ServerAPI serverAPI;
    SearchView searchView;
    TextView toolbarTitleTv;
    Button fastBtn;
    Button cheapBtn;
    Button littlePeopleBtn;
    Button manyPlugBtn;
    Button notLowBtn;
    Button lightBtn;
    Button stableBtn;
    Button stayLongBtn;
    Map<String, Boolean> selected = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cafe, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.cafeRecyclerView);
        searchView = v.findViewById(R.id.cafeSearchView);
        toolbarTitleTv = v.findViewById(R.id.cafeToolbarTitleTv);
        swipeRefreshLayout = v.findViewById(R.id.cafeSwipe);
        fastBtn = v.findViewById(R.id.cafeFastBtn);
        cheapBtn = v.findViewById(R.id.cafeCheapBtn);
        stayLongBtn = v.findViewById(R.id.cafeStayLongBtn);
        littlePeopleBtn = v.findViewById(R.id.cafeLittlePeopleBtn);
        manyPlugBtn = v.findViewById(R.id.cafeManyPlugBtn);
        notLowBtn = v.findViewById(R.id.cafeNotLowBtn);
        lightBtn = v.findViewById(R.id.cafeLightBtn);
        stableBtn = v.findViewById(R.id.cafeStableBtn);
        selected.put("fast", false);
        selected.put("cheap", false);
        selected.put("long", false);
        selected.put("littlePeople", false);
        selected.put("manyPlug", false);
        selected.put("notLow", false);
        selected.put("light", false);
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
                toolbarTitleTv.setText("카페 열었나?");
                adapter = new StoreAdapter(cafeList, mContext);
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
                for (Store store : cafeList) {
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
                refreshCafeList();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cafeFastBtn:
                        selected.put("fast", !selected.get("fast"));
                        break;
                    case R.id.cafeCheapBtn:
                        selected.put("cheap", !selected.get("cheap"));
                        break;
                    case R.id.cafeStayLongBtn:
                        selected.put("long", !selected.get("long"));
                        break;
                    case R.id.cafeLittlePeopleBtn:
                        selected.put("littlePeople", !selected.get("littlePeople"));
                        break;
                    case R.id.cafeManyPlugBtn:
                        selected.put("manyPlug", !selected.get("manyPlug"));
                        break;
                    case R.id.cafeNotLowBtn:
                        selected.put("notLow", !selected.get("notLow"));
                        break;
                    case R.id.cafeLightBtn:
                        selected.put("light", !selected.get("light"));
                        break;
                    case R.id.cafeStableBtn:
                        selected.put("stable", !selected.get("stable"));
                        break;
                }
                boolean nothingFlag = true; // 아무것도 선택 안 됐다
                selectedList.clear();
                selectedList.addAll(cafeList);
                if(selected.get("fast")) {
                    nothingFlag = false;
                    fastBtn.setBackgroundResource(R.drawable.full_red_round);
                    fastBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeShortWaiting() == null || store.getCafeShortWaiting() == 0) {
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
                        if(store.getCafeCheap() == null || store.getCafeCheap() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    cheapBtn.setBackgroundResource(R.drawable.black_round_square);
                    cheapBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("long")) {
                    nothingFlag = false;
                    stayLongBtn.setBackgroundResource(R.drawable.full_red_round);
                    stayLongBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeStayLong() == null || store.getCafeStayLong() == 0) {
                            selectedList.remove(store);
                        }
                    }

                }
                else {
                    stayLongBtn.setBackgroundResource(R.drawable.black_round_square);
                    stayLongBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("littlePeople")) {
                    nothingFlag = false;
                    littlePeopleBtn.setBackgroundResource(R.drawable.full_red_round);
                    littlePeopleBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeLittlePeople() == null || store.getCafeLittlePeople() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    littlePeopleBtn.setBackgroundResource(R.drawable.black_round_square);
                    littlePeopleBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("manyPlug")) {
                    nothingFlag = false;
                    manyPlugBtn.setBackgroundResource(R.drawable.full_red_round);
                    manyPlugBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeManyPlug() == null || store.getCafeManyPlug() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    manyPlugBtn.setBackgroundResource(R.drawable.black_round_square);
                    manyPlugBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("notLow")) {
                    nothingFlag = false;
                    notLowBtn.setBackgroundResource(R.drawable.full_red_round);
                    notLowBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeNotLow() == null || store.getCafeNotLow() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    notLowBtn.setBackgroundResource(R.drawable.black_round_square);
                    notLowBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("light")) {
                    nothingFlag = false;
                    lightBtn.setBackgroundResource(R.drawable.full_red_round);
                    lightBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeLight() == null || store.getCafeLight() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    lightBtn.setBackgroundResource(R.drawable.black_round_square);
                    lightBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }
                if(selected.get("stable")) {
                    nothingFlag = false;
                    stableBtn.setBackgroundResource(R.drawable.full_red_round);
                    stableBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    for (Store store : selectedList) {
                        if(store.getCafeStable() == null || store.getCafeStable() == 0) {
                            selectedList.remove(store);
                        }
                    }
                }
                else {
                    stableBtn.setBackgroundResource(R.drawable.black_round_square);
                    stableBtn.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                if(nothingFlag) {
                    adapter = new StoreAdapter(cafeList, mContext);
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
        refreshCafeList();

        fastBtn.setOnClickListener(onClickListener);
        cheapBtn.setOnClickListener(onClickListener);
        stayLongBtn.setOnClickListener(onClickListener);
        littlePeopleBtn.setOnClickListener(onClickListener);
        manyPlugBtn.setOnClickListener(onClickListener);
        notLowBtn.setOnClickListener(onClickListener);
        lightBtn.setOnClickListener(onClickListener);
        stableBtn.setOnClickListener(onClickListener);

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
                        store.setCafeShortWaiting(cafe.getShortWaiting());
                        store.setCafeCheap(cafe.getCheap());
                        store.setCafeLight(cafe.getLight());
                        store.setCafeManyPlug(cafe.getManyPlug());
                        store.setCafeNotLow(cafe.getNotLow());
                        store.setCafeLittlePeople(cafe.getLittlePeople());
                        store.setCafeStayLong(cafe.getStayLong());
                        store.setCafeStable(cafe.getStable());
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
