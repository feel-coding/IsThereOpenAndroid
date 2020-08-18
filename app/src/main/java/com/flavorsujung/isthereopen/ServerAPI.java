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
    String BASE_URL = "http://210.125.96.73:8081"; //서버 url
//    String BASE_URL = "http://13.125.64.62:8081";

    //----------카페 API---------------
    @GET("/cafe/{cafeSeq}/openState")// 카페 오픈 여부 조회
    Call<String> getCafeCurrentState(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/all")// 카페 리스트 조회
    Call<List<Cafe>> getCafeList();

    @GET("/cafe/{cafeSeq}")// 카페 조회
    Call<Cafe> getCafe(@Path("cafeSeq") Long cafeSeq);

    @PUT("/cafe") //카페 추가
    Call<Void> putCafe(@Query("name") String name, @Query("address") String address,
                       @Query("runningTime") String runningTime, @Query("phoneNum") String phoneNum, @Query("photoURL") String photoURL);

    @POST("/cafe/{cafeSeq}/name")//카페 이름 변경
    Call<Void> changeCafeName(@Path("cafeSeq") Long cafeSeq, @Query("name") String name);

    @POST("/cafe/{cafeSeq}/runningTime")//카페 운영시간 변경
    Call<Void> changeCafeRunningTime(@Path("cafeSeq") Long cafeSeq, @Query("runningTime") String runningTime);

    @POST("/cafe/{cafeSeq}/photoUrl")//카페 프로필 사진 변경
    Call<Void> changeCafePhoto(@Path("cafeSeq") Long cafeSeq, @Query("photoUrl") String photoUrl);

    @POST("/cafe/{cafeSeq}/address")// 카페 주소 변경
    Call<Void> changeCafeAddress(@Path("cafeSeq") Long cafeSeq, @Query("address") String address);

    @DELETE("/cafe")//카페 삭제
    Call<Void> deleteCafe(@Query("cafeSeq") Long cafeSeq);

    @PUT("/cafe/{cafeSeq}/infoReview") // 카페 정보 리뷰 작성 (인자가 11개로 좀 많습니다....)
    Call<Void> putCafeInfoReview(
            @Path("cafeSeq") Long cafeSeq,
            @Query("userSeq") Long userSeq,
            @Query("openStyle") String openStyle,
            @Query("waitingTime") String waitingTime,
            @Query("price") String price,
            @Query("customerNum") String customerNum,
            @Query("plugNum") String plugNum,
            @Query("rate") String rate,
            @Query("tableHeight") String tableHeight,
            @Query("lightness") String lightness,
            @Query("stayLong") String stayLong);

    @GET("/cafe/{cafeSeq}/infoReview") //카페 정보 리뷰 리스트 조회
    Call<List<CafeInfoReview>> getCafeInfoReviewList(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/openReview") //카페 오픈 리뷰 조회
    Call<List<CafeOpenReview>> getCafeOpenReviewList(@Path("cafeSeq") Long cafeSeq);

    @PUT("/cafe/{cafeSeq}/openReview") //카페 오픈리뷰 작성
    Call<Void> putCafeOpenReview(@Path("cafeSeq") Long cafeSeq, @Query("userSeq") Long userSeq, @Query("openState") String openState);

    @GET("/user/{seq}/patronCafe/all") //내 단골카페 리스트 조회
    Call<List<PatronCafe>> getPatronCafeList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronCafe")// 단골 카페 추가
    Call<Void> putPatronCafe(@Path("seq") Long userSeq, @Query("cafeSeq") Long cafeSeq);



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
            @Query("rate") String rate, @Query("waitingTime") String waitingTime,
            @Query("cleanness") String cleanness,@Query("price") String price,
            @Query("takeOut") String takeOut, @Query("eatAlone") String eatAlone, @Query("openStyle") String openStyle);

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
            @Query("rate") String rate,
            @Query("toilet") String toilet,
            @Query("mood") String mood,
            @Query("mainAlcohol") String mainAlcohol,
            @Query("price") String price,
            @Query("cleanness") String cleanness,
            @Query("openStyle") String openStyle);

    @GET("/bar/{barSeq}/infoReview") //특정 술집의 정보리뷰 리스트 조회
    Call<List<BarInfoReview>> getRestaurantInfoReviewList(@Path("barSeq") Long barSeq);

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
    Call<Integer> putUser(@Query("name") String name); //이미 존재하는 이름이면 -1 반환, 성공적이면 0 반환

    @POST("/user/{seq}") //사용자 이름 변경
    Call<Void> postUserName(@Query("seq") Long userSeq, @Query("name") String name);

}
