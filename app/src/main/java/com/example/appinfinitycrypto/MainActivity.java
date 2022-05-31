package com.example.appinfinitycrypto;

import static androidx.navigation.Navigation.findNavController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.FragmentNavigator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.appinfinitycrypto.Fragment.DetailFragment;
import com.example.appinfinitycrypto.Fragment.HomeFragment;
import com.example.appinfinitycrypto.Fragment.MarketFragment;
import com.example.appinfinitycrypto.Fragment.NewsFragment;
import com.example.appinfinitycrypto.Fragment.NotSignInFragment;
import com.example.appinfinitycrypto.Fragment.ProfileFragment;
import com.example.appinfinitycrypto.Fragment.WatchListFragment;
import com.example.appinfinitycrypto.my_interface.ITransmitData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements ITransmitData {

    BottomNavigationView navigationView;
    FragmentNavigator fragmentNavigator;

    private DatabaseReference database;

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
        String phone = DataLocalManager.getPhoneInstall();
        database = FirebaseDatabase.getInstance().getReference("Account");
        database.child(phone).child("isOnline").setValue(true);

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
                        if(!DataLocalManager.getFirstInstall()){
                            fragment = new NotSignInFragment();
                        }else {
                            fragment = new ProfileFragment();
                        }
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
    }

    public void changeNavItem() {
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new NewsFragment()).commit();
        navigationView.setSelectedItemId(R.id.newspaper11);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String phone = DataLocalManager.getPhoneInstall();
        if(!phone.isEmpty()){
            database = FirebaseDatabase.getInstance().getReference("Account").child(phone);
            database.child("isOnline").setValue(true);
            Log.d("MainActivity Lifecycle", "===== onRestart =====");
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        String phone = DataLocalManager.getPhoneInstall();
        if(!phone.isEmpty()){
            database = FirebaseDatabase.getInstance().getReference("Account").child(phone);
            database.child("isOnline").setValue(false);
            Log.d("MainActivity Lifecycle", "===== onStop =====");
        }
    }

    @Override
    public void senData(String id) {
        Fragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        detailFragment.setArguments(bundle);

        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.add(R.id.body_container, detailFragment).commit();

    }
}
