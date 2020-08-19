package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BarInfoReview {
    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("barSeq")
    private Long barSeq;

    @SerializedName("rate")
    private String rate;

    @SerializedName("toilet")
    private String toilet;

    @SerializedName("mood")
    private String mood;

    @SerializedName("mainAlcohol")
    private String mainAlcohol;

    @SerializedName("price")
    private String price;

    @SerializedName("cleanness")
    private String cleanness;

    @SerializedName("openStyle")
    private String openStyle;

    @SerializedName("createdAt")
    private Date createdAt;

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

    public Long getBarSeq() {
        return barSeq;
    }

    public void setBarSeq(Long barSeq) {
        this.barSeq = barSeq;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getMainAlcohol() {
        return mainAlcohol;
    }

    public void setMainAlcohol(String mainAlcohol) {
        this.mainAlcohol = mainAlcohol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCleanness() {
        return cleanness;
    }

    public void setCleanness(String cleanness) {
        this.cleanness = cleanness;
    }

    public String getOpenStyle() {
        return openStyle;
    }

    public void setOpenStyle(String openStyle) {
        this.openStyle = openStyle;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
