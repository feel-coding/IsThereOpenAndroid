package com.flavorsujung.isthereopen;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amar.library.ui.StickyScrollView;

import java.util.ArrayList;


public class CafeInfoReviewFragment extends Fragment {
    Context mContext;
    Activity activity;
    StickyScrollView stickyScrollView;
    ListView listView;
    private Integer cafeSeq;

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
        listView = v.findViewById(R.id.cafeInfoReviewLv);
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
        ArrayList<String> arrayList = new ArrayList<>();
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
        cafeSeq = activity.getIntent().getIntExtra("seq", 0);

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
