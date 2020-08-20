package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RestaurantOpenReview {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("restaurantSeq")
    private Long restaurantSeq;

    @SerializedName("openState")
    private String openState;

    @SerializedName("createdAt")
    private Date createdAt;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public Long getRestaurantSeq() {
        return restaurantSeq;
    }

    public void setRestaurantSeq(Long restaurantSeq) {
        this.restaurantSeq = restaurantSeq;
    }

    public String getOpenState() {
        return openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
