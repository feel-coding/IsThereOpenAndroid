package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class Cafe {
    @SerializedName("seq")
    private Integer seq;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("runtime")
    private String runtime;
    @SerializedName("rate")
    private Double rate;
    @SerializedName("currentState")
    private Integer currentState;

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

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
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
}
