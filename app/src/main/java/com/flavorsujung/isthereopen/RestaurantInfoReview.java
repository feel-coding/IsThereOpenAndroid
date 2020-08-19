package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class RestaurantInfoReview {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("restaurantSeq")
    private Long restaurantSeq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("avgRate")
    private String rate;

    @SerializedName("waitingTimeTv")
    private String waitingTime;

    @SerializedName("cleanness")
    private String cleanness;

    @SerializedName("priceTv")
    private String price;

    @SerializedName("takeOut")
    private String takeOut;

    @SerializedName("eatAlone")
    private String eatAlone;

    @SerializedName("openStyleTv")
    private String openStyle;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getRestaurantSeq() {
        return restaurantSeq;
    }

    public void setRestaurantSeq(Long restaurantSeq) {
        this.restaurantSeq = restaurantSeq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getCleanness() {
        return cleanness;
    }

    public void setCleanness(String cleanness) {
        this.cleanness = cleanness;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTakeOut() {
        return takeOut;
    }

    public void setTakeOut(String takeOut) {
        this.takeOut = takeOut;
    }

    public String getEatAlone() {
        return eatAlone;
    }

    public void setEatAlone(String eatAlone) {
        this.eatAlone = eatAlone;
    }

    public String getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(String openStyle) {
        this.openStyle = openStyle;
    }
}
