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
}
