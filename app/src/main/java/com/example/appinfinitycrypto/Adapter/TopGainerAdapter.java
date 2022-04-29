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

import com.example.appinfinitycrypto.Model.TopGainer;

public class TopGainerAdapter extends RecyclerView.Adapter<TopGainerAdapter.TopGainerViewHolder> {

    private List<TopGainer> topGainerList;

    public TopGainerAdapter(List<TopGainer> topGainerList) {
        this.topGainerList = topGainerList;
    }

    @NonNull
    @Override
    public TopGainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        return new TopGainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopGainerViewHolder holder, int position) {
        holder.name.setText(topGainerList.get(position).getName());
        holder.price.setText(topGainerList.get(position).getPrize());
        holder.mImageView.setImageResource(topGainerList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return topGainerList.size();
    }

    public class TopGainerViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private ImageView mImageView;
        public TopGainerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.watchlist_signname);
            price = itemView.findViewById(R.id.watchlist_prize);
            mImageView = itemView.findViewById(R.id.watchlist_img);
        }
    }
}
