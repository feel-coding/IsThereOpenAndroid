package com.flavorsujung.isthereopen;

import java.util.List;

public class Restaurant {
    private Integer seq;
    private String name;
    private String address;
    private String runningTime;
    private Double rate;
    private Integer currentState;
    private String photoURL;
    private List<RestaurantInfoReview> restaurantInfoReviewList;
    private List<RestaurantOpenReview> restaurantOpenReviewList;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public List<RestaurantInfoReview> getRestaurantInfoReviewList() {
        return restaurantInfoReviewList;
    }

    public void setRestaurantInfoReviewList(List<RestaurantInfoReview> restaurantInfoReviewList) {
        this.restaurantInfoReviewList = restaurantInfoReviewList;
    }

    public List<RestaurantOpenReview> getRestaurantOpenReviewList() {
        return restaurantOpenReviewList;
    }

    public void setRestaurantOpenReviewList(List<RestaurantOpenReview> restaurantOpenReviewList) {
        this.restaurantOpenReviewList = restaurantOpenReviewList;
    }
}
