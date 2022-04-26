package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Model.Market;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_market extends AppCompatActivity {

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        txt = findViewById(R.id.edit_search);

//        https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=fac03ee8-101c-4a60-86c3-b38e63d5f955&sort=market_cap&start=1&limit=100&cryptocurrency_type=tokens&convert=USD
        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955","market_cap",1,2,"tokens","USD").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {
                Toast.makeText(activity_market.this, "Call Api Successful", Toast.LENGTH_SHORT).show();
                Log.w("Xinchao","Chạy thành công");
                Market market = response.body();
                if (market != null) {
                    Log.w("Source code",market.getData().get(1).getName());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(activity_market.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                Log.w("Xinchao","Chạy thất bại");
            }
        });
    }
}