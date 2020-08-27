package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Restaurant {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("phoneNum")
    private String phoneNum;

    @SerializedName("runningTime")
    private String runningTime;

    @SerializedName("avgRate")
    private Double avgRate;

    @SerializedName("currentState")
    private String currentState;

    @SerializedName("photoUrl")
    private String photoURL;

    @SerializedName("lastUpdate")
    private Date lastUpdate;

    private String avgWaitingTime;
    private String avgCleanness;
    private String avgPrice;
    private String avgTakeOut;
    private String avgEatAlone;
    private String avgOpenStyle;
    private Integer shortWaiting;
    private Integer clean;
    private Integer cheap;
    private Integer takeout;
    private Integer stable;
    private Integer eatAlone;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public Double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Double avgRate) {
        this.avgRate = avgRate;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
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

    public String getAvgWaitingTime() {
        return avgWaitingTime;
    }

    public void setAvgWaitingTime(String avgWaitingTime) {
        this.avgWaitingTime = avgWaitingTime;
    }

    public String getAvgCleanness() {
        return avgCleanness;
    }

    public void setAvgCleanness(String avgCleanness) {
        this.avgCleanness = avgCleanness;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getAvgTakeOut() {
        return avgTakeOut;
    }

    public void setAvgTakeOut(String avgTakeOut) {
        this.avgTakeOut = avgTakeOut;
    }

    public String getAvgEatAlone() {
        return avgEatAlone;
    }

    public void setAvgEatAlone(String avgEatAlone) {
        this.avgEatAlone = avgEatAlone;
    }

    public String getAvgOpenStyle() {
        return avgOpenStyle;
    }

    public void setAvgOpenStyle(String avgOpenStyle) {
        this.avgOpenStyle = avgOpenStyle;
    }

    public Integer getShortWaiting() {
        return shortWaiting;
    }

    public void setShortWaiting(Integer shortWaiting) {
        this.shortWaiting = shortWaiting;
    }

    public Integer getClean() {
        return clean;
    }

    public void setClean(Integer clean) {
        this.clean = clean;
    }

    public Integer getCheap() {
        return cheap;
    }

    public void setCheap(Integer cheap) {
        this.cheap = cheap;
    }

    public Integer getTakeout() {
        return takeout;
    }

    public void setTakeout(Integer takeout) {
        this.takeout = takeout;
    }

    public Integer getStable() {
        return stable;
    }

    public void setStable(Integer stable) {
        this.stable = stable;
    }

    public Integer getEatAlone() {
        return eatAlone;
    }

    public void setEatAlone(Integer eatAlone) {
        this.eatAlone = eatAlone;
    }
}
