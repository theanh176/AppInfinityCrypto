package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.WatchListAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.Market;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchListActivity extends AppCompatActivity {

    private List<DataItem> dataItems;
    private WatchListAdapter watchListAdapter;
    private RecyclerView watchListRecyclerView;
    private List<String> myList;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Account");
    public DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Account");


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
        setContentView(R.layout.activity_watch_list);
        setTranslucentStatusBar();
        LoadData();

        // watch list recycler view
        watchListRecyclerView = findViewById(R.id.discoverHomeRecyclerView);
        watchListRecyclerView.setHasFixedSize(true);
        watchListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        dataItems = new ArrayList<>();
        DataItem item;

        // get data from firebase
        String phone = ((MyApplication) this.getApplication()).getSomeVariable();
        ref.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Account account = new Account(dataSnapshot);
//                    myList = account.getWatchList();
             }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955", "market_cap", 1, 100, "all", "USD").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {

                Market market = response.body();

                if (market == null) {
                    System.out.println("Market null size");
                }

                if (market != null) {
                    for (int i = 0; i < market.getData().size(); i++) {
                        // check if my list contains the coin
                        for (int j = 0; j < myList.size(); j++) {
                            if (market.getData().get(i).getSymbol().equals(myList.get(j))) {
                                dataItems.add((DataItem) market.getData().get(i));
                            }
                        }
                    }
                    watchListAdapter = new WatchListAdapter(dataItems, phone, myList);
                    watchListRecyclerView.setAdapter(watchListAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(WatchListActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void LoadData(){
        String phone = ((MyApplication) this.getApplication()).getSomeVariable();
        ref.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Account account = new Account(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });;
    }
    }