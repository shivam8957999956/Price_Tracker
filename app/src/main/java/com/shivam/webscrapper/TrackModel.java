package com.shivam.webscrapper;

public class TrackModel {
    String time;
    String Price;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public TrackModel(String time, String price) {
        this.time = time;
        Price = price;
    }
}
