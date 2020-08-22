package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amar.library.ui.StickyScrollView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CafeInfoReviewFragment extends Fragment {
    Context mContext;
    Activity activity;
    ServerAPI serverAPI;
    StickyScrollView stickyScrollView;
    RecyclerView recyclerView;
    List<CafeInfoReview> reviewList = new ArrayList<>();
    CafeReviewAdapter adapter;
    private Long cafeSeq;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CafeInfoReviewFragment() {
        // Required empty public constructor
    }


    public static CafeInfoReviewFragment newInstance(String param1, String param2) {
        CafeInfoReviewFragment fragment = new CafeInfoReviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_cafe_info_review, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.rv);
        adapter = new CafeReviewAdapter(reviewList, activity);
        Log.d("탭탭", "도달함");
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        serverAPI.getCafeInfoReviewList(cafeSeq).enqueue(new Callback<List<CafeInfoReview>>() {
            @Override
            public void onResponse(Call<List<CafeInfoReview>> call, Response<List<CafeInfoReview>> response) {
                if(response.isSuccessful()) {
                    reviewList.clear();
                    reviewList = response.body();
                    adapter = new CafeReviewAdapter(response.body(), activity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CafeInfoReview>> call, Throwable t) {

            }
        });

       /* listView.setNestedScrollingEnabled(false);
        stickyScrollView = v.findViewById(R.id.stickyScroll);
        stickyScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(!stickyScrollView.canScrollVertically(1)) {
                    listView.setNestedScrollingEnabled(true);
                }
                else if (!stickyScrollView.canScrollVertically(-1)) {
                    listView.setNestedScrollingEnabled(false);
                }
                else if (!listView.canScrollVertically(-1)) {
                    listView.setNestedScrollingEnabled(false);
                }
                else if (!listView.canScrollVertically(1)) {
                    listView.setNestedScrollingEnabled(true);
                }
                else {
                    listView.setNestedScrollingEnabled(false);
                }
                return false;
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(!stickyScrollView.canScrollVertically(1)) {
                    listView.setNestedScrollingEnabled(true);
                }
                else if (!stickyScrollView.canScrollVertically(-1)) {
                    listView.setNestedScrollingEnabled(false);
                }
                else if (!listView.canScrollVertically(-1)) {
                    listView.setNestedScrollingEnabled(false);
                }
                else if (!listView.canScrollVertically(1)) {
                    listView.setNestedScrollingEnabled(true);
                }
                else {
                    listView.setNestedScrollingEnabled(false);
                }
                return false;
            }
        });*/

        return v;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if(context instanceof Activity)
            activity = (Activity) context;
        cafeSeq = activity.getIntent().getLongExtra("seq", 0);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
