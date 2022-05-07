package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Adapter.TopCoinAdapter;
import com.example.appinfinitycrypto.Adapter.TopGainerAdapter;
import com.example.appinfinitycrypto.Adapter.TopLoserAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.TopGainer;
import com.example.appinfinitycrypto.Model.TopLoser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private List<DataItem> dataItems;
    private TopCoinAdapter topCoinAdapter;
    private TopGainerAdapter topGainerAdapter;
    private TopLoserAdapter topLoserAdapter;

    //    Change the status bar color
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private RecyclerView topCoinRecyclerView, topGainerRecyclerView, topLoserRecyclerView, discoverRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTranslucentStatusBar();

        // top coin recycler view
        topCoinRecyclerView = findViewById(R.id.topCoinRecyclerView);
        topCoinRecyclerView.setHasFixedSize(true);
        topCoinRecyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        dataItems = new ArrayList<>();

        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955", "market_cap", 1, 10, "all", "USD").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {

                Market market = response.body();
                DataItem item;

                if (market == null) {
                    System.out.println("market null size");
                }

                if (market != null) {
                    for (int i = 0; i < market.getData().size(); i++) {
                        dataItems.add((DataItem) market.getData().get(i));
                    }
                    topCoinAdapter = new TopCoinAdapter(dataItems);
                    topCoinRecyclerView.setAdapter(topCoinAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // top gainer recycler view
        topGainerRecyclerView = findViewById(R.id.topGainerRecyclerView);
        topGainerRecyclerView.setHasFixedSize(true);
        topGainerRecyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        dataItems = new ArrayList<>();

        ApiCoinMarket.apiCoinMarket.convertUsdToVndGainer("fac03ee8-101c-4a60-86c3-b38e63d5f955", "asc").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {

                Market market = response.body();
                DataItem item;

                if (market == null) {
                    System.out.println("topGainer null size");
                }

                if (market != null) {
                    Log.w("Source code", market.getData().get(1).getName());
                    for (int i = 0; i < market.getData().size(); i++) {
                        dataItems.add((DataItem) market.getData().get(i));
                    }
                    topGainerAdapter = new TopGainerAdapter(dataItems);
                    topGainerRecyclerView.setAdapter(topGainerAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // top loser recycler view
        topLoserRecyclerView = findViewById(R.id.topLoserRecyclerView);
        topLoserRecyclerView.setHasFixedSize(true);
        topLoserRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<TopLoser> topLoserList = new ArrayList<>();

        topLoserList.add(new TopLoser(R.drawable.bitcoin, "Bitcoin", "BTC"));
        topLoserList.add(new TopLoser(R.drawable.bitcoin, "Ethereum", "ETH"));
        topLoserList.add(new TopLoser(R.drawable.bitcoin, "Ethereum", "ETH"));
        topLoserList.add(new TopLoser(R.drawable.bitcoin, "Ethereum", "ETH"));
        topLoserList.add(new TopLoser(R.drawable.bitcoin, "Ethereum", "ETH"));

        TopLoserAdapter topLoserAdapter = new TopLoserAdapter(topLoserList);
        topLoserRecyclerView.setAdapter(topLoserAdapter);

        // discover recycler view
        discoverRecyclerView = findViewById(R.id.notificationRecyclerView);
        discoverRecyclerView.setHasFixedSize(true);
        discoverRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<Discover> discoverList = new ArrayList<>();

        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));

        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoverList);
        discoverRecyclerView.setAdapter(discoverAdapter);
    }
}