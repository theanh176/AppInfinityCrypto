package com.example.appinfinitycrypto.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.R;

import java.io.IOException;
import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> implements Filterable {
    private List<DataItem> mDataItem;
    private List<DataItem> mDataItemOld;

    public CurrencyAdapter(List<DataItem> mDataItem) {
        this.mDataItem = mDataItem;
        this.mDataItemOld = mDataItem;
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

    class CurrencyViewHolder extends RecyclerView.ViewHolder{

        private TextView currencyNameTextView, currencySymbolTextView,
                currencyPriceTextView,
                currencyChangeTextView;
        private ImageView currencyImageView, currencyChartImageView, currencyChangeImageView;
        public CurrencyViewHolder(View itemView) {
            super(itemView);


            currencyImageView = itemView.findViewById(R.id.currencyImageView);
            currencyNameTextView = itemView.findViewById(R.id.currencyNameTextView);
            currencySymbolTextView = itemView.findViewById(R.id.currencySymbolTextView);
            currencyPriceTextView = itemView.findViewById(R.id.currencyPriceTextView);
            currencyChangeTextView = itemView.findViewById(R.id.currencyChangeTextView);
            currencyChartImageView = itemView.findViewById(R.id.currencyChartImageView);
            currencyChangeImageView = itemView.findViewById(R.id.currencyChangeImageView);
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
}
