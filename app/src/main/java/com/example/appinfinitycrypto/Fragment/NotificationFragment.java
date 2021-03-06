package com.example.appinfinitycrypto.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.FeedbackAdapter;
import com.example.appinfinitycrypto.Adapter.NotificationAdapter;
import com.example.appinfinitycrypto.Adapter.NotifyAdapter;
import com.example.appinfinitycrypto.DataLocalManager;
import com.example.appinfinitycrypto.Model.FeedBack;
import com.example.appinfinitycrypto.Model.Notification;
import com.example.appinfinitycrypto.MyApplication;
import com.example.appinfinitycrypto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    private RecyclerView recyclerViewNotification;
    private ImageView imgCreateNoti;
    private DatabaseReference database;
    private List<Notification> notificationList;
    private NotifyAdapter notificationAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        imgCreateNoti = view.findViewById(R.id.imgCreateNotifcation);
        recyclerViewNotification = view.findViewById(R.id.recyclerViewNotification);

        imgCreateNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileDialog(Gravity.CENTER);
            }
        });


        notificationList = new ArrayList<>();
        notificationAdapter = new NotifyAdapter(notificationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewNotification.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewNotification.addItemDecoration(dividerItemDecoration);
        recyclerViewNotification.setAdapter(notificationAdapter);
        event();
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
        EditText edtTitleNoti = dialog.findViewById(R.id.edtTitleNotifi);
        EditText edtDetailNoti = dialog.findViewById(R.id.edtDescNotifi);
        Button btnSendNoti = dialog.findViewById(R.id.btnSendDialogNotifi);

        btnSendNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khai b??o l??n firebase
                String title = edtTitleNoti.getText().toString();
                String description = edtDetailNoti.getText().toString();

                if(!title.isEmpty() || !description.isEmpty()){
                    Integer keyIdOld = DataLocalManager.getKeyIDNotify();
                    Integer keyId = keyIdOld+1;
                    database = FirebaseDatabase.getInstance().getReference("Notification");
                    Notification notification = new Notification(title,description);
                    // ????a l??n firebase
                    database.child(""+keyId).setValue(notification);
                    DataLocalManager.setKeyIDNotify(keyId);
                    dialog.dismiss();
                }else {
                    Toast.makeText(getActivity(), "Vui l??ng nh???p ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }
    private void getListFeedBacksRealtimeDB() {
        database = FirebaseDatabase.getInstance().getReference("Notification");
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    notificationList.add(notification);
                }
                notificationAdapter.notifyDataSetChanged();
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