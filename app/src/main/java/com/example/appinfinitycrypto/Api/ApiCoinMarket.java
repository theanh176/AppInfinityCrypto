package com.example.appinfinitycrypto.Api;

import com.example.appinfinitycrypto.Model.DetailItem;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.TopCoin;
import com.example.appinfinitycrypto.Model.TopGainer;
import com.example.appinfinitycrypto.Model.TopLoser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCoinMarket {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiCoinMarket apiCoinMarket = new Retrofit.Builder()
            .baseUrl("https://pro-api.coinmarketcap.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiCoinMarket.class);

    @GET("v1/cryptocurrency/listings/latest")
    Call<Market> convertMarket(@Query("CMC_PRO_API_KEY") String CMC_PRO_API_KEY,
                               @Query("sort") String sort,
                               @Query("start") int start,
                               @Query("limit") int limit,
                               @Query("cryptocurrency_type") String cryptocurrency_type,
                               @Query("convert") String convert);

    @GET("v1/cryptocurrency/listings/latest")
    Call<TopGainer> convertGainer(@Query("CMC_PRO_API_KEY") String CMC_PRO_API_KEY,
                                  @Query("sort") String sort,
                                  @Query("start") int start,
                                  @Query("limit") int limit,
                                  @Query("cryptocurrency_type") String cryptocurrency_type,
                                  @Query("convert") String convert);

    @GET("v1/cryptocurrency/listings/latest")
    Call<TopLoser> convertLoser(@Query("CMC_PRO_API_KEY") String CMC_PRO_API_KEY,
                                @Query("sort") String sort,
                                @Query("sort_dir") String sort_dir,
                                @Query("start") int start,
                                @Query("limit") int limit,
                                @Query("cryptocurrency_type") String cryptocurrency_type,
                                @Query("convert") String convert);

    @GET("v2/cryptocurrency/quotes/latest")
    Call<DetailItem> convertDetailMarket(@Query("CMC_PRO_API_KEY") String CMC_PRO_API_KEY,
                                         @Query("id") String id,
                                         @Query("convert") String convert);
}
