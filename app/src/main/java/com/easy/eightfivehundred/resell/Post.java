package com.easy.eightfivehundred.resell;

import android.net.Uri;

public class Post {

    private String name;
    private String brand;
    private String condition;
    private String description;
    private String location;
    private Integer price;
    private String title;
    private String image;
    //private String userId;

    public Post() {
    }

    public Post(String name, String brand, String condition, String description, String location, Integer price, String title, String image/*, String userId*/) {
        this.name = name;
        this.brand = brand;
        this.condition = condition;
        this.description = description;
        this.location = location;
        this.price = price;
        this.title = title;
        this.image = image;
        //this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Integer getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

//    public String getUserId() {
//        return userId;
//    }
}
