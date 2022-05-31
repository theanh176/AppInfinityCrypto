package com.example.appinfinitycrypto.Adapter;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Fragment.ListUserFragment;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.my_interface.IClickShowProfile;
import com.example.appinfinitycrypto.my_interface.ItemClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeAdminAdapter extends RecyclerView.Adapter<HomeAdminAdapter.MyViewHolder> implements Filterable {
    List<Account> accountList;
    List<Account> accountListOld;
    private IClickShowProfile iClickShowProfile;

    public HomeAdminAdapter(List<Account> accountList, IClickShowProfile iClick) {
        this.accountList = accountList;
        this.accountListOld = accountList;
        this.iClickShowProfile =iClick;

    }

    @NonNull
    @Override
    public HomeAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_admin, parent, false);
        return new HomeAdminAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdminAdapter.MyViewHolder holder, int position) {
        Account accounts = accountList.get(position);
        String name = accounts.getName();
        String phone = accounts.getPhone();
        Boolean check = accounts.getOnline();
        if(accounts!=null){
            if(check.equals(true)){
                holder.imgCheck.setImageResource(R.drawable.ic_check);
            }else {
                holder.imgCheck.setImageResource(R.drawable.ic_check_no);
            }
        }else{
            return;
        }

        holder.textFullName.setText(name);
        holder.textPhone.setText(phone);

        holder.textFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickShowProfile.onClickShowProfile(phone);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(accountList!=null){
            return accountList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    accountList = accountListOld;
                } else {
                    List<Account> list = new ArrayList<>();
                    for (Account account: accountListOld) {
                        if (account.getName().toLowerCase().contains(strSearch.toLowerCase()) ||
                                account.getPhone().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(account);
                        }
                    }
                    accountList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = accountList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                accountList = (List<Account>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textFullName, textPhone;
        ImageView imgCheck;

        public  MyViewHolder (@NonNull View itemView){
            super(itemView);
            textFullName = itemView.findViewById(R.id.fullname);
            textPhone = itemView.findViewById(R.id.phone);
            imgCheck = itemView.findViewById(R.id.check);
        }
    }

}
