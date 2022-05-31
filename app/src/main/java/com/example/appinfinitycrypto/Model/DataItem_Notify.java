package com.example.appinfinitycrypto.Model;

public class DataItem_Notify {
    private String description;
    private String title;

    public DataItem_Notify() {
    }

    public DataItem_Notify(String description, String title) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

