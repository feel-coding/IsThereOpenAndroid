package com.flavorsujung.isthereopen;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CafeOpenReviewActivity extends AppCompatActivity {

    ArrayList<CafeOpenReview> reviewOpenDataList;
    ServerAPI serverAPI; // 서버 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_review);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this); // 서버 안에 넣어줌


        ListView listView = (ListView) findViewById(R.id.OpenReviewListView);
        final CafeOpenReviewAdapter myAdapter = new CafeOpenReviewAdapter(this, reviewOpenDataList);
        listView.setAdapter(myAdapter);



        //데이터를 저장하게 되는 리스트
        List<String> list = new ArrayList<>();

        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        //리스트뷰의 어댑터를 지정해준다.
        listView.setAdapter(adapter);



        //리스트뷰에 보여질 아이템을 추가
        list.add("설"+ "님이" + "몇시몇분에" + "OPEN" + "을 확인하였습니다.");
        list.add("선"+ "님이" + "몇시몇분에" + "OPEN" + "을 확인하였습니다.");
        list.add("별"+ "님이" + "몇시몇분에" + "OPEN" + "을 확인하였습니다.");
        list.add("달"+ "님이" + "몇시몇분에" + "OPEN" + "을 확인하였습니다.");



        //서버 API
        // 오픈리뷰 넣는 함수 putCafeOpenReview 에서 필요한 인자 넣어주고 enqueue( new 한다음 ctrl+space 눌러서 자동완성 채워줌
        serverAPI.putCafeOpenReview(1L, 1L, "CLOSE").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                response.body(); // 이 바디 안에 내가 받아야 할 정보들이 있음. 지금은 오픈리뷰 넣어주는 함수이기 때문에 바디 안에 아무것도 없음
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        //서버 API
        // 오픈리뷰 가져오는 함수 getCafeOpenReviewList에 필요한 인자 넣어주고 위와 똑같이 ctrl+space 해줌
        serverAPI.getCafeOpenReviewList(1L).enqueue(new Callback<List<CafeOpenReview>>() {
            @Override
            public void onResponse(Call<List<CafeOpenReview>> call, Response<List<CafeOpenReview>> response) {
                // 이 if문을 꼭 써줘야 함. response가 성공적으로 가져와졌을떄 response.body()에 있는 오픈리뷰들 list에 넣어줌
                if(response.isSuccessful()) {
                    List<CafeOpenReview> list = response.body();
                } // 이제 이 list 를 보여주면 됨.
            }

            @Override
            public void onFailure(Call<List<CafeOpenReview>> call, Throwable t) {

            }
        });
    }



}

