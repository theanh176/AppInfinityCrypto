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

import com.example.appinfinitycrypto.Adapter.WatchListAdapter;
import com.example.appinfinitycrypto.Model.WatchList;

import java.util.ArrayList;
import java.util.List;

public class WatchListActivity extends AppCompatActivity {

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


    private RecyclerView watchListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);
        setTranslucentStatusBar();

        // watch list recycler view
        watchListRecyclerView = findViewById(R.id.notificationRecyclerView);
        watchListRecyclerView.setHasFixedSize(true);
        watchListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<WatchList> watchListList = new ArrayList<>();

        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));
        watchListList.add(new WatchList(R.drawable.bitcoin, R.drawable.ic_caret_up, R.drawable.dicover, R.drawable.ic_star_fill, "Bitcoin", "$9999", "99%", "BTC"));

        WatchListAdapter watchListAdapter = new WatchListAdapter(watchListList);
        watchListRecyclerView.setAdapter(watchListAdapter);

    }
}