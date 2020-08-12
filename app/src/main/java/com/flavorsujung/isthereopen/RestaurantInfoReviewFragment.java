package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
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

public class RestaurantInfoReviewFragment extends Fragment {
    Context mContext;
    Activity activity;
    StickyScrollView stickyScrollView;
    ListView listView;
    Integer restaurantSeq;
    ServerAPI serverAPI;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RestaurantInfoReviewFragment() {
        // Required empty public constructor
    }


    public static RestaurantInfoReviewFragment newInstance(String param1, String param2) {
        RestaurantInfoReviewFragment fragment = new RestaurantInfoReviewFragment();
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
        View v =  inflater.inflate(R.layout.fragment_restaurant_info_review, container, false);
        serverAPI = RetrofitManager.getInstance().getServerAPI(v.getContext());
        listView = v.findViewById(R.id.restaurantInfoReviewLv);
        ArrayList<String> arrayList = new ArrayList<>();
        /*serverAPI.getRestaurantOpenReviewList(restaurantSeq).enqueue(new Callback<List<RestaurantOpenReview>>() {
            @Override
            public void onResponse(Call<List<RestaurantOpenReview>> call, Response<List<RestaurantOpenReview>> response) {
                if (response.isSuccessful()) {
                    for (RestaurantOpenReview review : response.body()) {
                        String state;
                        Integer openState = review.getOpenState();
                        if (openState == 0) {
                            state = "CLOSE를";
                        }
                        else if (openState == 1) {
                            state = "BREAK를";
                        }
                        else if (openState == 2) {
                            state = "OPEN을";
                        }
                        else
                            state = "UNKNOWN";
                        //arrayList.add("(사용자id)님이" +review.getUpdatedTime().getHours() + "시 " + review.getUpdatedTime().getMinutes() + "분에 " + state + " 확인했습니다.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantOpenReview>> call, Throwable t) {

            }
        });*/
        arrayList.add("(사용자id)님이 몇시 몇분에 CLOSE을 확인하였습니다.");
        arrayList.add("(사용자id)님이 몇시 몇분에 BREAK를 확인하였습니다.");
        arrayList.add("(사용자id)님이 몇시 몇분에 OPEN을 확인하였습니다.");
        arrayList.add("(사용자id)님이 몇시 몇분에 OPEN을 확인하였습니다.");
        arrayList.add("(사용자id)님이 몇시 몇분에 OPEN을 확인하였습니다.");
        arrayList.add("(사용자id)님이 몇시 몇분에 OPEN을 확인하였습니다.");
        arrayList.add("(사용자id)님이 몇시 몇분에 OPEN을 확인하였습니다.");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
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
        restaurantSeq = activity.getIntent().getIntExtra("seq", 0);
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
