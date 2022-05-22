package com.example.appinfinitycrypto.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Adapter.TopCoinAdapter;
import com.example.appinfinitycrypto.Adapter.TopGainerAdapter;
import com.example.appinfinitycrypto.Adapter.TopLoserAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.Api.ApiNew;
import com.example.appinfinitycrypto.Home;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.DataItem_Gainer;
import com.example.appinfinitycrypto.Model.DataItem_Loser;
import com.example.appinfinitycrypto.Model.DataNews;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.TopGainer;
import com.example.appinfinitycrypto.Model.TopLoser;
import com.example.appinfinitycrypto.NewsActivity;
import com.example.appinfinitycrypto.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{

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

    private RecyclerView topCoinRecyclerView, topGainerRecyclerView, topLoserRecyclerView, discoverHomeRecyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // top coin recycler view
        topCoinRecyclerView = view.findViewById(R.id.topCoinRecyclerView);
        topCoinRecyclerView.setHasFixedSize(true);
        topCoinRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
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
                Toast.makeText(requireActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // top gainer recycler view
        topGainerRecyclerView = view.findViewById(R.id.topGainerRecyclerView);
        topGainerRecyclerView.setHasFixedSize(true);
        topGainerRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
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
                Toast.makeText(requireActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // top loser recycler view
        topLoserRecyclerView = view.findViewById(R.id.topLoserRecyclerView);
        topLoserRecyclerView.setHasFixedSize(true);
        topLoserRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
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
                Toast.makeText(requireActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // discover recycler view
        discoverHomeRecyclerView = view.findViewById(R.id.discoverHomeRecyclerView);
        discoverHomeRecyclerView.setHasFixedSize(true);
        discoverHomeRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        dataNews = new ArrayList<>();
        showAllNews = view.findViewById(R.id.showAllNews);

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
                Toast.makeText(requireActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Click change activity
        showAllNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFragment fragment = new NewsFragment();
                getFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
            }
        });

        return view;

    }
}