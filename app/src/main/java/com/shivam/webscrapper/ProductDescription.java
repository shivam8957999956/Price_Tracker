package com.shivam.webscrapper;

public class ProductDescription {
    String type,value;
public ProductDescription(){

}
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductDescription(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
