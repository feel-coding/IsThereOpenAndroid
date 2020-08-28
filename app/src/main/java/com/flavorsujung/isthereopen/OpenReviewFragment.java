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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OpenReviewFragment extends Fragment {

    Context mContext;
    Activity activity;
    private Long seq;
    String type;
    ServerAPI serverAPI;
    List<OpenReview> openReviewList = new ArrayList<>();
    //ListView listView;
    RecyclerView recyclerView;
    OpenReviewAdapter adapter;
    FrameLayout layout;

    private OnFragmentInteractionListener mListener;

    public OpenReviewFragment() {
        // Required empty public constructor
    }


    public static OpenReviewFragment newInstance(String param1, String param2) {
        OpenReviewFragment fragment = new OpenReviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_open_review, container, false);
        recyclerView = view.findViewById(R.id.openReviewRv);
        layout = view.findViewById(R.id.openReviewFragment);
        adapter = new OpenReviewAdapter(openReviewList, activity);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(activity));
        recyclerView.setAdapter(adapter);

        serverAPI = RetrofitManager.getInstance().getServerAPI(activity);
        if(type.equals("cafe")) {
            Log.d("여기는 OpenReviewActivity", "타입은 " + type + ", seq는 " + seq);
            serverAPI.getCafeOpenReviewList(seq).enqueue(new Callback<List<CafeOpenReview>>() {
                @Override
                public void onResponse(Call<List<CafeOpenReview>> call, Response<List<CafeOpenReview>> response) {
                    if(response.isSuccessful()) {
                        Log.d("카페", "첫번째 성공");
                        for (CafeOpenReview openReview : response.body()) {
                            serverAPI.getUser(openReview.getUserSeq()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("카페", "두번째 성공");
                                        Date date = openReview.getCreatedAt();
                                        openReviewList.add(new OpenReview(date, response.body().getName(), openReview.getOpenState()));

                                        Log.d("높이: ", "" + recyclerView.getMeasuredHeight());
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<CafeOpenReview>> call, Throwable t) {

                }
            });
        }
        else if (type.equals("restaurant")) {
            Log.d("여기는 OpenReviewActivity", "타입은 " + type + ", seq는 " + seq);
            serverAPI.getRestaurantOpenReviewList(seq).enqueue(new Callback<List<RestaurantOpenReview>>() {
                @Override
                public void onResponse(Call<List<RestaurantOpenReview>> call, Response<List<RestaurantOpenReview>> response) {
                    if(response.isSuccessful()) {
                        Log.d("카페", "첫번째 성공");
                        for (RestaurantOpenReview openReview : response.body()) {
                            serverAPI.getUser(openReview.getUserSeq()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("카페", "두번째 성공");
                                        Date date = openReview.getCreatedAt();
                                        openReviewList.add(new OpenReview(date, response.body().getName(), openReview.getOpenState()));
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<RestaurantOpenReview>> call, Throwable t) {

                }
            });
        }
        else if (type.equals("bar")) {
            Log.d("여기는 OpenReviewActivity", "타입은 " + type + ", seq는 " + seq);
            serverAPI.getBarOpenReviewList(seq).enqueue(new Callback<List<BarOpenReview>>() {
                @Override
                public void onResponse(Call<List<BarOpenReview>> call, Response<List<BarOpenReview>> response) {
                    if(response.isSuccessful()) {
                        Log.d("카페", "첫번째 성공");
                        for (BarOpenReview openReview : response.body()) {
                            serverAPI.getUser(openReview.getUserSeq()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()) {
                                        Log.d("카페", "두번째 성공");
                                        Date date = openReview.getCreatedAt();
                                        openReviewList.add(new OpenReview(date, response.body().getName(), openReview.getOpenState()));
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<BarOpenReview>> call, Throwable t) {

                }
            });
        }
        return view;
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
        seq = activity.getIntent().getLongExtra("seq", 0);
        type = activity.getIntent().getStringExtra("type");
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
