package com.shivam.webscrapper;

public class TopDealsHelperClass {
    String title;
    String image;
    String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public TopDealsHelperClass(){

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public TopDealsHelperClass(String title, String image, String price,String url) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.URL = url;
    }

    String price;

}
