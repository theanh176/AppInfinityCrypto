package com.example.appinfinitycrypto.Model;

public class DataItem_Notify {
    private String content;
    private String time;
    private String title;
    private Boolean isNotify;

    public DataItem_Notify() {
    }

    public DataItem_Notify(String content, String title) {
        this.title = title;
        this.content = content;
    }

    public DataItem_Notify(String content, String time, String title, Boolean isNotify) {
        this.title = title;
        this.content = content;
        this.isNotify = isNotify;
        this.time = time;
    }

    public Boolean getNotify() {
        return isNotify;
    }

    public void setNotify(Boolean notify) {
        isNotify = notify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

