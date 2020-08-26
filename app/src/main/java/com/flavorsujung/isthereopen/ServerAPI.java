package com.flavorsujung.isthereopen;

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
//    String BASE_URL = "http://210.125.96.73:8081"; //학교 컴퓨터 서버 url
//    String BASE_URL = "http://13.125.64.62:8081"; //aws 서버 url
    String BASE_URL = "http://192.168.1.10:8081"; //내 컴퓨터 동적 ip

    //----------카페 API---------------
    @GET("/cafe/{cafeSeq}/openState")// 카페 오픈 여부 조회
    Call<String> getCafeCurrentState(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/all")// 카페 리스트 조회
    Call<List<Cafe>> getCafeList();

    @GET("/cafe/{cafeSeq}")// 카페 조회
    Call<Cafe> getCafe(@Path("cafeSeq") Long cafeSeq);

    @PUT("/cafe") //카페 추가
    Call<Void> putCafe(@Body Cafe cafe);
//    Call<Void> putCafe(@Query("name") String name, @Query("address") String address,
//                       @Query("runningTime") String runningTime, @Query("phoneNum") String phoneNum, @Query("photoURL") String photoURL);

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

    @PUT("/cafe/infoReview") // 카페 정보 리뷰 작성
    Call<Void> putCafeInfoReview(@Body CafeInfoReview cafeInfoReview);

    @GET("/cafe/{cafeSeq}/infoReview") //카페 정보 리뷰 리스트 조회
    Call<List<CafeInfoReview>> getCafeInfoReviewList(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/openReview") //카페 오픈 리뷰 조회
    Call<List<CafeOpenReview>> getCafeOpenReviewList(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/openStyle")
    Call<List<String>> getCafeAvgOpenStyle(@Path("cafeSeq") Long cafeSeq);


    @GET("/cafe/{cafeSeq}/waitingTime")
    Call<List<String>> getCafeAvgWaitingTime(@Path("cafeSeq") Long cafeSeq);


    @GET("/cafe/{cafeSeq}/price")
    Call<List<String>> getCafeAvgPrice(@Path("cafeSeq") Long cafeSeq);


    @GET("/cafe/{cafeSeq}/customerNum")
    Call<List<String>> getAvgCustomerNum(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/stayLong")
    Call<List<String>> getAvgStayLong(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/plugNum")
    Call<List<String>> getAvgPlugNum(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/tableHeight")
    Call<List<String>> getCafeAvgTableHeight(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/lightness")
    Call<List<String> > getCafeAvgLightness(@Path("cafeSeq") Long cafeSeq);

    @GET("/cafe/{cafeSeq}/avgRate")
    Call<Double> getCafeAvgRate(@Path("cafeSeq") Long cafeSeq);

    @PUT("/cafe/openReview") //카페 오픈리뷰 작성
    Call<Void> putCafeOpenReview(@Body CafeOpenReview cafeOpenReview/*@Path("cafeSeq") Long cafeSeq, @Query("userSeq") Long userSeq, @Query("openState") String openState*/);

    @GET("/user/{seq}/patronCafe/all") //내 단골카페 리스트 조회
    Call<List<PatronCafe>> getPatronCafeList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronCafe")// 단골 카페 추가
    Call<Void> putPatronCafe(@Path("seq") Long userSeq, @Query("cafeSeq") Long cafeSeq);

    @DELETE("/user/{seq}/patronCafe") //단골 카페 삭제
    Call<Void> deletePatronCafe(@Path("seq") Long userSeq, @Query("cafeSeq") Long cafeSeq);


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

    @PUT("/restaurant/infoReview") // 음식점 정보리뷰 작성
    Call<Void> putRestaurantInfoReview(@Body RestaurantInfoReview restaurantInfoReview
            /*@Path("restaurantSeq") Long restaurantSeq,
            @Query("userSeq") Long userSeq,
            @Query("rate") String rate, @Query("waitingTime") String waitingTime,
            @Query("cleanness") String cleanness,@Query("price") String price,
            @Query("takeOut") String takeOut, @Query("eatAlone") String eatAlone, @Query("openStyle") String openStyle*/);

    @GET("/restaurant/{restaurantSeq}/infoReview") //음식점 정보리뷰 리스트 조회
    Call<List<RestaurantInfoReview>> getRestaurantInfoReviewList(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/avgRate") // 음식점 평균 평점 조회
    Call<Double> getRestaurantAvgRate(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/eatAlone")
    Call<List<String>> getRestaurantAvgEatAlone(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/waitingTime")
    Call<List<String>> getAvgRestaurantWaitingTime(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/takeout")
    Call<List<String>> getAvgTakeOut(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/openStyle")
    Call<List<String>> getRestaurantAvgOpenStyle(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/price")
    Call<List<String>> getAvgRestaurantPrice(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/restaurant/{restaurantSeq}/cleanness")
    Call<List<String>> getAvgRestaurantCleanness(@Path("restaurantSeq") Long restaurantSeq);

    @PUT("/restaurant/openReview") //음식점 오픈리뷰 작성
    Call<Void> putRestaurantOpenReview(@Body RestaurantOpenReview restaurantOpenReview/*@Path("restaurantSeq") Long restaurantSeq,  @Query("userSeq") Long userSeq, @Query("openState") String openState*/);

    @GET("/restaurant/{restaurantSeq}/openReview") //특정 음식점의 오픈 리뷰 리스트 조회
    Call<List<RestaurantOpenReview>> getRestaurantOpenReviewList(@Path("restaurantSeq") Long restaurantSeq);

    @GET("/user/{seq}/patronRestaurant/all") //내 단골 음식점 리스트 조회
    Call<List<PatronRestaurant>> getPatronRestaurantList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronRestaurant") //단골 음식점 추가
    Call<Void> putPatronRestaurant(@Path("seq") Long userSeq, @Query("restaurantSeq") Long restaurantSeq);

    @DELETE("/user/{seq}/patronRestaurant") //단골 카페 삭제
    Call<Void> deletePatronRestaurant(@Path("seq") Long userSeq, @Query("restaurantSeq") Long restaurantSeq);



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

    @PUT("/bar/infoReview")// 술집 정보 리뷰 추가
    Call<Void> putBarInfoReview(@Body BarInfoReview barInfoReview);

    @GET("/bar/{barSeq}/infoReview") //특정 술집의 정보리뷰 리스트 조회
    Call<List<BarInfoReview>> getBarInfoReviewList(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/toilet")
    Call<List<String>> getAvgToilet(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/openStyle")
    Call<List<String>> getBarAvgOpenStyle(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/mood")
    Call<List<String>> getAvgMood(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/alcohol")
    Call<List<String>> getAvgAlcohol(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/cleanness")
    Call<List<String>> getBarAvgCleanness(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/price")
    Call<List<String>> getBarAvgPrice(@Path("barSeq") Long barSeq);

    @GET("/bar/{barSeq}/avgRate")
    Call<Double> getBarAvgRate(@Path("barSeq") Long barSeq);

    @GET("/bar/{seq}/openReview") //술집 오픈 리뷰 리스트 조회
    Call<List<BarOpenReview>> getBarOpenReviewList(@Path("seq") Long seq);

    @PUT("/bar/openReview") //술집 오픈 리뷰 작성
    Call<Void> putBarOpenReview(@Body BarOpenReview barOpenReview);

    @GET("/user/{seq}/patronBar/all")// 내 단골 술집 리스트 조회
    Call<List<PatronBar>> getPatronBarList(@Path("seq") Long userSeq);

    @PUT("/user/{seq}/patronBar")// 단골 술집 추가
    Call<Void> putPatronBar(@Path("seq") Long userSeq, @Query("barSeq") Long barSeq);

    @DELETE("/user/{seq}/patronBar") //단골 술집 삭제
    Call<Void> deletePatronBar(@Path("seq") Long userSeq, @Query("barSeq") Long barSeq);


    //--------사용자 API------------
    @GET("/user/{seq}") //사용자 조회
    Call<User> getUser(@Path("seq") Long userSeq);

    @GET("/user")
    Call<Long> getUserSeq(@Query("name") String name);

    @GET("/user/all") //사용자 리스트 조회
    Call<List<User>> getUserList();

    @PUT("/user") //사용자 추가
    Call<Long> putUser(@Query("name") String name); //이미 존재하는 이름이면 -1 반환, 성공적이면 0 반환

    @POST("/user/{seq}") //사용자 이름 변경
    Call<Void> postUserName(@Query("seq") Long userSeq, @Query("name") String name);

}
