package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Cafe {

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
    private String avgPrice;
    private String avgCustomerNum;
    private String avgPlugNum;
    private String avgTableHeight;
    private String avgLightness;
    private String avgStayLong;
    private String avgOpenStyle;
    private Integer manyPlug;
    private Integer cheap;
    private Integer littlePeople;
    private Integer stayLong;
    private Integer light;
    private Integer stable;
    private Integer notLow;
    private Integer shortWaiting;

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

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getAvgCustomerNum() {
        return avgCustomerNum;
    }

    public void setAvgCustomerNum(String avgCustomerNum) {
        this.avgCustomerNum = avgCustomerNum;
    }

    public String getAvgPlugNum() {
        return avgPlugNum;
    }

    public void setAvgPlugNum(String avgPlugNum) {
        this.avgPlugNum = avgPlugNum;
    }

    public String getAvgTableHeight() {
        return avgTableHeight;
    }

    public void setAvgTableHeight(String avgTableHeight) {
        this.avgTableHeight = avgTableHeight;
    }

    public String getAvgLightness() {
        return avgLightness;
    }

    public void setAvgLightness(String avgLightness) {
        this.avgLightness = avgLightness;
    }

    public String getAvgStayLong() {
        return avgStayLong;
    }

    public void setAvgStayLong(String avgStayLong) {
        this.avgStayLong = avgStayLong;
    }

    public String getAvgOpenStyle() {
        return avgOpenStyle;
    }

    public void setAvgOpenStyle(String avgOpenStyle) {
        this.avgOpenStyle = avgOpenStyle;
    }

    public Integer getManyPlug() {
        return manyPlug;
    }

    public void setManyPlug(Integer manyPlug) {
        this.manyPlug = manyPlug;
    }

    public Integer getCheap() {
        return cheap;
    }

    public void setCheap(Integer cheap) {
        this.cheap = cheap;
    }

    public Integer getLittlePeople() {
        return littlePeople;
    }

    public void setLittlePeople(Integer littlePeople) {
        this.littlePeople = littlePeople;
    }

    public Integer getStayLong() {
        return stayLong;
    }

    public void setStayLong(Integer stayLong) {
        this.stayLong = stayLong;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }

    public Integer getStable() {
        return stable;
    }

    public void setStable(Integer stable) {
        this.stable = stable;
    }

    public Integer getNotLow() {
        return notLow;
    }

    public void setNotLow(Integer notLow) {
        this.notLow = notLow;
    }

    public Integer getShortWaiting() {
        return shortWaiting;
    }

    public void setShortWaiting(Integer shortWaiting) {
        this.shortWaiting = shortWaiting;
    }
}
