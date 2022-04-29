package com.example.appinfinitycrypto.Model;

public class TopGainer {
    private int image;
    private String name;
    private String prize;

    public TopGainer(int image, String name, String prize){
        this.image = image;
        this.name = name;
        this.prize = prize;
    }

    public int getImage(){
        return image;
    };

    public String getName(){
        return name;
    };

    public String getPrize(){
        return prize;
    };
}
