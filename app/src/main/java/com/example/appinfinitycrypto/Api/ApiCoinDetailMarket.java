package com.example.appinfinitycrypto.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface ApiCoinDetailMarket {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

}
