package com.example.appinfinitycrypto.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.MyApplication;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private DatabaseReference ref;
    TextView txtFullNamePro, txtPhonePro, txtPasswordPro, txtEmailPro, txtBirthdayPro, txtGender;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFullNamePro = view.findViewById(R.id.txtFullNameProfile);
        txtEmailPro = view.findViewById(R.id.txtEmailProfile);
        txtPasswordPro = view.findViewById(R.id.txtPasswordProfile);
        txtPhonePro = view.findViewById(R.id.txtPhoneProfile);
        txtBirthdayPro = view.findViewById(R.id.txtBirthDayProfile);
        txtGender = view.findViewById(R.id.txtGenderProfile);
        LoadData();
        return view;
    }

    private void LoadData(){
        String phone = ((MyApplication) getActivity().getApplication()).getSomeVariable();
        ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                System.out.println(snapshot.child("name").getValue());
                txtFullNamePro.setText(account.getName());
                txtEmailPro.setText(account.getEmail());
                txtPasswordPro.setText(account.getPass());
                txtPhonePro.setText(getActivity().getIntent().getStringExtra("phone"));
                txtBirthdayPro.setText(account.getDate());
                txtGender.setText(account.getSex());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}