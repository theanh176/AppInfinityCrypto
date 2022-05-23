package com.example.appinfinitycrypto;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private static String TAG = SessionManager.class.getName();
    private static Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String NAME = "App InfinityCrypto";
    private static final String KEY_LOGIN = "isLogin";
    private static final String KEY_PHONE = "isPhone";

    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

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

    public void SetLogin(boolean isLogin){
        editor.putBoolean(KEY_LOGIN, isLogin);
        editor.commit();
    }
    public void SetPhone(String isPhone){
        editor.putString(KEY_PHONE, isPhone);
        editor.commit();
    }
    public boolean Check(){
        return preferences.getBoolean(KEY_LOGIN, false);
    }
}
