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

import com.example.appinfinitycrypto.Model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.content.setText(notificationList.get(position).getContent());
        holder.time.setText(notificationList.get(position).getTime());
        holder.mIconView.setImageResource(notificationList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView time;
        private ImageView mIconView;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.notification_content);
            time = itemView.findViewById(R.id.notification_date);
            mIconView = itemView.findViewById(R.id.notification_img);
        }
    }
}
