package com.example.appinfinitycrypto.Api;

import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.TopCoin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCoinMarket {

//    https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=fac03ee8-101c-4a60-86c3-b38e63d5f955&sort=market_cap&start=1&limit=100&cryptocurrency_type=tokens&convert=USD
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiCoinMarket apiCoinMarket = new Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiCoinMarket.class);

    @GET("v1/cryptocurrency/listings/latest")
    Call<Market> convertUsdToVnd(@Query("CMC_PRO_API_KEY") String CMC_PRO_API_KEY,
                                 @Query("sort") String sort,
                                 @Query("start") int start,
                                 @Query("limit") int limit,
                                 @Query("cryptocurrency_type") String cryptocurrency_type,
                                 @Query("convert") String convert);

    Call<TopCoin> convertUsdToVndTop(@Query("CMC_PRO_API_KEY") String CMC_PRO_API_KEY,
                                 @Query("sort") String sort,
                                 @Query("start") int start,
                                 @Query("limit") int limit,
                                 @Query("cryptocurrency_type") String cryptocurrency_type,
                                 @Query("convert") String convert);
}
