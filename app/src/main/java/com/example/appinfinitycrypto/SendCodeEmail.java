package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SendCodeEmail extends AppCompatActivity {

    private DatabaseReference database;
    EditText edtcode1, edtcode2, edtcode3, edtcode4, edtcode5, edtcode6;
    Button btnVerify;
    TextView textResend, textPhone;
    ProgressBar progressBar;
    String verificationId;
    View imgBackForgot;

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
        setContentView(R.layout.activity_send_code_email);

        setTranslucentStatusBar();
        textPhone = findViewById(R.id.textViewPhone);
        textPhone.setText(String.format(
                "+84-%s", getIntent().getStringExtra("phone")
        ));
        mapping();
        event();


    }

    private void mapping() {
        edtcode1 = findViewById(R.id.edtcode1);
        edtcode2 = findViewById(R.id.edtcode2);
        edtcode3 = findViewById(R.id.edtcode3);
        edtcode4 = findViewById(R.id.edtcode4);
        edtcode5 = findViewById(R.id.edtcode5);
        edtcode6 = findViewById(R.id.edtcode6);
        btnVerify = findViewById(R.id.btnVerify);
        textResend =findViewById(R.id.textResend);
        progressBar = findViewById(R.id.progessBar);
        imgBackForgot = findViewById(R.id.imgBackVerifi);
    }

    private void event() {
        setupOTPInputs();
        verificationId = getIntent().getStringExtra("verificationId");
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifiOTP();
            }
        });

        textResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode();
            }
        });

        imgBackForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backForgot();
            }
        });
    }

    private void backForgot() {
        Intent intent = new Intent(SendCodeEmail.this, ForgotPassword.class);
        startActivity(intent);
    }

    private void setupOTPInputs() {
        edtcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    edtcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void verifiOTP(){
        if(edtcode1.getText().toString().trim().isEmpty()
        ||edtcode2.getText().toString().trim().isEmpty()
        ||edtcode3.getText().toString().trim().isEmpty()
        ||edtcode4.getText().toString().trim().isEmpty()
        ||edtcode5.getText().toString().trim().isEmpty()
        ||edtcode6.getText().toString().trim().isEmpty()){
            Toast.makeText(SendCodeEmail.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }
        String code =
                edtcode1.getText().toString()+
                        edtcode2.getText().toString()+
                        edtcode3.getText().toString()+
                        edtcode4.getText().toString()+
                        edtcode5.getText().toString()+
                        edtcode6.getText().toString();
        if(verificationId !=null){
            progressBar.setVisibility(View.VISIBLE);
            btnVerify.setVisibility(View.INVISIBLE);
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                    verificationId,
                    code
            );
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            btnVerify.setVisibility(View.VISIBLE);

                            if(task.isSuccessful()){
                                if(getIntent().getStringExtra("checkSendCode").isEmpty()){
                                    //Khai báo lên firebase
                                    String name = getIntent().getStringExtra("name");
                                    String phone = getIntent().getStringExtra("phone");
                                    String email = getIntent().getStringExtra("email");
                                    String date = getIntent().getStringExtra("date");
                                    String pass = getIntent().getStringExtra("pass");
                                    String sex = getIntent().getStringExtra("sex");
                                    String country = getIntent().getStringExtra("country");
                                    String rule = getIntent().getStringExtra("rule");

                                    database = FirebaseDatabase.getInstance().getReference("Account");

                                    Account account = new Account(name, phone, email, date, pass, country, sex, rule);
                                    // Đưa lên firebase
                                    database.child(phone).setValue(account);

                                    Toast.makeText(SendCodeEmail.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("phone", phone);
                                    intent.putExtra("pass", pass);
                                    intent.putExtra("checkSignIn","signup");
                                    intent.putExtra("checkSignIn2","SendCodeEmail");
                                    startActivity(intent);
                                }
                                if(!getIntent().getStringExtra("checkSendCode").isEmpty()){
                                    Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                                    intent.putExtra("phone", getIntent().getStringExtra("phone"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }


                            }else{
                                Toast.makeText(SendCodeEmail.this, "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    private void resendCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + getIntent().getStringExtra("phone"),
                60,
                TimeUnit.SECONDS,
                SendCodeEmail.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(SendCodeEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        verificationId = newVerificationId;
                        Toast.makeText(SendCodeEmail.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }

}