package com.shivam.webscrapper;

public class SearchHistoryHelperClass {
    String title,image,url,time;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SearchHistoryHelperClass(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SearchHistoryHelperClass(String title, String image, String url, String time,int id) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.time = time;
        this.id = id;
    }
}
