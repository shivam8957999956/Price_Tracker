package com.shivam.webscrapper;

public class AmazonSearchResult {
    String title,image,price,link;
    public AmazonSearchResult(){

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AmazonSearchResult(String title, String image, String price, String link) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.link = link;
    }
}
