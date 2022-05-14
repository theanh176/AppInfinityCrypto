package com.example.appinfinitycrypto;

import static androidx.navigation.Navigation.findNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.appinfinitycrypto.Fragment.HomeFragment;
import com.example.appinfinitycrypto.Fragment.MarketFragment;
import com.example.appinfinitycrypto.Fragment.NewsFragment;
import com.example.appinfinitycrypto.Fragment.ProfileFragment;
import com.example.appinfinitycrypto.Fragment.WatchListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTranslucentStatusBar();

        navigationView = findViewById(R.id.bottom_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.home11);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home11:
                        fragment = new HomeFragment();
                        break;

                    case R.id.market11:
                        fragment = new MarketFragment();
                        break;

                    case R.id.watchlist11:
                        fragment = new WatchListFragment();
                        break;

                    case R.id.newspaper11:
                        fragment = new NewsFragment();
                        break;

                    case R.id.account11:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
    }
}
