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

import com.example.appinfinitycrypto.Adapter.DiscoverAdapter;
import com.example.appinfinitycrypto.Api.ApiNew;
import com.example.appinfinitycrypto.Model.DataNews;
import com.example.appinfinitycrypto.Model.Discover;
import com.example.appinfinitycrypto.NewsActivity;
import com.example.appinfinitycrypto.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private RecyclerView newsRecyclerView;
    private List<DataNews> dataNews;
    private DiscoverAdapter discoverAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // notification recycler view
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        dataNews = new ArrayList<>();

        ApiNew.apiNew.convertUsdToVnd("283d7ecd8fc18b8a775b3feb651323c508943b922be9b5978fe299fe21f6f0d2").enqueue(new Callback<Discover>() {
            @Override
            public void onResponse(@NonNull Call<Discover> call, @NonNull Response<Discover> response) {

                Discover discover = response.body();
                DataNews item_discover;

                if (discover == null) {
                    System.out.println("discover null size111");
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
                Toast.makeText(requireActivity(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }
}