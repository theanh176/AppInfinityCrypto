package com.example.appinfinitycrypto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.R;

import java.util.List;

import com.example.appinfinitycrypto.Model.TopCoin;

public class TopCoinAdapter extends RecyclerView.Adapter<TopCoinAdapter.TopCoinViewHolder> {

    private List<TopCoin> topCoinList;

    public TopCoinAdapter(List<TopCoin> topCoinList) {
        this.topCoinList = topCoinList;
    }

    @NonNull
    @Override
    public TopCoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        return new TopCoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCoinViewHolder holder, int position) {
        holder.name.setText(topCoinList.get(position).getName());
        holder.price.setText(topCoinList.get(position).getPrize());
        holder.mImageView.setImageResource(topCoinList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return topCoinList.size();
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
}
