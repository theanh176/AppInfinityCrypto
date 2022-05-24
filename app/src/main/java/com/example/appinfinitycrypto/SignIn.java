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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

public class SignIn extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://appinfinitycrypto-default-rtdb.firebaseio.com/");
    TextView textsignUp, textForgotPass;
    EditText editTextPhone, editTextPass;
    Button buttonSignIn;

    ImageView imgGoogle, imgFacebook;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private static final int RC_SIGN_IN = 100;

    SessionManager sessionManager;

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

        if(!DataLocalManager.getFirstInstall()){
            mapping();
            event();
        }else{
            if(DataLocalManager.getRuleUserInstall()){
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
                finish();
            }if(DataLocalManager.getRuleAdminInstall()){
                Intent intent = new Intent(SignIn.this, MainAdmin.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void mapping() {
        textsignUp = findViewById(R.id.textSignUp);
        textForgotPass = findViewById(R.id.textForgotPass);
        editTextPhone = findViewById(R.id.edtPhoneSignIn);
        editTextPass = findViewById(R.id.edtPasswordSignIn);
        buttonSignIn = findViewById(R.id.btnSignIn);
        imgGoogle = findViewById(R.id.imgvGoogle);
        imgFacebook = findViewById(R.id.imgvFacebook);
    }

    private void event() {
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

        getPhonePass();
        createRequest();

        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGG();
            }
        });

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signInWithFB();
            }
        });

    }

    //SignIn Google
    private void createRequest() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

    }

    private void signInWithGG() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void SignUp() {
        Intent intent = new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
    }

    private void ForgotPassword() {
        Intent intent = new Intent(SignIn.this, ForgotPassword.class);
        startActivity(intent);
    }

    private void SignInAccount() {
        final String phone = editTextPhone.getText().toString();
        final String pass = editTextPass.getText().toString();

        if (phone.isEmpty() || pass.isEmpty()) {
            Toast.makeText(SignIn.this, "Please enter Phone or Email and Password", Toast.LENGTH_SHORT).show();
        } else {
            database.child("Account").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(phone)) {
                        final String getPass = snapshot.child(phone).child("pass").getValue(String.class);
                        final String getRule = snapshot.child(phone).child("rule").getValue(String.class);
                        if (getRule.equals("user")) {
                            if (getPass.equals(pass)) {


                                DataLocalManager.setFirstInstall(true);
                                DataLocalManager.setPhoneInstall(phone);
                                DataLocalManager.setRuleUserInstall(true);

                                Toast.makeText(SignIn.this, "Successfully Sign In", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);

                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (getRule.equals("admin")) {
                            if (getPass.equals(pass)) {

                                DataLocalManager.setFirstInstall(true);
                                DataLocalManager.setPhoneInstall(phone);
                                DataLocalManager.setRuleAdminInstall(true);

                                Intent intent = new Intent(SignIn.this, MainAdmin.class);
                                startActivity(intent);

                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                System.out.println("Hello World");
                            }
                        }
                    } else {
                        Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void getPhonePass() {
        if (getIntent().getStringExtra("checkSignIn").isEmpty()) {
            editTextPhone.setText("");
            editTextPass.setText("");
        }
        if (!getIntent().getStringExtra("checkSignIn").isEmpty()) {
            editTextPhone.setText(String.format(
                    "%s", getIntent().getStringExtra("phone")
            ));
            if (getIntent().getStringExtra("checkSignIn2").isEmpty()) {
                editTextPass.setText(String.format(
                        "%s", getIntent().getStringExtra("changepass")
                ));
            }
            if (!getIntent().getStringExtra("checkSignIn2").isEmpty()) {
                editTextPass.setText(String.format(
                        "%s", getIntent().getStringExtra("pass")
                ));
            }
        }
    }
}