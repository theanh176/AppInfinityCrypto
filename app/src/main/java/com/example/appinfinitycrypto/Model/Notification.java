package com.example.appinfinitycrypto.Model;

public class Notification {
    private int icon;
    private String content;
    private String time;
    private String link;

    public Notification(int icon, String content, String time, String link) {
        this.icon = icon;
        this.content = content;
        this.time = time;
        this.link = link;
    }

    public int getIcon() {
        return icon;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getLink() {
        return link;
    }
}
