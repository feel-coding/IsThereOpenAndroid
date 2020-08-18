package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

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
}
