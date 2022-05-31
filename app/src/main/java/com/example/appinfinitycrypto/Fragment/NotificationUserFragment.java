package com.example.appinfinitycrypto.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.NotificationAdapter;
import com.example.appinfinitycrypto.Model.DataItem_Notify;
import com.example.appinfinitycrypto.NotificationActivity;
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
 * Use the {@link NotificationUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationUserFragment extends Fragment {

    private static final String CHANNEL_ID = "channel_01";;
    private DatabaseReference database;
    private List<DataItem_Notify> notificationList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private RecyclerView notificationRecyclerView;
    private NotificationManagerCompat notificationManagerCompat;
    private ImageView backHome;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationUserFragment() {
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
    public static NotificationUserFragment newInstance(String param1, String param2) {
        NotificationUserFragment fragment = new NotificationUserFragment();
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
        View view = inflater.inflate(R.layout.fragment_notificationuser, container, false);

        backHome = view.findViewById(R.id.notification_back);
        LoadData();
        BackHome();


        // Show data on recycler view
        notificationRecyclerView = view.findViewById(R.id.notificationRecycleView);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        notificationAdapter = new NotificationAdapter(notificationList);
        notificationRecyclerView.setAdapter(notificationAdapter);

        //auto show notification
//        Intent fullScreenIntent = new Intent(this, NotificationActivity.class);
//        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
//                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        notificationManagerCompat = NotificationManagerCompat.from(NotificationActivity.this);
//        Notification notification = new NotificationCompat.Builder(NotificationActivity.this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.facebook_icon)
//                .setContentTitle("Notification")
//                .setContentText("This is notification")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setFullScreenIntent(fullScreenPendingIntent, true)
//                .build();
//        notificationManagerCompat.notify(1, notification);

        return view;
    }
    private void LoadData() {
        database = FirebaseDatabase.getInstance().getReference("Notification");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataItem_Notify notification = dataSnapshot.getValue(DataItem_Notify.class);
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

    // click backHome to go to home
    public void BackHome() {
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
            }
        });
    }
}