package com.example.appinfinitycrypto.Api;

import com.example.appinfinitycrypto.Model.Discover;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiNew {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    //https://min-api.cryptocompare.com/data/v2/news/?lang=EN&api_key={283d7ecd8fc18b8a775b3feb651323c508943b922be9b5978fe299fe21f6f0d2}
    ApiNew apiNew = new Retrofit.Builder()
            .baseUrl("https://min-api.cryptocompare.com/data/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiNew.class);

    @GET("v2/news/?lang=EN")
    Call<Discover> convertUsdToVnd(@Query("api_key") String api_key);
}
