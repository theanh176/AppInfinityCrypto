package com.example.appinfinitycrypto.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.my_interface.ItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> implements Filterable {
    private List<DataItem> mDataItem;
    private List<DataItem> mDataItemOld;
    private Context context;
    private DatabaseReference ref;

    public CurrencyAdapter(List<DataItem> mDataItem, Context context) {
        this.mDataItem = mDataItem;
        this.mDataItemOld = mDataItem;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);

        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        DataItem dataItem = mDataItem.get(position);
        if (dataItem == null) {
            return;
        }
        holder.currencyNameTextView.setText(dataItem.getName());
        holder.currencySymbolTextView.setText(dataItem.getSymbol());
        holder.currencyPriceTextView.setText(String.format("$%.2f", dataItem.getQuote().getUsd().getPrice()));

        if (dataItem.getQuote().getUsd().getPercent_change_24h() < 0) {
            holder.currencyChangeTextView.setTextColor(Color.RED);
            holder.currencyChangeTextView.setText("-" + String.format("$%.2f",dataItem.getQuote().getUsd().getPercent_change_24h()) + "%");
            holder.currencyChangeImageView.setImageResource(R.drawable.caret_down_red);
        } else{
            holder.currencyChangeTextView.setTextColor(Color.GREEN);
            holder.currencyChangeTextView.setText("+" + String.format("$%.2f",dataItem.getQuote().getUsd().getPercent_change_24h()) + "%");
            holder.currencyChangeImageView.setImageResource(R.drawable.caret_up_green);
        }

        LoadImage loadImage = new LoadImage(holder.currencyImageView);
        loadImage.execute("https://s2.coinmarketcap.com/static/img/coins/64x64/" + dataItem.getId() + ".png");
        LoadImage loadImageChart = new LoadImage(holder.currencyChartImageView);
        loadImageChart.execute("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + dataItem.getId() + ".png");

        CheckWatchlistStar(holder.ckStar, dataItem);

        holder.ckStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.ckStar.isChecked()) {
                    String phone = DataLocalManager.getPhoneInstall();
                    ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Account account = snapshot.getValue(Account.class);

                            if (account.getWatchlist() == null) {
                                HashMap<String, Boolean> wList = new HashMap<>();
                                wList.put(dataItem.getSymbol(), true);
                                ref.child("watchlist").setValue(wList);
                            } else {
                                account.getWatchlist().put(dataItem.getSymbol(), true);
                                ref.child("watchlist").setValue(account.getWatchlist());
                            }
                            Toast.makeText(context, "Đã thêm vào Watchlist của bạn", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    String phone = DataLocalManager.getPhoneInstall();
                    ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Account account = snapshot.getValue(Account.class);
                            HashMap<String, Boolean> wList = account.getWatchlist();
                            wList.remove(dataItem.getSymbol(), true);
                            ref.child("watchlist").setValue(wList);
                            Toast.makeText(context, "Đã xóa khỏi Watchlist của bạn", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        holder.ckStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


            }
        });
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongCLick) {

                Toast.makeText(context, "" + mDataItem.get(position).getId() + " " + mDataItem.get(position).getSymbol(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mDataItem != null) {
            return mDataItem.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    mDataItem = mDataItemOld;
                } else {
                    List<DataItem> list = new ArrayList<>();
                    for (DataItem dataItem: mDataItemOld) {
                        if (dataItem.getName().toLowerCase().contains(strSearch.toLowerCase()) ||
                                dataItem.getSymbol().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(dataItem);
                        }
                    }

                    mDataItem = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataItem;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataItem = (List<DataItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView currencyNameTextView, currencySymbolTextView,
                currencyPriceTextView,
                currencyChangeTextView;
        private CheckBox ckStar;
        private ImageView currencyImageView, currencyChartImageView, currencyChangeImageView;

//        Khai báo interface xử lý sự kiện click recyclerview
        private ItemClickListener itemClickListener;

        public CurrencyViewHolder(View itemView) {
            super(itemView);


            currencyImageView = itemView.findViewById(R.id.currencyImageView);
            currencyNameTextView = itemView.findViewById(R.id.currencyNameTextView);
            currencySymbolTextView = itemView.findViewById(R.id.currencySymbolTextView);
            currencyPriceTextView = itemView.findViewById(R.id.currencyPriceTextView);
            currencyChangeTextView = itemView.findViewById(R.id.currencyChangeTextView);
            currencyChartImageView = itemView.findViewById(R.id.currencyChartImageView);
            currencyChangeImageView = itemView.findViewById(R.id.currencyChangeImageView);
            ckStar = itemView.findViewById(R.id.ckStar);

            itemView.setOnClickListener(this);



        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView ivResult){
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

    private void CheckWatchlistStar(CheckBox checkBox, DataItem dataItem){
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
