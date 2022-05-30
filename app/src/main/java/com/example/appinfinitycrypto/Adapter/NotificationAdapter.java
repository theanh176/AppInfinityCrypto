package com.example.appinfinitycrypto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.DataItem_Notify;
import com.example.appinfinitycrypto.Model.Notification;
import com.example.appinfinitycrypto.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    List<DataItem_Notify> notifyList;
    public NotificationAdapter(List<DataItem_Notify> notifyList) {
        this.notifyList = notifyList;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        DataItem_Notify dataItem_notify = notifyList.get(position);
        String description = dataItem_notify.getDescription();
        String title = dataItem_notify.getTitle();
        holder.txt_description.setText(description);
        holder.txt_title.setText(title);
    }

    @Override
    public int getItemCount() {
        if(notifyList!=null){
            return notifyList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_description, txt_title;

        public  MyViewHolder (@NonNull View itemView){
            super(itemView);
            txt_description = itemView.findViewById(R.id.notification_description);
            txt_title = itemView.findViewById(R.id.notification_title);
        }
    }
}
