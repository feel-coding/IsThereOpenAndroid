package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("seq")
    private Long seq;

    @SerializedName("name")
    private String name;

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
}
