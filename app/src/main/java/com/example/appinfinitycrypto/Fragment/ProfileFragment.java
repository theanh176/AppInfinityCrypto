package com.example.appinfinitycrypto.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appinfinitycrypto.ChangeProfile;
import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.MainActivity;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.MyApplication;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.SessionManager;
import com.example.appinfinitycrypto.SignIn;
import com.example.appinfinitycrypto.activity_profile_user;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private DatabaseReference ref;
    TextView txtFullNamePro, txtPhonePro, txtPasswordPro, txtEmailPro, txtBirthdayPro, txtGender;
    TextView txtLogOut, txtChangePass, txtChangeProfile, txtWatchList;
    CountryCodePicker ccpCountryPro;
    SessionManager sessionManager;

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
        ccpCountryPro = view.findViewById(R.id.ccpCountryProfile);

        txtLogOut = view.findViewById(R.id.txtLogOut);
        txtChangePass = view.findViewById(R.id.txtChangePassPro);
        txtChangeProfile = view.findViewById(R.id.txtChangePro);
        txtWatchList = view.findViewById(R.id.txtWatchlist_pro);

        LoadData();
        LoadListener();
        UpdateData();

        return view;
    }

    private void LoadListener(){

        txtChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ChangeProfileFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.add(R.id.fragment_profile, fragment);
                fm.addToBackStack(null);
                fm.commit();
            }

        });

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ChangePasswordFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.add(R.id.fragment_profile, fragment);
                fm.addToBackStack(null);
                fm.commit();
            }
        });

        txtWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Huu Hieu
//                Intent intent = new Intent(ProfileFragment.this.getContext(), SignIn.class);
////                ((MyApplication) getActivity().getApplication()).setSomeVariable("");
//                startActivity(intent);

//                getActivity().getSupportFragmentManager().popBackStack();

                // The Anh
                DataLocalManager.setFirstInstall(false);
                DataLocalManager.setPhoneInstall("");
                Intent intent = new Intent(getActivity(), SignIn.class);
                intent.putExtra("checkSignIn","");
                startActivity(intent);
            }
        });
    }

    private void LoadData(){
//        String phone = ((MyApplication) getActivity().getApplication()).getSomeVariable();
        String phone = DataLocalManager.getPhoneInstall();
        ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                System.out.println(snapshot.child("name").getValue());
                txtFullNamePro.setText(account.getName());
                txtEmailPro.setText(account.getEmail());
                txtPasswordPro.setText(account.getPass());
                txtPhonePro.setText("*******" + account.getPhone().substring(7));
                txtBirthdayPro.setText(account.getDate());
                txtGender.setText(account.getSex());
                ccpCountryPro.setCountryForPhoneCode(Integer.parseInt(account.getCountry()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UpdateData() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Account");
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Account account = snapshot.getValue(Account.class);
                System.out.println(snapshot.child("name").getValue());
                txtFullNamePro.setText(account.getName());
                txtEmailPro.setText(account.getEmail());
                txtPasswordPro.setText(account.getPass());
                txtPhonePro.setText("*******" + account.getPhone().substring(7));
                txtBirthdayPro.setText(account.getDate());
                txtGender.setText(account.getSex());
                ccpCountryPro.setCountryForPhoneCode(Integer.parseInt(account.getCountry()));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}