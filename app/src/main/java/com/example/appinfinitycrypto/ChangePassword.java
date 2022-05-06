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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {

    View imgBackVerify;
    TextView editPass, editConfPass;
    Button btnChangePass;
    private DatabaseReference database;

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
        setContentView(R.layout.activity_change_password);

        setTranslucentStatusBar();
        mapping();
        event();
    }

    private void mapping() {
        imgBackVerify = findViewById(R.id.imgBackVerifi);
        editPass = findViewById(R.id.editPass);
        editConfPass = findViewById(R.id.editConfPass);
        btnChangePass = findViewById(R.id.btnChangePass);
    }

    private void event() {

        imgBackVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backVerify();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword(){
        final String Pass = editPass.getText().toString();
        final String Confpass = editConfPass.getText().toString();
        String getPhoneByForgot = getIntent().getStringExtra("phone");
        if(Pass.isEmpty()||Confpass.isEmpty()){
            Toast.makeText(ChangePassword.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        if(!Pass.equals(Confpass)){
            editConfPass.setText("");
            Toast.makeText(ChangePassword.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
        }
        else{
            database = FirebaseDatabase.getInstance().getReference("Account");
            database.child(getPhoneByForgot).child("pass").setValue(Pass);
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            intent.putExtra("phone", getPhoneByForgot);
            intent.putExtra("changepass", Pass);
            intent.putExtra("checkSignIn","changepass");
            intent.putExtra("checkSignIn2","");
            startActivity(intent);
        }
    }

    private void backVerify() {
        Intent intent = new Intent(ChangePassword.this, SendCodeEmail.class);
        startActivity(intent);
    }
}