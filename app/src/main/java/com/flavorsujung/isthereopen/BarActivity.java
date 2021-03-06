package com.flavorsujung.isthereopen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarActivity extends AppCompatActivity implements BarInfoReviewFragment.OnFragmentInteractionListener, OpenReviewFragment.OnFragmentInteractionListener {

    BarReviewAdapter adapter;
    private BarViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String[] tabTitles= {"가게리뷰", "오픈리뷰"};

    ImageView barLogoIv;
    Intent intent;
    Bar bar;
    Double rate;
    ServerAPI serverAPI;
    TextView barTitleTv;
    Toolbar toolbar;

    TextView openStateTv;
    TextView runningTimeTv;
    TextView addressTv;
    TextView phoneNumberTv;
    SwipeRefreshLayout swipeRefreshLayout;
    Button openBtn;
    Button breakBtn;
    Button closeBtn;
    SharedPreferences sharedPreferences;
    Long userSeq;
    Long barSeq;
    ImageView callBtn;

    ImageView firstStar;
    ImageView secondStar;
    ImageView thirdStar;
    ImageView fourthStar;
    ImageView fifthStar;
    TextView rateTv;

    TextView openStyleTv;
    TextView moodTv;
    TextView priceTv;
    TextView toiletTv;
    TextView alcoholTv;
    TextView cleannessTv;

    ChangeOpenStateDialog dialog;
    View.OnClickListener openListener;
    View.OnClickListener breakListener;
    View.OnClickListener closeListener;
    View.OnClickListener cancelListener;

    boolean isPatron;
    ImageView heartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        View decoView = getWindow().getDecorView();
        decoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        viewPagerAdapter = new BarViewPagerAdapter(this, 2);
        viewPager = findViewById(R.id.barViewPager);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.barReviewTab);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {tab.setText(tabTitles[position]);
                    viewPager.setCurrentItem(tab.getPosition(), true);}).attach();
        toolbar = findViewById(R.id.barToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        serverAPI = RetrofitManager.getInstance().getServerAPI(this);
        sharedPreferences = getSharedPreferences("nickname", MODE_PRIVATE);
        userSeq = sharedPreferences.getLong("userSeq", 0L);
        toolbar = findViewById(R.id.barToolbar);
        swipeRefreshLayout = findViewById(R.id.barUpdate);
        barTitleTv = findViewById(R.id.barTitleTv);
        barLogoIv = findViewById(R.id.barProfileImage);
        openStateTv = findViewById(R.id.barOpenStateTv);
        runningTimeTv = findViewById(R.id.barRunningTimeTv);
        addressTv = findViewById(R.id.barAddressTv);
        phoneNumberTv = findViewById(R.id.barPhoneTv);
        openBtn = findViewById(R.id.barOpenBtn);
        breakBtn = findViewById(R.id.barBreakBtn);
        closeBtn = findViewById(R.id.barCloseBtn);
        callBtn = findViewById(R.id.barCall);
        firstStar = findViewById(R.id.barAvgStarOne);
        secondStar = findViewById(R.id.barAvgStarTwo);
        thirdStar = findViewById(R.id.barAvgStarThree);
        fourthStar = findViewById(R.id.barAvgStarFour);
        fifthStar = findViewById(R.id.barAvgStarFive);
        rateTv = findViewById(R.id.barAvgRate);
        openStyleTv = findViewById(R.id.barAvgOpenStyle);
        moodTv = findViewById(R.id.barAvgMood);
        priceTv = findViewById(R.id.barAvgPrice);
        toiletTv = findViewById(R.id.barAvgToilet);
        alcoholTv = findViewById(R.id.barAvgAlcohol);
        cleannessTv = findViewById(R.id.barAvgCleanness);
        heartButton = findViewById(R.id.barAddPatron);
        intent = getIntent();
        barSeq = intent.getLongExtra("seq", 0);
        rate = intent.getDoubleExtra("rate", -1.0);
        cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        };

        refreshInfo();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                serverAPI.getBar(bar.getSeq()).enqueue(new Callback<Bar>() {
                    @Override
                    public void onResponse(Call<Bar> call, Response<Bar> response) {
                        if (response.isSuccessful()) {
                            String openState = response.body().getCurrentState();
                            if(openState.equals("UNKNOWN")) {
                                openState = "등록된 오픈 정보 없음";
                                openStateTv.setText(openState);
                            }
                            else {
                                openStateTv.setText(openState);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Bar> call, Throwable t) {

                    }
                });
                refreshInfo();
                swipeRefreshLayout.setRefreshing(false); // 다 됐으면 새로고침 표시 제거
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bar.getPhoneNum()));
                startActivity(intent);
            }
        });

        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverAPI.getPatronBarList(userSeq).enqueue(new Callback<List<PatronBar>>() {
                    @Override
                    public void onResponse(Call<List<PatronBar>> call, Response<List<PatronBar>> response) {
                        boolean isAlreadyPatron = false;
                        for(PatronBar patronBar : response.body()) {
                            if(patronBar.getBarSeq().equals(barSeq)) {
                                isAlreadyPatron = true;
                                break;
                            }
                        }
                        if(isAlreadyPatron) {
                            serverAPI.deletePatronBar(userSeq, barSeq).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()) {
                                        heartButton.setImageResource(R.drawable.ic_heart);
                                        Toast.makeText(BarActivity.this, "단골 가게에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(BarActivity.this, "네트워크 연결상태를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            serverAPI.putPatronBar(userSeq, barSeq).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()) {
                                        heartButton.setImageResource(R.drawable.ic_heart_red);
                                        Toast.makeText(BarActivity.this, "단골 가게에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PatronBar>> call, Throwable t) {

                    }
                });
            }
        });
        openListener = v -> changeState("OPEN");
        breakListener = v -> changeState("BREAK");
        closeListener = v-> changeState("CLOSE");
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(BarActivity.this, openListener, cancelListener, bar.getName() + "의 상태를 OPEN으로 변경하시겠습니까?");
                dialog.show();
            }
        });
        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(BarActivity.this, breakListener, cancelListener, bar.getName() + "의 상태를  BREAK로 변경하시겠습니까?");
                dialog.show();

            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog =  new ChangeOpenStateDialog(BarActivity.this, closeListener, cancelListener, bar.getName() + "의 상태를 CLOSE로 변경하시겠습니까?");
                dialog.show();
            }
        });
        barLogoIv.setBackground(new ShapeDrawable(new OvalShape()));
        barLogoIv.setClipToOutline(true);
        barLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarActivity.this, ImageActivity.class);
                i.putExtra("imageURL", bar.getPhotoURL());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    void refreshInfo() {
        serverAPI.getBar(barSeq).enqueue(new Callback<Bar>() {
            @Override
            public void onResponse(Call<Bar> call, Response<Bar> response) {
                if (response.isSuccessful()) {
                    bar = response.body();
                    barTitleTv.setText(bar.getName());
                    Glide.with(BarActivity.this).load(bar.getPhotoURL()).into(barLogoIv);
                    String openState = bar.getCurrentState();
                    if(openState.equals("UNKNOWN")) {
                        openState = "등록된 오픈 정보 없음";
                        openStateTv.setText(openState);
                    }
                    else {
                        openStateTv.setText(openState);
                    }
                    addressTv.setText(bar.getAddress());
                    runningTimeTv.setText(bar.getRunningTime());
                    phoneNumberTv.setText(bar.getPhoneNum());
                    if(bar.getAvgOpenStyle() == null || bar.getAvgOpenStyle().equals("UNKNOWN"))
                        openStyleTv.setText("정보 없음");
                    else
                        openStyleTv.setText(bar.getAvgOpenStyle());
                    if(bar.getAvgPrice() == null)
                        priceTv.setText("정보 없음");
                    else
                        priceTv.setText(bar.getAvgPrice());
                    if(bar.getAvgCleanness() == null)
                        cleannessTv.setText("정보 없음");
                    else
                        cleannessTv.setText(bar.getAvgCleanness());
                    if(bar.getAvgMood() == null)
                        moodTv.setText("정보 없음");
                    else
                        moodTv.setText(bar.getAvgMood());
                    if(bar.getAvgMainAlcohol() == null)
                        alcoholTv.setText("정보 없음");
                    else
                        alcoholTv.setText(bar.getAvgMainAlcohol());
                    if(bar.getAvgToilet() == null)
                        toiletTv.setText("정보 없음");
                    else
                        toiletTv.setText(bar.getAvgToilet());
                    serverAPI.getPatronBarList(userSeq).enqueue(new Callback<List<PatronBar>>() {
                        @Override
                        public void onResponse(Call<List<PatronBar>> call, Response<List<PatronBar>> response) {
                            if(response.isSuccessful()) {
                                List<PatronBar> patronBarList = response.body();
                                for(PatronBar patronBar : patronBarList) {
                                    if(patronBar.getBarSeq().equals(barSeq)) {
                                        isPatron = true;
                                        break;
                                    }
                                }
                                if(isPatron)
                                    heartButton.setImageResource(R.drawable.ic_heart_red);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PatronBar>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Bar> call, Throwable t) {

            }
        });

        serverAPI.getBarAvgRate(barSeq).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Log.d("별점", "들어옴");
                if(response.isSuccessful()) {
                    Double rate = response.body();
                    rateTv.setText(String.format("%.1f", rate));
                    if (rate <= 0.1) {
                        Log.d("별점", "없음");
                        firstStar.setImageResource(R.drawable.ic_star_gray);
                        secondStar.setImageResource(R.drawable.ic_star_gray);
                        thirdStar.setImageResource(R.drawable.ic_star_gray);
                        fourthStar.setImageResource(R.drawable.ic_star_gray);
                        fifthStar.setImageResource(R.drawable.ic_star_gray);
                        rateTv.setText("");
                    }
                    else if (rate < 1.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_border_red);
                        thirdStar.setImageResource(R.drawable.ic_star_border_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);

                    }
                    else if(rate < 1.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_half_red);
                        thirdStar.setImageResource(R.drawable.ic_star_border_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);

                    }
                    else if (rate < 2.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_border_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);

                    }
                    else if (rate < 2.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_half_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 3.25) {

                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_border_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 3.75) {

                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_half_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 4.25) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_border_red);
                    }
                    else if (rate < 4.75) {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_half_red);
                    }
                    else {
                        firstStar.setImageResource(R.drawable.ic_star_red);
                        secondStar.setImageResource(R.drawable.ic_star_red);
                        thirdStar.setImageResource(R.drawable.ic_star_red);
                        fourthStar.setImageResource(R.drawable.ic_star_red);
                        fifthStar.setImageResource(R.drawable.ic_star_red);
                    }
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.d("평균평점", t.getMessage());
            }
        });
    }
    void changeState(String openState) {
        BarOpenReview barOpenReview = new BarOpenReview();
        barOpenReview.setBarSeq(bar.getSeq());
        barOpenReview.setUserSeq(userSeq);
        barOpenReview.setOpenState(openState);
        serverAPI.putBarOpenReview(barOpenReview).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    if(openState.equals("OPEN")) {
                        Toast.makeText(BarActivity.this, bar.getName() + "의 오픈 정보가 " + openState + "으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(BarActivity.this, bar.getName() + "의 오픈 정보가 " + openState + "로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    openStateTv.setText(openState);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}