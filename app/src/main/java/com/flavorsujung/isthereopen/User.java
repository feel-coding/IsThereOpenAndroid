package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("seq")
    private Integer seq;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
