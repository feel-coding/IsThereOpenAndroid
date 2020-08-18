package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BarOpenReview {

    @SerializedName("seq")
    private Long seq;

    @SerializedName("userSeq")
    private Long userSeq;

    @SerializedName("barSeq")
    private Long barSeq;

    @SerializedName("openState")
    private String openState;
}
