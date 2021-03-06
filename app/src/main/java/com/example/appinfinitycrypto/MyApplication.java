package com.example.appinfinitycrypto;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class MyApplication extends Application {

    public static final  String CHANNEL_1_ID = "channel1";
    public static final  String CHANNEL_2_ID = "channel2";

    private String someVariable = "";

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }

    private Integer someVariable1 = 0;

    public Integer getSomeVariable1() {
        return someVariable1;
    }

    public void setSomeVariable1(Integer someVariable) {
        this.someVariable1 = someVariable;
    }

    // set
//    ((MyApplication) this.getApplication()).setSomeVariable("foo");

    // get
//    String s = ((MyApplication) this.getApplication()).getSomeVariable();

    @Override
    public void onCreate() {
        super.onCreate();

        DataLocalManager.init(getApplicationContext());
        this.createNotificationChannels();
    }

    private void createNotificationChannels()  {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is channel 2");


            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

}
