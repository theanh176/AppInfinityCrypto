package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Adapter.NotificationAdapter;
import com.example.appinfinitycrypto.Api.ApiNew;
import com.example.appinfinitycrypto.Model.DataNews;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.Model.Notification;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

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

    private RecyclerView newsRecyclerView;
    private List<DataNews> dataNews;
    private DiscoverAdapter discoverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTranslucentStatusBar();

        // notification recycler view
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this, LinearLayoutManager.VERTICAL, false));

        dataNews = new ArrayList<>();

        ApiNew.apiNew.convertUsdToVnd("283d7ecd8fc18b8a775b3feb651323c508943b922be9b5978fe299fe21f6f0d2").enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {

                Discover discover = response.body();
                DataNews item_discover;

                if (discover == null) {
                    System.out.println("discover null size");
                }

                if (discover != null) {
                    for (int i = 0; i < discover.getData().size(); i++) {
                        dataNews.add((DataNews) discover.getData().get(i));
                    }
                    discoverAdapter = new DiscoverAdapter(dataNews);
                    newsRecyclerView.setAdapter(discoverAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Discover> call, @NonNull Throwable t) {
                Toast.makeText(NewsActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}