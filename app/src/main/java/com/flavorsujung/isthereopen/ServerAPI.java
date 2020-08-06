package com.flavorsujung.isthereopen;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerAPI {
    //String BASE_URL = "http://210.125.96.73:8081"; //서버 url
    String BASE_URL = "http://13.125.64.62:8081";
    @GET("/user/{seq}")
    Call<User> getUser(@Path("seq")Integer seq);

    @GET("/user/all")
    Call<User> getUserList();

    @PUT("/user/{seq}")
    Call<Void> putUser(@Path("seq") Integer seq, @Body String id, @Body String name);

    @PUT("/cafeOpenReview/{seq}")
    Call<Void> putCafeOpenReview(@Path("seq") Integer seq, @Body Integer cafeSeq, @Body Integer userSeq, @Body Integer openState);

    @GET("/cafe/all")
    Call<List<Cafe>> getCafeList();

    @GET("/restaurant/all")
    public List<Restaurant> getRestaurantList();

    @PUT("/cafe/{cafeSeq}/openReview") //오픈 리뷰 추가하는 API
    public void putCafeOpenReview(@Path("cafeSeq") Integer cafeSeq, @Body Integer userSeq, @Body Integer openState);


    /*

    @POST("/api/v1/users/signin")
    Call<Void> signInUser(@Body ReqUserSignIn reqUserSignIn);

    @POST("/api/v1/users/signup")
    Call<Void> signUpUser(@Body ReqUserSignUp reqUserSignUp);

    @POST("/api/v1/users/signout")
    Call<Void> signOut();

    @POST("/api/v1/friends/requests") //POST의 경우 Body 사용
    Call<Void> createFriendRequest(@Body ReqCreateFriendRequest reqCreateFriendRequest);

    @PUT("/api/v1/friends/requests/{seq}")
    Call<Void> updateFriendRequest(@Body ReqFriendRequestUpdate reqFriendRequestUpdate, @Path("seq")Integer seq);

    @GET("/api/v1/friends/requests")
    Call<List<FriendReq>> getFriendRequestList(@Query("direction") String direction, @Query("page") Integer page, @Query("page_size") Integer pageSize);

    @GET("/api/v1/friends")
    Call<List<Friend>> getFriendList();

    @GET("/api/v1/friends")
    Call<List<Friend>> getFriendList(@Query("query") String query);

    @PUT("/api/v1/users/me")
    Call<User> updateUserMe(@Body ReqUserUpdateMe reqUserUpdateMe);

    @GET("/api/v1/users")
    Call<List<EachUser>> getAllUsers(@Query("page") Integer page, @Query("page_size") Integer pageSize, @Query("query") String query);

    @GET("/api/v1/diaries")
    Call<List<Diary>> getDiaries(@Query("filterType") String filterType);

    @DELETE("/api/v1/diaries/{diarySeq}")
    Call<Void> deleteDiary(@Path("diarySeq") BigInteger diarySeq);

    @HTTP(method = "DELETE", path = "/api/v1/friends", hasBody = true)
    Call<Void> deleteFriend(@Body ReqFriendDelete reqFriendDelete);

    @POST("/api/v1/diaries/{diarySeq}/likes")
    Call<Void> likesDiary(@Path("diarySeq")BigInteger diarySeq);

    @DELETE("/api/v1/users/me")
    Call<Void> deleteMyAccount();*/
}
