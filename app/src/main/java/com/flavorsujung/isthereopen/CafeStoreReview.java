package com.flavorsujung.isthereopen;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CafeStoreReview {
    private Long seq;
    private Long userSeq;
    private Long cafeSeq;
    String userName;
    Double rate;
    Date date;
    String waiting_time;
    String price;
    String customer_num;
    String plug_num;
    String table_height;
    String lightness;
    String long_possibility;
    String open_style;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public String getCustomer_num() {
        return customer_num;
    }

    public String getPlug_num() {
        return plug_num;
    }

    public String getTable_height() {
        return table_height;
    }

    public String getLightness() {
        return lightness;
    }

    public String getLong_possibility() {
        return long_possibility;
    }

    public String getOpen_style() {
        return open_style;
    }

    public Long getCafeSeq() {
        return cafeSeq;
    }

    public void setCafeSeq(Long cafeSeq) {
        this.cafeSeq = cafeSeq;
    }
    public void setRate(Double rate) {
        this.rate = rate;
    }
    public Double getRate() {
        return rate;
    }

    public void setDate(String latestUpdate) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setWaitingTime(String waiting_time) {
        this.waiting_time = waiting_time;
    }
    public String getWaiting_time(){
        return waiting_time;
    }

    public void setCustomer_num(String customer_num) {
        this.customer_num = customer_num;
    }

    public void setPlug_num(String plug_num) {
        this.plug_num = plug_num;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setWaiting_time(String waiting_time) {
        this.waiting_time = waiting_time;
    }

    public void setLightness(String lightness) {
        this.lightness = lightness;
    }

    public void setLong_possibility(String long_possibility) {
        this.long_possibility = long_possibility;
    }

    public void setOpen_style(String open_style) {
        this.open_style = open_style;
    }

    public void setTable_height(String table_height) {
        this.table_height = table_height;
    }
}
