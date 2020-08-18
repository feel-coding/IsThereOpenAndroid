package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class PatronRestaurant {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("restaurantSeq")
    private Long restaurantSeq;

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
}
