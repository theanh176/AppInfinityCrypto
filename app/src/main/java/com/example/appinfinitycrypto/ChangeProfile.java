package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.appinfinitycrypto.Model.Account;
import com.firebase.ui.database.FirebaseArray;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChangeProfile extends AppCompatActivity {

    private EditText editFullName, editEmailPro, editBirthday, editCountry;
    RadioButton r_male, r_female, r_difference;
    Button btnSaveChangePro, btnBackChange;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        Mapping();

        String id = getIntent().getStringExtra("id");
        databaseReference = FirebaseDatabase.getInstance().getReference("Account").child(id);

        btnSaveChangePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.getRef().setValue(createAccount());
//                Đóng activity hiện tại
                finish();
            }
        });

        btnBackChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

    private void Mapping() {
        editFullName = findViewById(R.id.editFullNameProfile);
        editEmailPro = findViewById(R.id.editEmailProfile);
        editBirthday = findViewById(R.id.editBirthDayProfile);
        editCountry = findViewById(R.id.editCountryProfile);

        btnSaveChangePro =findViewById(R.id.btnSaveChange);
        btnBackChange = findViewById(R.id.btnBackChange);

        r_male = findViewById(R.id.radio_Male);
        r_female = findViewById(R.id.radio_Female);
        r_difference = findViewById(R.id.radioDifference);
    }

    private Account createAccount() {
        String name = editFullName.getText().toString();
        String email = editEmailPro.getText().toString();
        String birthDay = editBirthday.getText().toString();
        String gender = "";
        if (r_male.isChecked()) {
            gender = "Nam";
        } else if (r_female.isChecked()) {
            gender = "Nữ";
        } else if (r_difference.isChecked()) {
            gender = "Khác";
        }
        String country = editCountry.getText().toString();
        String pass = getIntent().getStringExtra("pass");
        Account account = new Account(name, email, birthDay, pass, country, gender);

        return account;
    }
}