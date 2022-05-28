package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.appinfinitycrypto.Fragment.AccountAdminFragment;
import com.example.appinfinitycrypto.Fragment.FeedbackFragment;
import com.example.appinfinitycrypto.Fragment.HomeAdminFragment;
import com.example.appinfinitycrypto.Fragment.ListAdminFragment;
import com.example.appinfinitycrypto.Fragment.ListUserFragment;
import com.example.appinfinitycrypto.Fragment.ProfileFragment;
import com.example.appinfinitycrypto.Fragment.StatisticalFragment;
import com.example.appinfinitycrypto.Fragment.ViewHomeAdminFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainAdmin extends AppCompatActivity {

    BottomNavigationView navigationView;
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
        setContentView(R.layout.activity_main_admin);

        setTranslucentStatusBar();

        navigationView = findViewById(R.id.bottom_admin_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_admin_container, new ViewHomeAdminFragment()).commit();
        navigationView.setSelectedItemId(R.id.homeadmin11);

        String phone = DataLocalManager.getPhoneInstall();
        database = FirebaseDatabase.getInstance().getReference("Account");
        database.child(phone).child("isOnline").setValue(true);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.homeadmin11:
                        fragment = new ViewHomeAdminFragment();
                        break;
                    case R.id.listUser11:
                        fragment = new HomeAdminFragment();
                        break;
                    case R.id.feedback:
                        fragment = new FeedbackFragment();
                        break;
                    case R.id.statistical:
                        fragment = new StatisticalFragment();
                        break;
                    case R.id.accountadmin11:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_admin_container, fragment).commit();

                return true;
            }
        });
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
}