package com.example.appinfinitycrypto.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.DataNews;
import com.example.appinfinitycrypto.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {
    private List<DataNews> mDataItem;
    private List<DataNews> mDataItemOld;

    public DiscoverAdapter(List<DataNews> mDataItem) {
        this.mDataItem = mDataItem;
        this.mDataItemOld = mDataItem;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_coin, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        DataNews dataItem = mDataItem.get(position);
        if (dataItem == null) {
            return;
        }
        holder.title.setText(dataItem.getTitle());
        holder.day.setText(dataItem.getPublished_on());
        LoadImage loadImage = new LoadImage(holder.image);
        loadImage.execute(dataItem.getImageurl());

    }

    @Override
    public int getItemCount() {
        if(mDataItem != null) {
            return mDataItem.size();
        }
        return 0;
    }

    class DiscoverViewHolder extends RecyclerView.ViewHolder{

        private TextView title, day;
        private ImageView image;

        public DiscoverViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.discover_title);
            day = itemView.findViewById(R.id.discover_day);
            image = itemView.findViewById(R.id.discover_img);

            //Event Click Open Browser
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        DataNews dataItem = mDataItem.get(position);
                        if (dataItem != null) {
                            String href = dataItem.getUrl();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
                            v.getContext().startActivity(browserIntent);
                        }
                        else {
                            Log.d("DiscoverAdapter", "onClick: null");
                        }
                    }
                    else {
                        Log.d("DiscoverAdapter", "onClick: NO_POSITION");
                    }
                }
            });
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
