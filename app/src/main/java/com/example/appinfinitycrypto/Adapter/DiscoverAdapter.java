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
import com.example.appinfinitycrypto.Model.DataNew;
import com.example.appinfinitycrypto.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private List<DataNew> mDataItem;
    private List<DataNew> mDataItemOld;

    public DiscoverAdapter(List<DataNew> mDataItem) {
        this.mDataItem = mDataItem;
        this.mDataItemOld = mDataItem;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        DataNew dataItem = mDataItem.get(position);
        if (dataItem == null) {
            return;
        }
        holder.title.setText(dataItem.getTitle());
        holder.published_on.setText(dataItem.getPublished_on());
    }

    @Override
    public int getItemCount() {
        if(mDataItem != null) {
            return mDataItem.size();
        }
        return 0;
    }

    public class DiscoverViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView published_on;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.discover_title);
            published_on = itemView.findViewById(R.id.discover_day);
        }
    }

}
