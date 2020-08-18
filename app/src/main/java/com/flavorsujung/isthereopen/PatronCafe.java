package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class PatronCafe {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("cafeSeq")
    private Long cafeSeq;

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
}
