package com.example.appinfinitycrypto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.WatchList;
import com.example.appinfinitycrypto.R;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> {

    private List<WatchList> watchListList;

    public WatchListAdapter(List<WatchList> watchListList) {
        this.watchListList = watchListList;
    }

    @NonNull
    @Override
    public WatchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch_list, parent, false);
        return new WatchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListAdapter.WatchListViewHolder holder, int position) {
        holder.name.setText(watchListList.get(position).getName());
        holder.prize.setText(watchListList.get(position).getPrize());
        holder.img.setImageResource(watchListList.get(position).getImage());
        holder.change.setText(watchListList.get(position).getChange());
        holder.change_icon.setImageResource(watchListList.get(position).getImage_change());
        holder.chart.setImageResource(watchListList.get(position).getChart());
        holder.sign_name.setText(watchListList.get(position).getSign_name());
        holder.star.setImageResource(watchListList.get(position).getStar());
    }

    @Override
    public int getItemCount() {
        return watchListList.size();
    }

    public class WatchListViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView prize;
        private TextView change;
        private TextView sign_name;
        private ImageView change_icon;
        private ImageView img;
        private ImageView chart;
        private ImageView star;

        public WatchListViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.watchlist_signname);
            prize = itemView.findViewById(R.id.watchlist_prize);
            change = itemView.findViewById(R.id.watchlist_change);
            sign_name = itemView.findViewById(R.id.watchlist_signname);
            change_icon = itemView.findViewById(R.id.watchlist_changelogo);
            img = itemView.findViewById(R.id.watchlist_img);
            chart = itemView.findViewById(R.id.watchlist_chart);
            star = itemView.findViewById(R.id.watchlist_star);
        }
    }
}
