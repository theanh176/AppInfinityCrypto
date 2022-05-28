package com.example.appinfinitycrypto.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.AccountAdapter;
import com.example.appinfinitycrypto.Adapter.FeedbackAdapter;
import com.example.appinfinitycrypto.Adapter.HomeAdminAdapter;
import com.example.appinfinitycrypto.Adapter.HomeAdminViewAdapter;
import com.example.appinfinitycrypto.Model.Account;
import com.example.appinfinitycrypto.Model.FeedBack;
import com.example.appinfinitycrypto.R;
import com.example.appinfinitycrypto.my_interface.IClickItem;
import com.example.appinfinitycrypto.my_interface.IClickShowProfile;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {

    private RecyclerView recyclerViewFeedback;
    private DatabaseReference database;
    private FeedbackAdapter feedbackAdapter;
    private List<FeedBack> feedBackList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerViewFeedback = view.findViewById(R.id.recyclerViewFeedback);

        feedBackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(feedBackList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewFeedback.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewFeedback.addItemDecoration(dividerItemDecoration);
        recyclerViewFeedback.setAdapter(feedbackAdapter);
        event();
        return view;
    }
    private void getListFeedBacksRealtimeDB() {
        database = FirebaseDatabase.getInstance().getReference("FeedBack");
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedBackList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FeedBack feedBack = dataSnapshot.getValue(FeedBack.class);
                    feedBackList.add(feedBack);
                }
                feedbackAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getActivity(), "Get list data faile!!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    private void event(){
        getListFeedBacksRealtimeDB();

    }
}