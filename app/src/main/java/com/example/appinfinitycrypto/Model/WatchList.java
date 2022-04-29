package com.example.appinfinitycrypto.Model;

public class WatchList {
    private int image;
    private int star;
    private String name;
    private String sign_name;
    private String prize;
    private String change;
    private int image_change;
    private int chart;


    public WatchList(int image, int image_change, int chart, int star,String name, String prize, String change, String sign_name) {
        this.image = image;
        this.name = name;
        this.prize = prize;
        this.star = star;
        this.change = change;
        this.image_change = image_change;
        this.chart = chart;
        this.sign_name = sign_name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrize() {
        return prize;
    }

    public String getChange() {
        return change;
    }

    public int getImage_change() {
        return image_change;
    }

    public int getChart() {
        return chart;
    }

    public String getSign_name() {
        return sign_name;
    }

    public int getStar() {
        return star;
    }
}
