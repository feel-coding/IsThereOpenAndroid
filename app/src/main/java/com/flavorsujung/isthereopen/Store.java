package com.flavorsujung.isthereopen;

import java.util.Date;

public class Store {
    int type; // 0 카페, 1 식당, 2 술집
    int seq;
    String photoUrl;
    String name;
    String openState;
    Date latestUpdate;
    String runtime;
    double rate;

    public Store(int type, int seq, String photoUrl, String name, String openState, Date latestUpdate, String runtime, double rate) {
        this.type = type;
        this.seq = seq;
        this.photoUrl = photoUrl;
        this.name = name;
        this.openState = openState;
        this.latestUpdate = latestUpdate;
        this.runtime = runtime;
        this.rate = rate;
    }

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

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
