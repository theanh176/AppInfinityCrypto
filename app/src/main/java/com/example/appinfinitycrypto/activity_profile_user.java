package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appinfinitycrypto.Model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_profile_user extends AppCompatActivity {

    private DatabaseReference ref;
    TextView txtFullNamePro, txtPhonePro, txtPasswordPro, txtEmailPro, txtBirthdayPro, txtGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);


        System.out.println(getIntent().getStringExtra("phone"));
        System.out.println(getIntent().getStringExtra("phone"));

        Mapping();
        LoadData();
    }

    private void Mapping(){
        txtFullNamePro = findViewById(R.id.txtFullNameProfile);
        txtEmailPro = findViewById(R.id.txtEmailProfile);
        txtPasswordPro = findViewById(R.id.txtPasswordProfile);
        txtPhonePro = findViewById(R.id.txtPhoneProfile);
        txtBirthdayPro = findViewById(R.id.txtBirthDayProfile);
        txtGender = findViewById(R.id.txtGenderProfile);
    }

    private void LoadData(){
        ref = FirebaseDatabase.getInstance().getReference("Account").child(getIntent().getStringExtra("phone"));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                System.out.println(snapshot.child("name").getValue());
                txtFullNamePro.setText(account.getName());
                txtEmailPro.setText(account.getEmail());
                txtPasswordPro.setText(account.getPass());
                txtPhonePro.setText(getIntent().getStringExtra("phone"));
                txtBirthdayPro.setText(account.getDate());
                txtGender.setText(account.getSex());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}