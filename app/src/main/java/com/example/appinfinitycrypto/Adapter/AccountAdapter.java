package com.example.appinfinitycrypto.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.R;

import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> implements Filterable {
    List<Account> accountList;
    List<Account> accountListOld;


    public AccountAdapter(List<Account> accountList) {
        this.accountList = accountList;
        this.accountListOld = accountList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Account accounts = accountList.get(position);
        if(accounts==null){
            return;
        }
        holder.textFullName.setText(accounts.getName());
        holder.textPhone.setText(accounts.getPhone());
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

        public  MyViewHolder (@NonNull View itemView){
            super(itemView);
            textFullName = itemView.findViewById(R.id.fullname);
            textPhone = itemView.findViewById(R.id.phone);
        }
    }
}
