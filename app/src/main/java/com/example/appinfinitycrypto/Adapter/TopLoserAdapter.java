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

import com.example.appinfinitycrypto.Model.TopLoser;

public class TopLoserAdapter extends RecyclerView.Adapter<TopLoserAdapter.TopLoserViewHolder> {

    private List<TopLoser> topLoserList;

    public TopLoserAdapter(List<TopLoser> topLoserList) {
        this.topLoserList = topLoserList;
    }

    @NonNull
    @Override
    public TopLoserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        return new TopLoserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopLoserViewHolder holder, int position) {
        holder.name.setText(topLoserList.get(position).getName());
        holder.price.setText(topLoserList.get(position).getPrize());
        holder.mImageView.setImageResource(topLoserList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return topLoserList.size();
    }

    public class TopLoserViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private ImageView mImageView;
        public TopLoserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.watchlist_signname);
            price = itemView.findViewById(R.id.watchlist_prize);
            mImageView = itemView.findViewById(R.id.watchlist_img);
        }
    }
}
