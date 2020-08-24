package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class CafeInfoReviewFragment extends Fragment {
    Context mContext;
    Activity activity;
    ServerAPI serverAPI;
    RecyclerView recyclerView;
    List<CafeInfoReview> reviewList = new ArrayList<>();
    CafeReviewAdapter adapter;
    TextView reviewCount;
    private Long cafeSeq;
    private String cafeName;
    Button writeReviewBtn;


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
        recyclerView = v.findViewById(R.id.cafeReviewRecyclerView);
        reviewCount = v.findViewById(R.id.cafeReviewCount);
        writeReviewBtn = v.findViewById(R.id.writeCafeReviewBtn);
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
                    reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                    adapter = new CafeReviewAdapter(response.body(), activity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CafeInfoReview>> call, Throwable t) {

            }
        });
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, WriteCafeReviewActivity.class);
                Log.d("정보넘기기", "seq: " + cafeSeq + ", name: " + cafeName);
                intent.putExtra("seq", cafeSeq);
                intent.putExtra("name", cafeName);
                startActivityForResult(intent, 200);
            }
        });

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
        cafeName = activity.getIntent().getStringExtra("name");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK) {
            serverAPI.getCafeInfoReviewList(cafeSeq).enqueue(new Callback<List<CafeInfoReview>>() {
                @Override
                public void onResponse(Call<List<CafeInfoReview>> call, Response<List<CafeInfoReview>> response) {
                    if(response.isSuccessful()) {
                        reviewList.clear();
                        reviewList = response.body();
                        reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                        adapter = new CafeReviewAdapter(response.body(), activity);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<CafeInfoReview>> call, Throwable t) {

                }
            });
        }
    }
}
