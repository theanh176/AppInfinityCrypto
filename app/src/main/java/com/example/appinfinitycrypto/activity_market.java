package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.CurrencyAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.Market;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_market extends AppCompatActivity {

    EditText txt;
    Button btnArrangeName, btnArrangePrice, btnArrangePercent24h, btnArrangePercent7d;
    private RecyclerView rcvCurrency;
    private List<DataItem> dataItems;
    private CurrencyAdapter currencyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        txt = findViewById(R.id.edit_search);
        btnArrangeName = findViewById(R.id.btnArrangeName);
        btnArrangePrice = findViewById(R.id.btnArrangePrice);
        btnArrangePercent24h = findViewById(R.id.btnArrangePercent24h);
        btnArrangePercent7d = findViewById(R.id.btnArrangePercent7D);
        rcvCurrency = findViewById(R.id.rcv_market);
        dataItems = new ArrayList<>();


//        https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=fac03ee8-101c-4a60-86c3-b38e63d5f955&sort=market_cap&start=1&limit=100&cryptocurrency_type=tokens&convert=USD
        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955","market_cap", 1,10,"tokens","USD").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {
                Market market = response.body();
                if (market != null) {
                    for (int i = 0; i < market.getData().size(); i++) {
                        dataItems.add((DataItem) market.getData().get(i));
                    }
                    currencyAdapter = new CurrencyAdapter(dataItems);
//                    RecyclerView cần có một LayoutManager, ta tạo một LayoutManager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity_market.this);
                    rcvCurrency.setLayoutManager(linearLayoutManager);
                    rcvCurrency.setAdapter(currencyAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(activity_market.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnArrangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return t1.getName().compareToIgnoreCase(dataItem.getName());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems);
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        btnArrangePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return Double.valueOf(t1.getQuote().getUsd().getPrice()).compareTo(dataItem.getQuote().getUsd().getPrice());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems);
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        btnArrangePercent24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return Double.valueOf(t1.getQuote().getUsd().getPercent_change_24h()).compareTo(dataItem.getQuote().getUsd().getPercent_change_24h());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems);
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        btnArrangePercent7d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return Double.valueOf(t1.getQuote().getUsd().getPercent_change_7d()).compareTo(dataItem.getQuote().getUsd().getPercent_change_7d());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems);
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activity_market.this.currencyAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}