package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.AccountAdapter;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.my_interface.IClickItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    private RecyclerView recyclerViewAccount;
    private EditText editTextSearch;
    private DatabaseReference database;
    private AccountAdapter accountAdapter;
    private List<Account> accountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mapping();
        event();
    }
    private void getListPhoneBooksRealtimeDB() {
        database = FirebaseDatabase.getInstance().getReference("Account");
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accountList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account account = dataSnapshot.getValue(Account.class);
                    accountList.add(account);
                }
                accountAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(Admin.this, "Get list data faile!!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void mapping(){
        recyclerViewAccount = findViewById(R.id.recyclerViewAccount);
        editTextSearch = findViewById(R.id.edit_search);

        accountList = new ArrayList<>();
        accountAdapter = new AccountAdapter(accountList, new IClickItem() {
            @Override
            public void onClickItemSetRule(String phone) {

            }

            @Override
            public void onClickItemDelete(String phone) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewAccount.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewAccount.addItemDecoration(dividerItemDecoration);
        recyclerViewAccount.setAdapter(accountAdapter);
    }

    private void event(){
        getListPhoneBooksRealtimeDB();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Admin.this.accountAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}