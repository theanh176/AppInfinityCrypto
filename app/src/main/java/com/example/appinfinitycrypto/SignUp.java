package com.example.appinfinitycrypto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private DatabaseReference database;
    EditText editTextDate, editTextUsername, editTextPassword, editTextConfPassword, editTextEmail, editTextPhone;
    RadioButton radioButtonMale, radioButtonFemale;
    CountryCodePicker ccpCountry;
    View imgBackSignUp;
    Button btnSignup;

    //    Change the status bar color
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public void setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTranslucentStatusBar();

        mapping();
        event();
    }

    private void event(){
        imgBackSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSignUp();
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAccount();
            }
        });


    }

    private void mapping(){
        imgBackSignUp = findViewById(R.id.imgBackSignIn);
        editTextUsername = findViewById(R.id.edtUsernameSignup);
        editTextPhone = findViewById(R.id.edtPhoneSignup);
        editTextEmail = findViewById(R.id.edtEmailSignup);
        editTextDate = findViewById(R.id.edtBirthSignup);
        editTextPassword = findViewById(R.id.edtPassSignup);
        editTextConfPassword = findViewById(R.id.edtConfPassSignup);
        radioButtonMale = findViewById(R.id.radio_Male);
        radioButtonFemale = findViewById(R.id.radio_Female);
        ccpCountry = findViewById(R.id.ccpCountrySignup);
        btnSignup = findViewById(R.id.btnSignup);
    }

    private void backSignUp() {
        Intent intent = new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
    }

    private void datePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editTextDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year, month, day);
        datePickerDialog.show();
    }

    private String sex(){
        String sex = "";
        if(radioButtonMale.isChecked()){
            sex = radioButtonMale.getText().toString();
        }
        if(radioButtonFemale.isChecked()){
            sex = radioButtonFemale.getText().toString();
        }
        return sex;
    }

    private void registerAccount(){
        final String name = editTextUsername.getText().toString();
        final String phone = editTextPhone.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String date = editTextDate.getText().toString();
        final String pass = editTextPassword.getText().toString();
        final String confpass = editTextConfPassword.getText().toString();
        sex();
        final String country = ccpCountry.getSelectedCountryEnglishName();

        if(name.isEmpty()||phone.isEmpty()||email.isEmpty()||date.isEmpty()||pass.isEmpty()||confpass.isEmpty()){
            Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(confpass)){
            Toast.makeText(SignUp.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
        }
        else{
            database = FirebaseDatabase.getInstance().getReference("Account");

            Account account = new Account(name, email, date, pass, country, sex());
            database.child(phone).setValue(account);

            Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();

            editTextUsername.setText("");
            editTextPhone.setText("");
            editTextEmail.setText("");
            editTextDate.setText("");
            editTextPassword.setText("");
            editTextConfPassword.setText("");
            radioButtonMale.setChecked(true);
            ccpCountry.setCountryForNameCode("VN");
        }
    }

    public static class Account{
        public String name;
        public String email;
        public String date;
        public String pass;
        public String country;
        public String sex;

        public Account(String name, String email, String date, String pass, String country, String sex) {
            this.name = name;
            this.email = email;
            this.date = date;
            this.pass = pass;
            this.country = country;
            this.sex = sex;
        }

        public Account(){

        }

    }
}