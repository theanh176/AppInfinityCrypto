package com.example.appinfinitycrypto.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.HomeAdminAdapter;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.my_interface.IClickShowProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {

    private RecyclerView recyclerViewAccount;
    private EditText editTextSearch;
    private TextView textViewTotal;
    private DatabaseReference database;
    private HomeAdminAdapter accountAdapter;
    private List<Account> accountList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeAdminFragment newInstance(String param1, String param2) {
        HomeAdminFragment fragment = new HomeAdminFragment();
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
        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);

        recyclerViewAccount = view.findViewById(R.id.recyclerViewAccount);
        editTextSearch = view.findViewById(R.id.edit_search);
        textViewTotal = view.findViewById(R.id.textViewTotalOnline);

        accountList = new ArrayList<>();
        accountAdapter = new HomeAdminAdapter(accountList, new IClickShowProfile() {
            @Override
            public void onClickShowProfile(String phone) {
                openProfileDialog(Gravity.CENTER, phone);
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
                    accountList.add(account);
                }
                accountAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getActivity(), "Get list data faile!!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void openProfileDialog(int gravity, String phone){
        final Dialog dialog =new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_profile);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER ==gravity){
            dialog.setCancelable(false);
        } else{
            dialog.setCancelable(true);
        }
        TextView textViewName = dialog.findViewById(R.id.txtNameDialog);
        TextView textViewEmail = dialog.findViewById(R.id.txtEmailDialog);
        TextView textViewPhone = dialog.findViewById(R.id.txtPhoneDialog);
        TextView textViewBirth = dialog.findViewById(R.id.txtBirthDayDialog);
        TextView textViewGender = dialog.findViewById(R.id.txtGenderDialog);
        CountryCodePicker countryDialog = dialog.findViewById(R.id.ccpCountryDialog);

        Button btnClose = dialog.findViewById(R.id.btnCloseDialog);

        database = FirebaseDatabase.getInstance().getReference("Account").child(phone);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                textViewName.setText(account.getName());
                textViewEmail.setText(account.getEmail());
                textViewPhone.setText(account.getPhone());
                textViewBirth.setText(account.getDate());
                textViewGender.setText(account.getSex());
                countryDialog.setCountryForPhoneCode(Integer.parseInt(account.getCountry()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getTotalOnline(){
        database = FirebaseDatabase.getInstance().getReference("Account");
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int totaloff = 0;
                int totalon = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    final Boolean getPhoneonline = snapshot.child(dataSnapshot.getKey()).child("isOnline").getValue(Boolean.class);
                    if(getPhoneonline.equals(true)){
                        totalon++;
                    }
                    else if (getPhoneonline.equals(false)) {
                        totaloff++;
                    } else {
                        return;
                    }

                }
                int total = totaloff + totalon;
                textViewTotal.setText("Online " + totalon + "/" + total);


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
        getTotalOnline();
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