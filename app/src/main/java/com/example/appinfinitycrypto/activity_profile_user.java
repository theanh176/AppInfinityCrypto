package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    TextView txtFullNamePro, txtPhonePro, txtPasswordPro, txtEmailPro, txtBirthdayPro, txtGender, txtCountry;
    TextView txtLogOut, txtChangePass, txtChangeProfile, txtWatchList;

    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        String id = getIntent().getStringExtra("phone");
        System.out.println(getIntent().getStringExtra("phone"));

        Mapping();
        LoadData();

        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profile_user.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profile_user.this, ChangePassword.class);

                startActivity(intent);
            }
        });

        txtChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profile_user.this, ChangeProfile.class);
                intent.putExtra("fullName", txtFullNamePro.getText().toString());
                intent.putExtra("email", txtEmailPro.getText().toString());
                intent.putExtra("phone", txtPhonePro.getText().toString());
                intent.putExtra("pass", pass);
                intent.putExtra("birthday",txtBirthdayPro.getText().toString());
                intent.putExtra("gender", txtGender.getText().toString());
                intent.putExtra("country", txtCountry.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void Mapping(){
        txtFullNamePro = findViewById(R.id.txtFullNameProfile);
        txtEmailPro = findViewById(R.id.txtEmailProfile);
        txtPasswordPro = findViewById(R.id.txtPasswordProfile);
        txtPhonePro = findViewById(R.id.txtPhoneProfile);
        txtBirthdayPro = findViewById(R.id.txtBirthDayProfile);
        txtGender = findViewById(R.id.txtGenderProfile);
        txtCountry = findViewById(R.id.txtCountryProfile);

        txtLogOut = findViewById(R.id.txtLogOut);
        txtChangePass = findViewById(R.id.txtChangePassPro);
        txtChangeProfile = findViewById(R.id.txtChangeProfile);
        txtWatchList = findViewById(R.id.txtWatchlist_pro);
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
                txtCountry.setText(account.getCountry());
                pass = account.getPass();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}