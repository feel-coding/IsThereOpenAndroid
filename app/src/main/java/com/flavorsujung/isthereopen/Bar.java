package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Bar {
    @SerializedName("seq")
    private Integer seq;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("runningTime")
    private String runningTime;

    @SerializedName("rate")
    private Double rate;

    @SerializedName("currentState")
    private Integer currentState;

    @SerializedName("photoURL")
    private String photoURL;

    @SerializedName("lastUpdate")
    private Date lastUpdate;

    @SerializedName("barInfoReviewList")
    List<CafeInfoReview> barInfoReviewList;

    @SerializedName("barOpenReviewList")
    List<CafeOpenReview> barOpenReviewList;

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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<CafeInfoReview> getBarInfoReviewList() {
        return barInfoReviewList;
    }

    public void setBarInfoReviewList(List<CafeInfoReview> barInfoReviewList) {
        this.barInfoReviewList = barInfoReviewList;
    }

    public List<CafeOpenReview> getBarOpenReviewList() {
        return barOpenReviewList;
    }

    public void setBarOpenReviewList(List<CafeOpenReview> barOpenReviewList) {
        this.barOpenReviewList = barOpenReviewList;
    }
}
