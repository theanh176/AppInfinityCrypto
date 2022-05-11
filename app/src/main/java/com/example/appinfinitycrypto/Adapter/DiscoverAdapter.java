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

import com.example.appinfinitycrypto.Model.Discover;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private List<Discover> discoverList;

    public DiscoverAdapter(List<Discover> discoverList) {
        this.discoverList = discoverList;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_coin, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        Discover discover = discoverList.get(position);
        if(discover==null){
            return;
        }
        holder.title.setText(discoverList.get(position).getTitle());
        holder.day.setText(discoverList.get(position).getDay());
//        holder.mImageView.setImageResource(discoverList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        if(discoverList!=null){
            return discoverList.size();
        }
        return 0;
    }

    public class DiscoverViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView day;
        private ImageView mImageView;
        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.discover_title);
            day = itemView.findViewById(R.id.discover_day);
//            mImageView = itemView.findViewById(R.id.discover_img);
        }
    }
}
