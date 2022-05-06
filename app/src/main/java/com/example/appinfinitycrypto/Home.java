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

import com.example.appinfinitycrypto.Adapter.CurrencyAdapter;
import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Adapter.TopCoinAdapter;
import com.example.appinfinitycrypto.Adapter.TopGainerAdapter;
import com.example.appinfinitycrypto.Adapter.TopLoserAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.TopCoin;
import com.example.appinfinitycrypto.Model.TopGainer;
import com.example.appinfinitycrypto.Model.TopLoser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private List<DataItem> dataItems;
    private TopCoinAdapter topCoinAdapter;

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
        topCoinRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dataItems = new ArrayList<>();

        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955","market_cap",1,10,"tokens","USD").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {
//                CAN XOA DI
                Toast.makeText(Home.this, "Call Api Successful", Toast.LENGTH_SHORT).show();
//                CAN XOA DI
                Market market = response.body();
                Log.w("Source code","chay thanh cong");
               // Log.w("Xinchao",market.getData().get(1).getName());

                DataItem item;
                if (market != null) {
                    Log.w("Source code",market.getData().get(1).getName());


                    for (int i = 0; i < market.getData().size(); i++) {
                        dataItems.add((DataItem) market.getData().get(i));
                    }
                    topCoinAdapter = new TopCoinAdapter(dataItems);
//                    RecyclerView cần có một LayoutManager, ta tạo một LayoutManager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Home.this);
                    topCoinRecyclerView.setLayoutManager(linearLayoutManager);
                    topCoinRecyclerView.setAdapter(topCoinAdapter);
                }


            }

            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                Log.w("Xinchao","Chạy thất bại");
            }
        });

//        List<TopCoin> topCoinList = new ArrayList<>();
//
//        topCoinList.add(new TopCoin(R.drawable.bitcoin, "BTC", "$9999"));
//        topCoinList.add(new TopCoin(R.drawable.bitcoin, "ETH", "$7778"));
//        topCoinList.add(new TopCoin(R.drawable.bitcoin, "ONUS", "$10000"));
//        topCoinList.add(new TopCoin(R.drawable.bitcoin, "RUN", "$9876"));
//        topCoinList.add(new TopCoin(R.drawable.bitcoin, "ANM", "$2.1"));
//
//        TopCoinAdapter topCoinAdapter = new TopCoinAdapter(topCoinList);
//        topCoinRecyclerView.setAdapter(topCoinAdapter);

        // top gainer recycler view
        topGainerRecyclerView = findViewById(R.id.topGainerRecyclerView);
        topGainerRecyclerView.setHasFixedSize(true);
        topGainerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<TopGainer> topGainerList = new ArrayList<>();

        topGainerList.add(new TopGainer(R.drawable.bitcoin, "Bitcoin", "BTC"));
        topGainerList.add(new TopGainer(R.drawable.bitcoin, "Ethereum", "ETH"));
        topGainerList.add(new TopGainer(R.drawable.bitcoin, "Ethereum", "ETH"));
        topGainerList.add(new TopGainer(R.drawable.bitcoin, "Ethereum", "ETH"));
        topGainerList.add(new TopGainer(R.drawable.bitcoin, "Ethereum", "ETH"));

        TopGainerAdapter topGainerAdapter = new TopGainerAdapter(topGainerList);
        topGainerRecyclerView.setAdapter(topGainerAdapter);

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