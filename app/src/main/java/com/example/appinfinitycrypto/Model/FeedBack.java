package com.example.appinfinitycrypto.Model;

public class FeedBack {
    public String title;
    public String description;
    public String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FeedBack(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    public FeedBack() {

    }
}
