package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class PatronBar {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("barSeq")
    private Long barSeq;

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
}
