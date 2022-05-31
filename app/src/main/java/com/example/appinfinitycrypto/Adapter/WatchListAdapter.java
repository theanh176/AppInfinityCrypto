package com.example.appinfinitycrypto.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.MyApplication;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.WatchListActivity;
import com.example.appinfinitycrypto.my_interface.ITransmitData;
import com.example.appinfinitycrypto.my_interface.ItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> {

    private final List<String> myList;
    private String phone;
    private List<DataItem> mDataItem;
    private ITransmitData iTransmitData;

    public WatchListAdapter(List<DataItem> mDataItem, String phone, List<String> myList, ITransmitData iTransmitData) {
        this.mDataItem = mDataItem;
        this.phone = phone;
        this.myList = myList;
        this.iTransmitData = iTransmitData;
    }

    @NonNull
    @Override
    public WatchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch_list, parent, false);
        return new WatchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataItem dataItem = mDataItem.get(position);
        if (dataItem == null) {
            return;
        }
        holder.symbol.setText(dataItem.getSymbol());
        holder.price.setText(String.format("$%.2f", dataItem.getQuote().getUsd().getPrice()));
        holder.name.setText(dataItem.getName());
        if (dataItem.getQuote().getUsd().getPercent_change_24h() < 0) {
            holder.change.setTextColor(Color.RED);
            holder.change.setText("-" + String.format("$%.2f",dataItem.getQuote().getUsd().getPercent_change_24h()) + "%");
            holder.mImageViewChange.setImageResource(R.drawable.caret_down_red);
        } else{
            holder.change.setTextColor(Color.GREEN);
            holder.change.setText("+" + String.format("$%.2f",dataItem.getQuote().getUsd().getPercent_change_24h()) + "%");
            holder.mImageViewChange.setImageResource(R.drawable.caret_up_green);
        }

        LoadImage loadImage = new LoadImage(holder.mImageView);
        loadImage.execute("https://s2.coinmarketcap.com/static/img/coins/64x64/" + dataItem.getId() + ".png");
        holder.star.setImageResource(R.drawable.ic_star_fill);


        //click star set value to firebase
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Account").child(phone).child("watchlist").child(dataItem.getSymbol());
                databaseReference.setValue(null);
                mDataItem.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongCLick) {

                iTransmitData.senData(mDataItem.get(position).getId());
//                Toast.makeText(context, "" + mDataItem.get(position).getId() + " " + mDataItem.get(position).getSymbol(), Toast.LENGTH_SHORT).show();

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

    public int getPosition() {
        return mDataItem.indexOf(mDataItem);
    }

    public class WatchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView symbol;
        private TextView price;
        private ImageView mImageView;
        private TextView name;
        private TextView change;
        private ImageView mImageViewChange;
        private ImageView star;

        private ItemClickListener itemClickListener;

        public WatchListViewHolder(@NonNull View itemView) {
            super(itemView);

            symbol = itemView.findViewById(R.id.watchlist_symbol);
            price = itemView.findViewById(R.id.watchlist_price);
            mImageView = itemView.findViewById(R.id.watchlist_img);
            name = itemView.findViewById(R.id.watchlist_name);
            change = itemView.findViewById(R.id.watchlist_change);
            mImageViewChange = itemView.findViewById(R.id.watchlist_changelogo);
            star = itemView.findViewById(R.id.watchlist_star);

            itemView.setOnClickListener((View.OnClickListener) this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
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
