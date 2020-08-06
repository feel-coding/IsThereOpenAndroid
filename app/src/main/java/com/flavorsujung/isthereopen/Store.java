package com.flavorsujung.isthereopen;

public class Store {
    int type; // 0 카페, 1 식당, 2 술집
    String photoUrl;
    String name;
    String openState;
    String latestUpdate;
    String runtime;
    double rate;

    public Store(int type, String photoUrl, String name, String openState, String latestUpdate, String runtime, double rate) {
        this.type = type;
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

    public void setLatestUpdate(String latestUpdate) {
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
}
