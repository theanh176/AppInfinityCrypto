package com.example.appinfinitycrypto.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.CurrencyAdapter;
import com.example.appinfinitycrypto.Api.ApiCoinMarket;
import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.Model.DetailItem;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private TextView txtName, txtSymbol, txtPercent24h, txtUsd;
    private ImageView imgDetail, imgBackDetail;
    private WebView webViewChart;
    private Button btn1h, btn24h, btn7d, btn30d;
    private CheckBox ckStar;

    private DatabaseReference ref;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        txtName = view.findViewById(R.id.txtNameDetail);
        txtSymbol = view.findViewById(R.id.txtSymbolDetail);
        txtPercent24h = view.findViewById(R.id.percent24hDetail);
        txtUsd = view.findViewById(R.id.txtUSDDetail);
        imgDetail = view.findViewById(R.id.imgDetail);
        imgBackDetail = view.findViewById(R.id.imgBackDetail);
        webViewChart = view.findViewById(R.id.webViewChart);

        btn1h = view.findViewById(R.id.btn1h);
        btn24h = view.findViewById(R.id.btn24h);
        btn7d = view.findViewById(R.id.btn7d);
        btn30d = view.findViewById(R.id.btn30d);

        ckStar = view.findViewById(R.id.ckStarDetail);

        ApiCoinMarket.apiCoinMarket.convertDetailMarket("fac03ee8-101c-4a60-86c3-b38e63d5f955", getArguments().getString("id"), "USD").enqueue(new Callback<DetailItem>() {
            @Override
            public void onResponse(Call<DetailItem> call, Response<DetailItem> response) {
                DetailItem item = response.body();
                if(item != null) {
                    loadData(item);
                    loadImage(imgDetail);
                    checkStar(ckStar, item.getData().get(getArguments().getString("id")));
                } else {
                    Toast.makeText(DetailFragment.this.getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }

                ckStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ckStar.isChecked()) {
                            String phone = DataLocalManager.getPhoneInstall();
                            ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Account account = snapshot.getValue(Account.class);

                                    if (account.getWatchlist() == null) {
                                        HashMap<String, Boolean> wList = new HashMap<>();
                                        wList.put(item.getData().get(getArguments().getString("id")).getSymbol(), true);
                                        ref.child("watchlist").setValue(wList);
                                    } else {
                                        account.getWatchlist().put(item.getData().get(getArguments().getString("id")).getSymbol(), true);
                                        ref.child("watchlist").setValue(account.getWatchlist());
                                    }
                                    Toast.makeText(DetailFragment.this.getContext(), "Đã thêm vào Watchlist của bạn", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            String phone = DataLocalManager.getPhoneInstall();
                            ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Account account = snapshot.getValue(Account.class);
                                    HashMap<String, Boolean> wList = account.getWatchlist();
                                    wList.remove(item.getData().get(getArguments().getString("id")).getSymbol(), true);
                                    ref.child("watchlist").setValue(wList);
                                    Toast.makeText(DetailFragment.this.getContext(), "Đã xóa khỏi Watchlist của bạn", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<DetailItem> call, Throwable t) {

            }
        });

        imgBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new MarketFragment();
//                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//                fm.add(R.id.fragment_detail, fragment);
//                fm.commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MarketFragment()).commit();
            }
        });

        return view;
    }

    private void loadData(DetailItem item) {
        String id = getArguments().getString("id");
        txtName.setText(item.getData().get(id).getName());
        txtSymbol.setText(item.getData().get(id).getSymbol());
        txtUsd.setText(String.format("$%.2f",item.getData().get(id).getQuote().getUsd().getPrice()));
//                    txtPercent24h.setText((int) item.getData().get(id).getQuote().getUsd().getPercent_change_24h());
        if (item.getData().get(id).getQuote().getUsd().getPercent_change_24h() < 0) {
            txtPercent24h.setBackgroundResource(R.drawable.border_percent_red);
            txtPercent24h.setText(String.format("$%.2f", item.getData().get(id).getQuote().getUsd().getPercent_change_24h()) + "%");
        } else{
            txtPercent24h.setBackgroundResource(R.drawable.border_percent_green);
            txtPercent24h.setText(String.format("$%.2f", item.getData().get(id).getQuote().getUsd().getPercent_change_24h()) + "%");
        }
    }

    private void loadImage(ImageView imageView) {
        LoadImageTask loadImage = new LoadImageTask(imageView);
        loadImage.execute("https://s2.coinmarketcap.com/static/img/coins/64x64/" + getArguments().getString("id") + ".png");
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImageTask(ImageView ivResult){
            this.imageView = ivResult;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void checkStar(CheckBox checkBox, DataItem dataItem){
        String phone = DataLocalManager.getPhoneInstall();
        ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account.getWatchlist() != null) {
                    for(Map.Entry<String, Boolean> item: account.getWatchlist().entrySet()) {
                        if(item.getKey().equals(dataItem.getSymbol())) {
                            checkBox.setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}