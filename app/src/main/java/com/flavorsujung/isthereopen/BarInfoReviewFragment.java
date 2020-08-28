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
import android.widget.TextView;

import com.amar.library.ui.StickyScrollView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BarInfoReviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BarInfoReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarInfoReviewFragment extends Fragment {
    Context mContext;
    Activity activity;
    ServerAPI serverAPI;
    StickyScrollView stickyScrollView;
    RecyclerView recyclerView;
    List<BarInfoReview> reviewList = new ArrayList<>();
    BarReviewAdapter adapter;
    TextView reviewCount;
    Button writeReviewBtn;
    private Long barSeq;
    private String barName;

    private BarInfoReviewFragment.OnFragmentInteractionListener mListener;

    public BarInfoReviewFragment() {
        // Required empty public constructor
    }


    public static BarInfoReviewFragment newInstance(String param1, String param2) {
        BarInfoReviewFragment fragment = new BarInfoReviewFragment();
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
        View v =  inflater.inflate(R.layout.fragment_bar_info_review, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        recyclerView = v.findViewById(R.id.barReviewRecyclerView);
        reviewCount = v.findViewById(R.id.barReviewCount);
        writeReviewBtn = v.findViewById(R.id.writeBarReviewBtn);
        adapter = new BarReviewAdapter(reviewList, activity);
        Log.d("탭탭", "도달함");
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(activity));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        serverAPI.getBarInfoReviewList(barSeq).enqueue(new Callback<List<BarInfoReview>>() {
            @Override
            public void onResponse(Call<List<BarInfoReview>> call, Response<List<BarInfoReview>> response) {
                if(response.isSuccessful()) {
                    reviewList.clear();
                    reviewList = response.body();
                    reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                    adapter = new BarReviewAdapter(response.body(), activity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BarInfoReview>> call, Throwable t) {

            }
        });
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, WriteBarReviewActivity.class);
                intent.putExtra("seq", barSeq);
                intent.putExtra("name", barName);
                startActivityForResult(intent, 300);
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
        barSeq = activity.getIntent().getLongExtra("seq", 0);
        barName = activity.getIntent().getStringExtra("name");

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
        if(requestCode == 300 && resultCode == RESULT_OK) {
            serverAPI.getBarInfoReviewList(barSeq).enqueue(new Callback<List<BarInfoReview>>() {
                @Override
                public void onResponse(Call<List<BarInfoReview>> call, Response<List<BarInfoReview>> response) {
                    if(response.isSuccessful()) {
                        reviewList.clear();
                        reviewList = response.body();
                        reviewCount.setText("총 " + response.body().size() + "개의 리뷰");
                        adapter = new BarReviewAdapter(response.body(), activity);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<BarInfoReview>> call, Throwable t) {

                }
            });
        }
    }
}
