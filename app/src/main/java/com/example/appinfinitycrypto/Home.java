package com.example.appinfinitycrypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Adapter.TopCoinAdapter;
import com.example.appinfinitycrypto.Adapter.TopGainerAdapter;
import com.example.appinfinitycrypto.Adapter.TopLoserAdapter;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.Model.TopCoin;
import com.example.appinfinitycrypto.Model.TopGainer;
import com.example.appinfinitycrypto.Model.TopLoser;

public class Home extends AppCompatActivity {

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

        List<TopCoin> topCoinList = new ArrayList<>();

        topCoinList.add(new TopCoin(R.drawable.bitcoin, "BTC", "$9999"));
        topCoinList.add(new TopCoin(R.drawable.bitcoin, "ETH", "$7778"));
        topCoinList.add(new TopCoin(R.drawable.bitcoin, "ONUS", "$10000"));
        topCoinList.add(new TopCoin(R.drawable.bitcoin, "RUN", "$9876"));
        topCoinList.add(new TopCoin(R.drawable.bitcoin, "ANM", "$2.1"));

        TopCoinAdapter topCoinAdapter = new TopCoinAdapter(topCoinList);
        topCoinRecyclerView.setAdapter(topCoinAdapter);

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
        discoverRecyclerView = findViewById(R.id.watchListRecyclerView);
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