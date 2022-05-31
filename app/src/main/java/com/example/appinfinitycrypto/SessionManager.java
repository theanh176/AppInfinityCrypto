package com.example.appinfinitycrypto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.IInterface;

public class SessionManager {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private static String TAG = SessionManager.class.getName();
    private static Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String NAME = "App InfinityCrypto";

    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    // Lưu lại sign in
    public static void putBooleanValue(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    // Lưu Phone
    public static void putStringPhone(String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPhone(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    // Kiểm tra rule User
    public static void putBooleanRuleUser(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanRuleUser(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    // Kiểm tra rule Admin
    public static void putBooleanRuleAdmin(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanRuleAdmin(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    // Lấy id của trang notifi trên firebase
    public static void putIntegerKeyID(Integer key, Integer value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(key), value);
        editor.apply();
    }

    public static Integer getIntegerKeyID(Integer key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(String.valueOf(key), 0);
    }

    // Check back detail --> watchlist or market
    public static void putIntegerCheckDetail(Integer key, Integer value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(key), value);
        editor.apply();
    }

    public static Integer getIntegerCheckDetail(Integer key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(String.valueOf(key), 0);
    }
}
