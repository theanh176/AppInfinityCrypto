package com.example.appinfinitycrypto.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.AccountAdapter;
import com.example.appinfinitycrypto.Adapter.AccountAdminAdapter;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.my_interface.IClickItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAdminFragment extends Fragment {

    private RecyclerView recyclerViewAccount;
    private EditText editTextSearch;
    private DatabaseReference database;
    private AccountAdminAdapter accountAdapter;
    private List<Account> accountList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListAdminFragment newInstance(String param1, String param2) {
        ListAdminFragment fragment = new ListAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_admin, container, false);
        recyclerViewAccount = view.findViewById(R.id.recyclerViewAccount);
        editTextSearch = view.findViewById(R.id.edit_search);

        accountList = new ArrayList<>();
        accountAdapter = new AccountAdminAdapter(accountList, new IClickItem() {
            @Override
            public void onClickItemSetRule(String phone) {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
                dialogDelete.setMessage("Bạn chắc chắn muốn xóa quyền Admin của tài khoản có số điện thoại  " +phone+ " ?");
                dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database = FirebaseDatabase.getInstance().getReference("Account");
                        database.child(phone).child("rule").setValue("user");
                    }
                });
                dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogDelete.show();
            }

            @Override
            public void onClickItemDelete(String phone) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewAccount.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewAccount.addItemDecoration(dividerItemDecoration);
        recyclerViewAccount.setAdapter(accountAdapter);
        event();
        return view;
    }
    private void getListPhoneBooksRealtimeDB() {
        database = FirebaseDatabase.getInstance().getReference("Account");
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accountList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account account = dataSnapshot.getValue(Account.class);
                    final String getRule = snapshot.child(dataSnapshot.getKey()).child("rule").getValue(String.class);
                    if(getRule.equals("admin")){
                        accountList.add(account);
                    }
                }
                accountAdapter.notifyDataSetChanged();
//                final String getRule = snapshot.child("rule").getValue(String.class);
//                System.out.println(getRule);
//                if(getRule.equals("user")){
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Account account = dataSnapshot.getValue(Account.class);
//                        accountList.add(account);
//                    }
//                    accountAdapter.notifyDataSetChanged();
//                }else{
//                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
//                }

//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        System.out.println(dataSnapshot.getKey());
//                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getActivity(), "Get list data faile!!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    private void event(){
        getListPhoneBooksRealtimeDB();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                accountAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}