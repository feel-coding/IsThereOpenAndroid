package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class CafeInfoReview {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("barSeq")
    private Long cafeSeq;

    @SerializedName("avgRate")
    private String rate;

    @SerializedName("waitingTimeTv")
    private String waitingTime;

    @SerializedName("priceTv")
    private String price;

    @SerializedName("customerNumTv")
    private String customerNum;

    @SerializedName("plugNumTv")
    private String plugNum;

    @SerializedName("tableHeightTv")
    private String tableHeight;

    @SerializedName("lightnessTv")
    private String lightness;

    @SerializedName("stayLongTv")
    private String stayLong; // POSSIBLE/NORMAL/UNCOMFORTABLE

    @SerializedName("openStyleTv")
    private String openStyle; // STABLE/NORMAL/UNSTABLE

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Long userSeq) {
        this.userSeq = userSeq;
    }

    public Long getCafeSeq() {
        return cafeSeq;
    }

    public void setCafeSeq(Long cafeSeq) {
        this.cafeSeq = cafeSeq;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    public String getPlugNum() {
        return plugNum;
    }

    public void setPlugNum(String plugNum) {
        this.plugNum = plugNum;
    }

    public String getTableHeight() {
        return tableHeight;
    }

    public void setTableHeight(String tableHeight) {
        this.tableHeight = tableHeight;
    }

    public String getLightness() {
        return lightness;
    }

    public void setLightness(String lightness) {
        this.lightness = lightness;
    }

    public String getStayLong() {
        return stayLong;
    }

    public void setStayLong(String stayLong) {
        this.stayLong = stayLong;
    }

    public String getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(String openStyle) {
        this.openStyle = openStyle;
    }
}
