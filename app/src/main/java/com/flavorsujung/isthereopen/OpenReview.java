package com.flavorsujung.isthereopen;

import java.util.Date;

public class OpenReview {
    Date date;
    String userName;
    String openState;

    public OpenReview(Date date, String userName, String openState) {
        this.date = date;
        this.userName = userName;
        this.openState = openState;
    }
}
