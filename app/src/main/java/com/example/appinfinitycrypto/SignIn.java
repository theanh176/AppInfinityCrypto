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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appinfinitycrypto-default-rtdb.firebaseio.com/");
    TextView textsignUp, textForgotPass;
    EditText editTextPhone, editTextPass;
    Button buttonSignIn;

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
        setContentView(R.layout.activity_sign_in);
        setTranslucentStatusBar();

        mapping();
        event();
    }

    private void mapping(){
        textsignUp = findViewById(R.id.textSignUp);
        textForgotPass = findViewById(R.id.textForgotPass);
        editTextPhone = findViewById(R.id.edtPhoneSignIn);
        editTextPass = findViewById(R.id.edtPasswordSignIn);
        buttonSignIn = findViewById(R.id.btnSignIn);
    }

    private void event(){
        textsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        textForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword();
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAccount();
            }
        });
    }

    private void SignUp(){
        Intent intent = new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
    }

    private void ForgotPassword(){
        Intent intent = new Intent(SignIn.this, ForgotPassword.class);
        startActivity(intent);
    }

    private void SignInAccount(){
        final String phone = editTextPhone.getText().toString();
        final String pass = editTextPass.getText().toString();

        if(phone.isEmpty()||pass.isEmpty()){
            Toast.makeText(SignIn.this, "Please enter Phone or Email and Password", Toast.LENGTH_SHORT).show();
        }else{
            database.child("Account").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(phone)){
                        final String getPass = snapshot.child(phone).child("pass").getValue(String.class);
                        if(getPass.equals(pass)){
                            Toast.makeText(SignIn.this, "Successfully Sign In", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignIn.this,activity_profile_user.class);
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}