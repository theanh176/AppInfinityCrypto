package com.example.appinfinitycrypto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.FeedBack;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>{
    List<FeedBack> feedBackList;

    public FeedbackAdapter(List<FeedBack> feedBackList) {
        this.feedBackList= feedBackList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_feedback, parent, false);
        return new FeedbackAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FeedBack feedBack = feedBackList.get(position);
        String title = feedBack.getTitle();
        String description = feedBack.getDescription();
        String time = feedBack.getDate();
        if(feedBack==null){
            return;
        }
        holder.textTitle.setText(title);
        holder.textDescription.setText(description);
        holder.textTime.setText(time);

    }

    @Override
    public int getItemCount() {
        if(feedBackList!=null){
            return feedBackList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDescription, textTime;

        public  MyViewHolder (@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.titlefeedback);
            textDescription = itemView.findViewById(R.id.descfeedback);
            textTime = itemView.findViewById(R.id.timefeedback);
        }
    }
}
