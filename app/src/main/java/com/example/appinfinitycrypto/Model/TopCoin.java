package com.example.appinfinitycrypto.Model;

public class TopCoin {
    private int image;
    private String name;
    private String prize;

    public TopCoin(int image, String name, String prize){
        this.image = image;
        this.name = name;
        this.prize = prize;
    }

    public int getImage(){
        return image;
    }

    public String getName(){
        return name;
    }

    public String getPrize(){
        return prize;
    }
}
