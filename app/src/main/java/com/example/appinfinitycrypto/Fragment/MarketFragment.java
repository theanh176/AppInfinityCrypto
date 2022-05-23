package com.example.appinfinitycrypto.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.CurrencyAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.MainActivity;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.activity_market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketFragment extends Fragment {

    EditText editSearchMarket;
    Button btnArrangeName, btnArrangePrice, btnArrangePercent24h, btnArrangePercent7d;
    private RecyclerView rcvCurrency;
    private List<DataItem> dataItems;
    private CurrencyAdapter currencyAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MarketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarketFragment newInstance(String param1, String param2) {
        MarketFragment fragment = new MarketFragment();
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
        View view = inflater.inflate(R.layout.fragment_market, container, false);

        editSearchMarket = view.findViewById(R.id.edit_search_market);
        btnArrangeName = view.findViewById(R.id.btnArrangeName);
        btnArrangePrice = view.findViewById(R.id.btnArrangePrice);
        btnArrangePercent24h = view.findViewById(R.id.btnArrangePercent24h);
        btnArrangePercent7d = view.findViewById(R.id.btnArrangePercent7D);
        rcvCurrency = view.findViewById(R.id.rcv_market);
        dataItems = new ArrayList<>();


//        https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=fac03ee8-101c-4a60-86c3-b38e63d5f955&sort=market_cap&start=1&limit=100&cryptocurrency_type=tokens&convert=USD
        ApiCoinMarket.apiCoinMarket.convertUsdToVnd("fac03ee8-101c-4a60-86c3-b38e63d5f955","market_cap", 1,10,"tokens","USD").enqueue(new Callback<Market>() {
            @Override
            public void onResponse(@NonNull Call<Market> call, @NonNull Response<Market> response) {
//                CAN XOA DI
                Toast.makeText(requireActivity(), "Call Api Successful", Toast.LENGTH_SHORT).show();
//                CAN XOA DI
                Log.w("Xinchao","Chạy thành công");
                Market market = response.body();
//                DataItem item;
                if (market != null) {
                    Log.w("Source code",market.getData().get(1).getName());


                    for (int i = 0; i < market.getData().size(); i++) {
                        dataItems.add((DataItem) market.getData().get(i));
                    }
                    currencyAdapter = new CurrencyAdapter(dataItems, MarketFragment.this.getContext());
//                    RecyclerView cần có một LayoutManager, ta tạo một LayoutManager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
                    rcvCurrency.setLayoutManager(linearLayoutManager);
                    rcvCurrency.setAdapter(currencyAdapter);
                }


            }

            @Override
            public void onFailure(@NonNull Call<Market> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
                Log.w("Xinchao","Chạy thất bại");
            }
        });

        btnArrangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return t1.getName().compareToIgnoreCase(dataItem.getName());
                    }
                });

                Collections.reverse(dataItems);

                currencyAdapter = new CurrencyAdapter(dataItems, MarketFragment.this.getContext());
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        btnArrangePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return Double.valueOf(t1.getQuote().getUsd().getPrice()).compareTo(dataItem.getQuote().getUsd().getPrice());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems, MarketFragment.this.getContext());
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        btnArrangePercent24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return Double.valueOf(t1.getQuote().getUsd().getPercent_change_24h()).compareTo(dataItem.getQuote().getUsd().getPercent_change_24h());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems, MarketFragment.this.getContext());
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        btnArrangePercent7d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(dataItems, new Comparator<DataItem>() {
                    @Override
                    public int compare(DataItem dataItem, DataItem t1) {
                        return Double.valueOf(t1.getQuote().getUsd().getPercent_change_7d()).compareTo(dataItem.getQuote().getUsd().getPercent_change_7d());
                    }
                });
                Collections.reverse(dataItems);
                currencyAdapter = new CurrencyAdapter(dataItems, MarketFragment.this.getContext());
                rcvCurrency.setAdapter(currencyAdapter);
            }
        });

        editSearchMarket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currencyAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}