package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Bar {

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

    private String avgToilet;
    private String avgMood;
    private String avgMainAlcohol;
    private String avgPrice;
    private String avgCleanness;
    private String avgOpenStyle;
    private Integer separate;
    private Integer notLoud;
    private Integer cheap;
    private Integer soju;
    private Integer beer;
    private Integer wine;
    private Integer makgeolli;
    private Integer vodka;
    private Integer clean;
    private Integer stable;

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

    public String getAvgToilet() {
        return avgToilet;
    }

    public void setAvgToilet(String avgToilet) {
        this.avgToilet = avgToilet;
    }

    public String getAvgMood() {
        return avgMood;
    }

    public void setAvgMood(String avgMood) {
        this.avgMood = avgMood;
    }

    public String getAvgMainAlcohol() {
        return avgMainAlcohol;
    }

    public void setAvgMainAlcohol(String avgMainAlcohol) {
        this.avgMainAlcohol = avgMainAlcohol;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getAvgCleanness() {
        return avgCleanness;
    }

    public void setAvgCleanness(String avgCleanness) {
        this.avgCleanness = avgCleanness;
    }

    public String getAvgOpenStyle() {
        return avgOpenStyle;
    }

    public void setAvgOpenStyle(String avgOpenStyle) {
        this.avgOpenStyle = avgOpenStyle;
    }

    public Integer getSeparate() {
        return separate;
    }

    public void setSeparate(Integer separate) {
        this.separate = separate;
    }

    public Integer getNotLoud() {
        return notLoud;
    }

    public void setNotLoud(Integer notLoud) {
        this.notLoud = notLoud;
    }

    public Integer getCheap() {
        return cheap;
    }

    public void setCheap(Integer cheap) {
        this.cheap = cheap;
    }

    public Integer getSoju() {
        return soju;
    }

    public void setSoju(Integer soju) {
        this.soju = soju;
    }

    public Integer getBeer() {
        return beer;
    }

    public void setBeer(Integer beer) {
        this.beer = beer;
    }

    public Integer getWine() {
        return wine;
    }

    public void setWine(Integer wine) {
        this.wine = wine;
    }

    public Integer getMakgeolli() {
        return makgeolli;
    }

    public void setMakgeolli(Integer makgeolli) {
        this.makgeolli = makgeolli;
    }

    public Integer getVodka() {
        return vodka;
    }

    public void setVodka(Integer vodka) {
        this.vodka = vodka;
    }

    public Integer getClean() {
        return clean;
    }

    public void setClean(Integer clean) {
        this.clean = clean;
    }

    public Integer getStable() {
        return stable;
    }

    public void setStable(Integer stable) {
        this.stable = stable;
    }
}
