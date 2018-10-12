package com.easy.eightfivehundred.resell;

public class User {

    private String email, name, location, joinedDate;

    public User(){}

    public User(String email, String name, String location, String joinedDate) {
        this.email = email;
        this.name = name;
        this.location = location;
        this.joinedDate = joinedDate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getJoinedDate() {
        return joinedDate;
    }
}