package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {

    View imgBackSignIn;
    EditText edtPhoneOrMail;
    Button btnForgot;
    ProgressBar progressBar;

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
        setContentView(R.layout.activity_forgot_password);
        setTranslucentStatusBar();

        mapping();
        event();
    }

    private void mapping() {
        imgBackSignIn =findViewById(R.id.imgBackVerifi);
        edtPhoneOrMail = findViewById(R.id.edtPhoneOrMail);
        btnForgot = findViewById(R.id.btnForgot);
        progressBar = findViewById(R.id.progessBar);
    }
    private void event() {
        imgBackSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSignUp();
            }
        });
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOTP();
            }
        });

    }

    private void sendOTP() {
        if(edtPhoneOrMail.getText().toString().trim().isEmpty()){
            Toast.makeText(ForgotPassword.this, "Enter Phone Or Email", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        btnForgot.setVisibility(View.INVISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + edtPhoneOrMail.getText().toString(),
                60,
                TimeUnit.SECONDS,
                ForgotPassword.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                        btnForgot.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);
                        btnForgot.setVisibility(View.VISIBLE);
                        Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        progressBar.setVisibility(View.GONE);
                        btnForgot.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), SendCodeEmail.class);
                        intent.putExtra("phone", edtPhoneOrMail.getText().toString());
                        intent.putExtra("checkSendCode", "true");
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                    }
                }
        );


    }

    private void backSignUp() {
        Intent intent = new Intent(ForgotPassword.this, SignIn.class);
        intent.putExtra("checkSignIn","");
        startActivity(intent);
    }


}