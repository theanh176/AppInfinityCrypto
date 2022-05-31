package com.example.appinfinitycrypto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.FeedBack;
import com.example.appinfinitycrypto.Model.Notification;
import com.example.appinfinitycrypto.R;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {

    List<Notification> notificationList;

    public NotifyAdapter(List<Notification> notificationList) {
        this.notificationList= notificationList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_notify, parent, false);
        return new NotifyAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        String title = notification.getTitle();
        String description = notification.getDescription();
        if(notification==null){
            return;
        }
        holder.textTitle.setText(title);
        holder.textDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        if(notificationList!=null){
            DataLocalManager.setKeyIDNotify(notificationList.size());
            return notificationList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDescription;

        public  MyViewHolder (@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.titleNotification);
            textDescription = itemView.findViewById(R.id.descNotification);
        }
    }
}
