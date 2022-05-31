package com.example.appinfinitycrypto.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.MyApplication;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ChangeProfileFragment extends Fragment {
    private DatabaseReference ref;
    private EditText editFullName, editEmail, editBirthday;
    private RadioButton r_male, r_female, r_difference;
    private CountryCodePicker ccpCountryProfileChange;
    private ImageView imgBack;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_profile, container, false);

//        Ánh xạ các view
        editFullName = view.findViewById(R.id.editFullNameProfile);
        editEmail = view.findViewById(R.id.editEmailProfile);
        editBirthday = view.findViewById(R.id.editBirthDayProfile);

        r_male = view.findViewById(R.id.radioMaleProChange);
        r_female = view.findViewById(R.id.radioFemaleProChange);
        r_difference = view.findViewById(R.id.radioDifferenceProChange);

        ccpCountryProfileChange = view.findViewById(R.id.ccpCountryProfileChange);

        imgBack = view.findViewById(R.id.btnBackPassProChange);
        btnSave = view.findViewById(R.id.btnSaveProChange);


        LoadData();
        LoadListener();

        return view;
    }

    private void LoadListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFullName.getText().toString().isEmpty()
                || editEmail.getText().toString().isEmpty()) {
                    Toast.makeText(ChangeProfileFragment.this.getContext(), "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {

                    String phone = DataLocalManager.getPhoneInstall();
                    ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
                    ref.child("name").setValue(editFullName.getText().toString());
                    ref.child("email").setValue(editEmail.getText().toString());
                    if (r_male.isChecked()) {
                        ref.child("sex").setValue("Male");
                    } else if (r_female.isChecked()) {
                        ref.child("sex").setValue("Female");
                    } else {
                        ref.child("sex").setValue("Difference");
                    }
                    ref.child("country").setValue(ccpCountryProfileChange.getSelectedCountryCode());
                }
                Toast.makeText(ChangeProfileFragment.this.getContext(), "Change Successfully", Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new ProfileFragment()).commit();
            }
        });
    }

    private void LoadData() {
//        String phone = ((MyApplication) getActivity().getApplication()).getSomeVariable();
        String phone = DataLocalManager.getPhoneInstall();
        ref = FirebaseDatabase.getInstance().getReference("Account").child(phone);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                System.out.println(snapshot.child("name").getValue());
                editFullName.setText(account.getName());
                editEmail.setText(account.getEmail());
                editBirthday.setText(account.getDate());

                if (account.getSex().equals("Male")) {
                    r_male.setChecked(true);
                } else if (account.getSex().equals("Female")) {
                    r_female.setChecked(true);
                } else {
                    r_difference.setChecked(true);
                }
                ccpCountryProfileChange.setCountryForPhoneCode(Integer.parseInt(account.getCountry()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
