package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    EditText editTextDate, editTextUsername, editTextPassword, editTextConfPassword, editTextEmail, editTextPhone;
    RadioButton radioButtonMale, radioButtonFemale;
    CountryCodePicker ccpCountry;
    View imgBackSignUp;
    Button btnSignup;
    ProgressBar progressBar;

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
        imgBackSignUp = findViewById(R.id.imgBackVerifi);
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
        progressBar = findViewById(R.id.progessBar);
    }

    private void backSignUp() {
        Intent intent = new Intent(SignUp.this, SignIn.class);
        intent.putExtra("checkSignIn","");
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

        if(name.isEmpty()||phone.isEmpty()||email.isEmpty()||date.isEmpty()||pass.isEmpty()||confpass.isEmpty()){
            Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else{

            FirebaseDatabase.getInstance().getReferenceFromUrl("https://appinfinitycrypto-default-rtdb.firebaseio.com/").child("Account").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.hasChild(phone)){  //Kiểm tra sự tồn tại
                        if(!pass.equals(confpass)){
                            editTextPhone.setText("");
                            Toast.makeText(SignUp.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            sendOTP();
                        }
                    }else{
                        editTextPhone.setText("");
                        Toast.makeText(SignUp.this, "Phone number already in use, please enter another phone number", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SignUp.this, "onCancelled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void sendOTP() {
        if(editTextPhone.getText().toString().trim().isEmpty()){
            Toast.makeText(SignUp.this, "Enter Phone Or Email", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        btnSignup.setVisibility(View.INVISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + editTextPhone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                SignUp.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                        btnSignup.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);
                        btnSignup.setVisibility(View.VISIBLE);
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        progressBar.setVisibility(View.GONE);
//                        btnSignup.setVisibility(View.VISIBLE);
//                        Intent intent = new Intent(getApplicationContext(), SendCodeEmail.class);
//                        intent.putExtra("name", editTextUsername.getText().toString());
//                        intent.putExtra("phone", editTextPhone.getText().toString());
//                        intent.putExtra("email", editTextEmail.getText().toString());
//                        intent.putExtra("date", editTextDate.getText().toString());
//                        intent.putExtra("pass", editTextPassword.getText().toString());
//                        intent.putExtra("sex", sex());
//                        intent.putExtra("country", ccpCountry.getSelectedCountryEnglishName());
//                        intent.putExtra("checkSendCode", "");
//                        intent.putExtra("verificationId", verificationId);
//                        startActivity(intent);

                    }
                }
        );
    }
}