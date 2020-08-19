package com.flavorsujung.isthereopen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerAPI {
//    String BASE_URL = "http://210.125.96.73:8081"; //학교 컴퓨터 서버 url
//    String BASE_URL = "http://13.125.64.62:8081"; //aws 서버 url
    String BASE_URL = "http://192.168.126.1:8081";

    //----------카페 API---------------
    @GET("/bar/{barSeq}/openState")// 카페 오픈 여부 조회
    Call<String> getCafeCurrentState(@Path("barSeq") Long cafeSeq);

    @GET("/bar/all")// 카페 리스트 조회
    Call<List<Cafe>> getCafeList();

    @GET("/bar/{barSeq}")// 카페 조회
    Call<Cafe> getCafe(@Path("barSeq") Long cafeSeq);

    @PUT("/bar") //카페 추가
    Call<Void> putCafe(@Query("name") String name, @Query("address") String address,
                       @Query("runningTime") String runningTime, @Query("phoneNum") String phoneNum, @Query("photoURL") String photoURL);

    @POST("/bar/{barSeq}/name")//카페 이름 변경
    Call<Void> changeCafeName(@Path("barSeq") Long cafeSeq, @Query("name") String name);

    @POST("/bar/{barSeq}/runningTime")//카페 운영시간 변경
    Call<Void> changeCafeRunningTime(@Path("barSeq") Long cafeSeq, @Query("runningTime") String runningTime);

    @POST("/bar/{barSeq}/photoUrl")//카페 프로필 사진 변경
    Call<Void> changeCafePhoto(@Path("barSeq") Long cafeSeq, @Query("photoUrl") String photoUrl);

    @POST("/bar/{barSeq}/address")// 카페 주소 변경
    Call<Void> changeCafeAddress(@Path("barSeq") Long cafeSeq, @Query("address") String address);

    @DELETE("/bar")//카페 삭제
    Call<Void> deleteCafe(@Query("barSeq") Long cafeSeq);

    @PUT("/bar/{barSeq}/infoReview") // 카페 정보 리뷰 작성 (인자가 11개로 좀 많습니다....)
    Call<Void> putCafeInfoReview(
            @Path("barSeq") Long cafeSeq,
            @Query("userSeq") Long userSeq,
            @Query("openStyleTv") String openStyle,
            @Query("waitingTimeTv") String waitingTime,
            @Query("priceTv") String price,
            @Query("customerNumTv") String customerNum,
            @Query("plugNumTv") String plugNum,
            @Query("avgRate") String rate,
            @Query("tableHeightTv") String tableHeight,
            @Query("lightnessTv") String lightness,
            @Query("stayLongTv") String stayLong);

    @GET("/bar/{barSeq}/infoReview") //카페 정보 리뷰 리스트 조회
    Call<List<CafeInfoReview>> getCafeInfoReviewList(@Path("barSeq") Long cafeSeq);

    @GET("/bar/{barSeq}/openReview") //카페 오픈 리뷰 조회
    Call<List<CafeOpenReview>> getCafeOpenReviewList(@Path("barSeq") Long cafeSeq);

    @PUT("/bar/{barSeq}/openReview") //카페 오픈리뷰 작성
    Call<Void> putCafeOpenReview(@Path("barSeq") Long cafeSeq, @Query("userSeq") Long userSeq, @Query("openState") String openState);

    @GET("/user/{seq}/patronCafe/all") //내 단골카페 리스트 조회
    Call<List<PatronCafe>> getPatronCafeList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronCafe")// 단골 카페 추가
    Call<Void> putPatronCafe(@Path("seq") Long userSeq, @Query("barSeq") Long cafeSeq);



    //------------음식점 API--------------
    @GET("/restaurant/{restaurantSeq}/openState") // 특정 음식점 오픈여부 조회
    Call<String> getRestaurantCurrentState(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/all") //음식점 리스트 조회
    Call<List<Restaurant>> getRestaurantList();

    @PUT("/restaurant") //음식점 추가
    Call<Void> putRestaurant(@Query("name") String name, @Query("address") String address,
                             @Query("runningTime") String runningTime, @Query("phoneNum") String phoneNum, @Query("photoURL") String photoURL);

    @GET("/restaurant/{restaurantSeq}") //음식점 조회
    Call<Restaurant> getRestaurant(@Path("restaurantSeq") Long restaurantSeq);

    @DELETE("/restaurant") //음식점 삭제
    Call<Void> deleteRestaurant(@Query("restaurantSeq") Long restaurantSeq);

    @PUT("/restaurant/{restaurantSeq}/infoReview") // 음식점 정보리뷰 작성
    Call<Void> putRestaurantInfoReview(
            @Path("restaurantSeq") Long restaurantSeq,
            @Query("userSeq") Long userSeq,
            @Query("avgRate") String rate, @Query("waitingTimeTv") String waitingTime,
            @Query("cleanness") String cleanness,@Query("priceTv") String price,
            @Query("takeOut") String takeOut, @Query("eatAlone") String eatAlone, @Query("openStyleTv") String openStyle);

    @GET("/restaurant/{restaurantSeq}/infoReview") //음식점 정보리뷰 리스트 조회
    Call<List<RestaurantInfoReview>> getRestaurantInfoReview(@Path("restaurantSeq") Long restaurantSeq);

    @PUT("/restaurant/{restaurantSeq}/openReview") //음식점 오픈리뷰 작성
    Call<Void> putRestaurantOpenReview(@Path("restaurantSeq") Long restaurantSeq,  @Query("userSeq") Long userSeq, @Query("openState") String openState);

    @GET("/restaurant/{restaurantSeq}/openReview") //특정 음식점의 오픈 리뷰 리스트 조회
    Call<List<RestaurantOpenReview>> getRestaurantOpenReviewList(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/user/{seq}/patronRestaurant/all") //내 단골 음식점 리스트 조회
    Call<List<PatronRestaurant>> getPatronRestaurantList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronRestaurant") //단골 음식점 추가
    Call<Void> putPatronRestaurant(@Path("seq") Long userSeq, @Query("restaurantSeq") Long restaurantSeq);


    //-------------술집 API----------------
    @GET("/bar/{barSeq}/openState") //술집 오픈 여부 조회
    Call<String> getCurrentState(@Path("barSeq") Long barSeq);

    @GET("/bar/all") //술집 리스트 조회
    Call<List<Bar>> getBarList();

    @GET("/bar/{barSeq}") //술집 조회
    Call<Bar> getBar(@Path("barSeq") Long barSeq);

    @PUT("/bar") //술집 추가
    Call<Void> putBar(@Query("name") String name, @Query("address") String address,
                      @Query("runningTime") String runningTime, @Query("phoneNum") String phoneNum, @Query("photoURL") String photoURL);

    @DELETE("/bar") //술집 삭제
    Call<Void> deleteBar(@Query("barSeq") Long barSeq);

    @PUT("/bar/{barSeq}/infoReview")// 술집 정보 리뷰 추가
    Call<Void> putBarInfoReview(
            @Path("barSeq") Long barSeq,
            @Query("userSeq") Long userSeq,
            @Query("avgRate") String rate,
            @Query("toilet") String toilet,
            @Query("mood") String mood,
            @Query("mainAlcohol") String mainAlcohol,
            @Query("priceTv") String price,
            @Query("cleanness") String cleanness,
            @Query("openStyleTv") String openStyle);

    @GET("/bar/{barSeq}/infoReview") //특정 술집의 정보리뷰 리스트 조회
    Call<List<BarInfoReview>> getBarInfoReviewList(@Path("barSeq") Long barSeq);

    @GET("/bar/{seq}/openReview") //술집 오픈 리뷰 리스트 조회
    Call<List<BarOpenReview>> getBarOpenReviewList(@Path("seq") Long seq);

    @PUT("/bar/{barSeq}/openReview") //술집 오픈 리뷰 작성
    Call<Void> putBarOpenReview(
            @Path("barSeq") Long barSeq,
            @Query("userSeq") Long userSeq,
            @Query("openState") String openState);

    @GET("/user/{seq}/patronBar/all")// 내 단골 술집 리스트 조회
    Call<List<PatronBar>> getPatronBarList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronBar")// 단골 술집 추가
    Call<Void> putPatronBar(@Path("seq") Long userSeq, @Query("barSeq") Long barSeq);

    //--------사용자 API------------
    @GET("/user/{seq}") //사용자 조회
    Call<User> getUser(@Path("seq") Long userSeq);

    @GET("/user/all") //사용자 리스트 조회
    Call<List<User>> getUserList();

    @PUT("/user") //사용자 추가
    Call<Long> putUser(@Query("name") String name); //이미 존재하는 이름이면 -1 반환, 성공적이면 0 반환

    @POST("/user/{seq}") //사용자 이름 변경
    Call<Void> postUserName(@Query("seq") Long userSeq, @Query("name") String name);

}
