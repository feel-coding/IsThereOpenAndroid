package com.flavorsujung.isthereopen;

import java.util.Date;

public class Store {
    int type; // 0 카페, 1 식당, 2 술집
    Long seq;
    String photoUrl;
    String name;
    String openState;
    Date latestUpdate;
    String runtime;
    double avgRate;
    boolean isPatron;

    private Integer cafeManyPlug;
    private Integer cafeCheap;
    private Integer cafeLittlePeople;
    private Integer cafeStayLong;
    private Integer cafeLight;
    private Integer cafeStable;
    private Integer cafeNotLow;
    private Integer cafeShortWaiting;

    private Integer restaurantShortWaiting;
    private Integer restaurantClean;
    private Integer restaurantCheap;
    private Integer restaurantTakeout;
    private Integer restaurantStable;
    private Integer restaurantEatAlone;


    public void setName(String name) {
        this.name = name;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public void setLatestUpdate(Date latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getOpenState() {
        return openState;
    }

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public String getRuntime() {
        return runtime;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public boolean isPatron() {
        return isPatron;
    }

    public void setPatron(boolean patron) {
        isPatron = patron;
    }

    public Integer getCafeManyPlug() {
        return cafeManyPlug;
    }

    public void setCafeManyPlug(Integer cafeManyPlug) {
        this.cafeManyPlug = cafeManyPlug;
    }

    public Integer getCafeCheap() {
        return cafeCheap;
    }

    public void setCafeCheap(Integer cafeCheap) {
        this.cafeCheap = cafeCheap;
    }

    public Integer getCafeLittlePeople() {
        return cafeLittlePeople;
    }

    public void setCafeLittlePeople(Integer cafeLittlePeople) {
        this.cafeLittlePeople = cafeLittlePeople;
    }

    public Integer getCafeStayLong() {
        return cafeStayLong;
    }

    public void setCafeStayLong(Integer cafeStayLong) {
        this.cafeStayLong = cafeStayLong;
    }

    public Integer getCafeLight() {
        return cafeLight;
    }

    public void setCafeLight(Integer cafeLight) {
        this.cafeLight = cafeLight;
    }

    public Integer getCafeStable() {
        return cafeStable;
    }

    public void setCafeStable(Integer cafeStable) {
        this.cafeStable = cafeStable;
    }

    public Integer getCafeNotLow() {
        return cafeNotLow;
    }

    public void setCafeNotLow(Integer cafeNotLow) {
        this.cafeNotLow = cafeNotLow;
    }

    public Integer getCafeShortWaiting() {
        return cafeShortWaiting;
    }

    public void setCafeShortWaiting(Integer cafeShortWaiting) {
        this.cafeShortWaiting = cafeShortWaiting;
    }

    public Integer getRestaurantShortWaiting() {
        return restaurantShortWaiting;
    }

    public void setRestaurantShortWaiting(Integer restaurantShortWaiting) {
        this.restaurantShortWaiting = restaurantShortWaiting;
    }

    public Integer getRestaurantClean() {
        return restaurantClean;
    }

    public void setRestaurantClean(Integer restaurantClean) {
        this.restaurantClean = restaurantClean;
    }

    public Integer getRestaurantCheap() {
        return restaurantCheap;
    }

    public void setRestaurantCheap(Integer restaurantCheap) {
        this.restaurantCheap = restaurantCheap;
    }

    public Integer getRestaurantTakeout() {
        return restaurantTakeout;
    }

    public void setRestaurantTakeout(Integer restaurantTakeout) {
        this.restaurantTakeout = restaurantTakeout;
    }

    public Integer getRestaurantStable() {
        return restaurantStable;
    }

    public void setRestaurantStable(Integer restaurantStable) {
        this.restaurantStable = restaurantStable;
    }

    public Integer getRestaurantEatAlone() {
        return restaurantEatAlone;
    }

    public void setRestaurantEatAlone(Integer restaurantEatAlone) {
        this.restaurantEatAlone = restaurantEatAlone;
    }
}
