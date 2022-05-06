package com.example.appinfinitycrypto.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.appinfinitycrypto.Model.TopCoin;

public class TopCoinAdapter extends RecyclerView.Adapter<TopCoinAdapter.TopCoinViewHolder> {

    private List<DataItem> mDataItem;
    private List<DataItem> mDataItemOld;
//    private List<TopCoin> topCoinList;

//    public TopCoinAdapter(List<TopCoin> topCoinList) {
//        this.topCoinList = topCoinList;
//    }
    public TopCoinAdapter(List<DataItem> mDataItem) {
    this.mDataItem = mDataItem;
    this.mDataItemOld = mDataItem;
}

    @NonNull
    @Override
    public TopCoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        return new TopCoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCoinViewHolder holder, int position) {
        DataItem dataItem = mDataItem.get(position);
        if (dataItem == null) {
            return;
        }
        holder.name.setText(dataItem.getName());
        holder.price.setText(String.format("$%.2f", dataItem.getQuote().getUsd().getPrice()));
        LoadImage loadImage = new LoadImage(holder.mImageView);
        // CAN TAO MOT LOP CHE DU LIEU
        loadImage.execute("https://s2.coinmarketcap.com/static/img/coins/64x64/" + dataItem.getId() + ".png");
//        holder.name.setText(topCoinList.get(position).getName());
//        holder.price.setText(topCoinList.get(position).getPrize());
//        holder.mImageView.setImageResource(topCoinList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        if(mDataItem != null) {
            return mDataItem.size();
        }
        return 0;
//        return topCoinList.size();
    }

    public class TopCoinViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private ImageView mImageView;

        public TopCoinViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.watchlist_signname);
            price = itemView.findViewById(R.id.watchlist_prize);
            mImageView = itemView.findViewById(R.id.watchlist_img);
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
