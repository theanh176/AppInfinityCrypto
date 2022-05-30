package com.example.appinfinitycrypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appinfinitycrypto.Adapter.NotificationAdapter;
import com.example.appinfinitycrypto.Fragment.HomeFragment;
import com.example.appinfinitycrypto.Model.DataItem_Notify;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_01";;
    private DatabaseReference database;
    private List<DataItem_Notify> notificationList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private RecyclerView notificationRecyclerView;
    private NotificationManagerCompat notificationManagerCompat;
    private ImageView backHome;

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
        backHome = findViewById(R.id.notification_back);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTranslucentStatusBar();
        LoadData();
//        BackHome();


        // Show data on recycler view
        notificationRecyclerView = findViewById(R.id.notificationRecycleView);
        notificationRecyclerView.setHasFixedSize(true);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false));

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
                Toast toast = Toast.makeText(NotificationActivity.this, "Get list data faile!!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    // click backHome to go to home
//    public void BackHome() {
//        backHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(NotificationActivity.this, "Back Home", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }



}