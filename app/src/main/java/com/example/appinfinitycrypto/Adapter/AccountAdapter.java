package com.example.appinfinitycrypto.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appinfinitycrypto.Fragment.ListUserFragment;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.DataItem;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.my_interface.IClickItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.file.DirectoryStream;
import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> implements Filterable {
    List<Account> accountList;
    List<Account> accountListOld;
    private DatabaseReference database;
    private IClickItem iClickItem;


    public AccountAdapter(List<Account> accountList, IClickItem iClick) {
        this.accountList = accountList;
        this.accountListOld = accountList;
        this.iClickItem = iClick;
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
        String name = accounts.getName();
        String phone = accounts.getPhone();
        if(accounts==null){
            return;
        }
        holder.textFullName.setText(name);
        holder.textPhone.setText(phone);

        holder.imgDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItem.onClickItemDelete(phone);
            }
        });
        holder.imgSetRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItem.onClickItemSetRule(phone);
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
        ImageView imgSetRule, imgDeleteUser;

        public  MyViewHolder (@NonNull View itemView){
            super(itemView);
            textFullName = itemView.findViewById(R.id.fullname);
            textPhone = itemView.findViewById(R.id.phone);
            imgDeleteUser = itemView.findViewById(R.id.imgViewDelete);
            imgSetRule = itemView.findViewById(R.id.imgViewSetRule);
        }
    }
}
