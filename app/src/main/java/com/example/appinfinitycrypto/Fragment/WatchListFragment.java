package com.example.appinfinitycrypto.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.WatchListAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.Market;
import com.example.appinfinitycrypto.Model.WatchList;
import com.example.appinfinitycrypto.Model.getWatchlist;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchListFragment extends Fragment {

    private List<DataItem> dataItems;
    private WatchListAdapter watchListAdapter;
    private RecyclerView watchListRecyclerView;
    private List<String> myList;
    private DatabaseReference database;
    private WatchListAdapter accountAdapter;
    private List<WatchList> accountList;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Account");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WatchListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WatchListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WatchListFragment newInstance(String param1, String param2) {
        WatchListFragment fragment = new WatchListFragment();
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
        View view = inflater.inflate(R.layout.fragment_watch_list, container, false);

        LoadData();
//        getListPhoneBooksRealtimeDB();
        // watch list recycler view
        watchListRecyclerView = view.findViewById(R.id.discoverHomeRecyclerView);
        watchListRecyclerView.setHasFixedSize(true);
        watchListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        dataItems = new ArrayList<>();
        DataItem item;

        // get data from firebase
        String phone = DataLocalManager.getPhoneInstall();
        ref.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getWatchlist getwatchlist = new getWatchlist(dataSnapshot);
                myList = getwatchlist.getWatchList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ApiCoinMarket.apiCoinMarket.convertMarket("fac03ee8-101c-4a60-86c3-b38e63d5f955", "market_cap", 1, 100, "all", "USD").enqueue(new Callback<Market>() {
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
                Toast.makeText(getActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void LoadData(){
        String phone = DataLocalManager.getPhoneInstall();;
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