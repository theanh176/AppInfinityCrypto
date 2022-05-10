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

import com.example.appinfinitycrypto.Model.DataItem_Gainer;
import com.example.appinfinitycrypto.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TopGainerAdapter extends RecyclerView.Adapter<TopGainerAdapter.TopGainerViewHolder> {

    private List<DataItem_Gainer> mDataItem;
    private List<DataItem_Gainer> mDataItemOld;

    public TopGainerAdapter(List<DataItem_Gainer> mDataItem) {
        this.mDataItem = mDataItem;
        this.mDataItemOld = mDataItem;
    }

    @NonNull
    @Override
    public TopGainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        return new TopGainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopGainerViewHolder holder, int position) {
        DataItem_Gainer dataItem = mDataItem.get(position);
        if (dataItem == null) {
            return;
        }
        holder.symbol.setText(dataItem.getSymbol());
        holder.price.setText(String.format("$%.2f", dataItem.getQuote().getUsd().getPrice()));
        LoadImage loadImage = new LoadImage(holder.mImageView);
        loadImage.execute("https://s2.coinmarketcap.com/static/img/coins/64x64/" + dataItem.getId() + ".png");
    }

    @Override
    public int getItemCount() {
        if(mDataItem != null) {
            return mDataItem.size();
        }
        return 0;
    }

    public class TopGainerViewHolder extends RecyclerView.ViewHolder {

        private TextView symbol;
        private TextView price;
        private ImageView mImageView;

        public TopGainerViewHolder(@NonNull View itemView) {
            super(itemView);

            symbol = itemView.findViewById(R.id.watchlist_signname);
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
