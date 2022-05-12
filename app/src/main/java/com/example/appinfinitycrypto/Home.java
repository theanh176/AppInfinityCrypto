package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Adapter.TopCoinAdapter;
import com.example.appinfinitycrypto.Adapter.TopGainerAdapter;
import com.example.appinfinitycrypto.Adapter.TopLoserAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Api.ApiNew;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.DataItem_Gainer;
import com.example.appinfinitycrypto.Model.DataItem_Loser;
import com.example.appinfinitycrypto.Model.DataNews;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.TopGainer;
import com.example.appinfinitycrypto.Model.TopLoser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private List<DataItem> dataItems;
    private List<DataItem_Gainer> dataItem_Gainers;
    private List<DataItem_Loser> dataItem_Losers;
    private List<DataNews> dataNews;

    private TopCoinAdapter topCoinAdapter;
    private TopGainerAdapter topGainerAdapter;
    private TopLoserAdapter topLoserAdapter;
    private DiscoverAdapter discoverAdapter;

    private TextView showAllNews;

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

    private RecyclerView topCoinRecyclerView, topGainerRecyclerView, topLoserRecyclerView, discoverHomeRecyclerView;

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

        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955", "market_cap", 1, 5, "all", "USD").enqueue(new Callback<Market>() {
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
        dataItem_Gainers = new ArrayList<>();

        ApiCoinMarket.apiCoinMarket.convertUsdToVndGainer("fac03ee8-101c-4a60-86c3-b38e63d5f955", "percent_change_24h", 1, 5, "all", "USD").enqueue(new Callback<TopGainer>() {
            @Override
            public void onResponse(@NonNull Call<TopGainer> call, @NonNull Response<TopGainer> response) {

                TopGainer topGainer = response.body();
                DataItem_Gainer item_gainer;

                if (topGainer == null) {
                    System.out.println("top gainer null size");
                }

                if (topGainer != null) {
                    for (int i = 0; i < topGainer.getData().size(); i++) {
                        dataItem_Gainers.add((DataItem_Gainer) topGainer.getData().get(i));
                    }
                    topGainerAdapter = new TopGainerAdapter(dataItem_Gainers);
                    topGainerRecyclerView.setAdapter(topGainerAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopGainer> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // top loser recycler view
        topLoserRecyclerView = findViewById(R.id.topLoserRecyclerView);
        topLoserRecyclerView.setHasFixedSize(true);
        topLoserRecyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
        dataItem_Losers = new ArrayList<>();

        ApiCoinMarket.apiCoinMarket.convertUsdToVndLoser("fac03ee8-101c-4a60-86c3-b38e63d5f955", "percent_change_24h", "asc", 1, 5, "all", "USD").enqueue(new Callback<TopLoser>() {
            @Override
            public void onResponse(@NonNull Call<TopLoser> call, @NonNull Response<TopLoser> response) {

                TopLoser topLoser = response.body();
                DataItem_Loser item_loser;

                if (topLoser == null) {
                    System.out.println("top loser null size");
                }

                if (topLoser != null) {
                    for (int i = 0; i < topLoser.getData().size(); i++) {
                        dataItem_Losers.add((DataItem_Loser) topLoser.getData().get(i));
                    }
                    topLoserAdapter = new TopLoserAdapter(dataItem_Losers);
                    topLoserRecyclerView.setAdapter(topLoserAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopLoser> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // discover recycler view
        discoverHomeRecyclerView = findViewById(R.id.discoverHomeRecyclerView);
        discoverHomeRecyclerView.setHasFixedSize(true);
        discoverHomeRecyclerView.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.VERTICAL, false));
        dataNews = new ArrayList<>();
        showAllNews = findViewById(R.id.showAllNews);

        ApiNew.apiNew.convertUsdToVnd("283d7ecd8fc18b8a775b3feb651323c508943b922be9b5978fe299fe21f6f0d2").enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {

                Discover discover = response.body();
                DataNews item_discover;

                if (discover == null) {
                    System.out.println("discover null size");
                }

                if (discover != null) {
                    for (int i = 0; i < 3; i++) {
                        dataNews.add((DataNews) discover.getData().get(i));
                    }
                    discoverAdapter = new DiscoverAdapter(dataNews);
                    discoverHomeRecyclerView.setAdapter(discoverAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Click change activity
        showAllNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NewsActivity.class);
                startActivity(intent);
            }
        });

    }
}