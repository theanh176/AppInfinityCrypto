package com.example.appinfinitycrypto.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.MyApplication;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordFragment extends Fragment {
    private DatabaseReference ref;
    private EditText editOldPassword, editNewPassword, editReenterPassword;
    private ImageView imgBack;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        editNewPassword = view.findViewById(R.id.editNewPasswordProfile);
        editOldPassword = view.findViewById(R.id.editOldPasswordProfile);
        editReenterPassword = view.findViewById(R.id.editReenterPasswordProfile);

        imgBack = view.findViewById(R.id.btnBackPassProChange);
        btnSave = view.findViewById(R.id.btnSavePassProChange);

        LoadListenerEvent();

        return view;
    }

    private void LoadListenerEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editNewPassword.getText().toString().isEmpty()
                || editOldPassword.getText().toString().isEmpty()
                || editReenterPassword.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordFragment.this.getContext(), "Y??u c???u nh???p ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = DataLocalManager.getPhoneInstall();
                    ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Account account = snapshot.getValue(Account.class);
                            if (editOldPassword.getText().toString().equals(account.getPass())) {
                                if (editNewPassword.getText().toString().equals(editReenterPassword.getText().toString())) {
                                    Toast.makeText(ChangePasswordFragment.this.getContext(), "Change Successfully", Toast.LENGTH_SHORT).show();
                                    ref.child("pass").setValue(editNewPassword.getText().toString());
                                    getActivity().getSupportFragmentManager().popBackStack();
                                } else {
                                    Toast.makeText(ChangePasswordFragment.this.getContext(), "M???t kh???u m???i kh??ng tr??ng kh???p", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ChangePasswordFragment.this.getContext(), "Nh???p sai m???t kh???u", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}