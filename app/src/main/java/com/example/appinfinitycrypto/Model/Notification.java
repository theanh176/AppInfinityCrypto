package com.example.appinfinitycrypto.Model;

public class Notification {
    public String title;
    public String description;

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

    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public Notification() {

    }
}
