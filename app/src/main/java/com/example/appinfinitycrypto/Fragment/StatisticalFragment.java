package com.example.appinfinitycrypto.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticalFragment extends Fragment {
    private DatabaseReference database;
    EditText edtTitle, edtContent;
    Button btnSendNotification;
    ImageView imgViewCreate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticalFragment newInstance(String param1, String param2) {
        StatisticalFragment fragment = new StatisticalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistical, container, false);
        edtTitle = view.findViewById(R.id.edtTitleNotifi);
        edtContent = view.findViewById(R.id.edtDescNotifi);
        imgViewCreate = view.findViewById(R.id.imgCreateNotifcation);

        imgViewCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileDialog(Gravity.CENTER);
            }
        });

        return view;
    }

    private void openProfileDialog(int gravity){
        final Dialog dialog =new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addnotifi);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER ==gravity){
            dialog.setCancelable(true);
        } else{
            dialog.setCancelable(false);
        }
//        TextView textViewName = dialog.findViewById(R.id.txtNameDialog);
//        TextView textViewEmail = dialog.findViewById(R.id.txtEmailDialog);
//        TextView textViewPhone = dialog.findViewById(R.id.txtPhoneDialog);
//        TextView textViewBirth = dialog.findViewById(R.id.txtBirthDayDialog);
//        TextView textViewGender = dialog.findViewById(R.id.txtGenderDialog);
//        CountryCodePicker countryDialog = dialog.findViewById(R.id.ccpCountryDialog);
//
//        Button btnClose = dialog.findViewById(R.id.btnCloseDialog);
//
//        database = FirebaseDatabase.getInstance().getReference("Account").child(phone);
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Account account = snapshot.getValue(Account.class);
//                textViewName.setText(account.getName());
//                textViewEmail.setText(account.getEmail());
//                textViewPhone.setText(account.getPhone());
//                textViewBirth.setText(account.getDate());
//                textViewGender.setText(account.getSex());
//                countryDialog.setCountryForPhoneCode(Integer.parseInt(account.getCountry()));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();
    }
}