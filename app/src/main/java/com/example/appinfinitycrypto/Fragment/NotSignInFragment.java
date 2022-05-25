package com.example.appinfinitycrypto.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.SignIn;
import com.example.appinfinitycrypto.SignUp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotSignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotSignInFragment extends Fragment {

    Button btnSignIn, btnSignUp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotSignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotSignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotSignInFragment newInstance(String param1, String param2) {
        NotSignInFragment fragment = new NotSignInFragment();
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
        View view = inflater.inflate(R.layout.fragment_not_sign_in, container, false);

        btnSignIn = view.findViewById(R.id.btnSignInnot);
        btnSignUp = view.findViewById(R.id.btnSignUpnot);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignIn.class);
                intent.putExtra("checkSignIn","");
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUp.class);
                intent.putExtra("checkSignIn","");
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}