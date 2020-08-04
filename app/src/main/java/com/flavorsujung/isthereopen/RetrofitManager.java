package com.flavorsujung.isthereopen;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.flavorsujung.isthereopen.ServerAPI.BASE_URL;

public class RetrofitManager {
    private static RetrofitManager instance = null; //자기자신을 변수로 가짐

    private RetrofitManager(){ //생성자를 이렇게 만들어서 아무도 new 못하게

    }

    public static RetrofitManager getInstance() {
        if(instance == null) {
            instance = new RetrofitManager(); //이게 싱글톤패턴
        }
        return instance;
    }

    public ServerAPI getServerAPI(Context context){
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        //쿠키설정 안해주면 401 에러 남
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ServerAPI.class);

    }
}
