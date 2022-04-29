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

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Model.Discover;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {
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

    private RecyclerView discoverRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        setTranslucentStatusBar();

        // discover recycler view
        discoverRecyclerView = findViewById(R.id.discoverRecyclerView);
        discoverRecyclerView.setHasFixedSize(true);
        discoverRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<Discover> discoverList = new ArrayList<>();

        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        discoverList.add(new Discover(R.drawable.dicover, "Prometeus Token (PROM) ONUS", "20/11/2022"));
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoverList);
        discoverRecyclerView.setAdapter(discoverAdapter);
    }
}