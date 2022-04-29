package com.example.appinfinitycrypto.Model;

public class Discover {
    private int image;
    private String title;
    private String day;

    public Discover(int image, String title, String day) {
        this.image = image;
        this.title = title;
        this.day = day;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }
}
