package com.example.appinfinitycrypto;

import android.content.Context;

public class DataLocalManager {

    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_FIRST_PHONE = "PREF_FIRST_PHONE";
    private static final String PREF_FIRST_RULE = "PREF_FIRST_RULE";
    private static DataLocalManager instance;
    private SessionManager sessionManager;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.sessionManager = new SessionManager(context);

    }

    public static  DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setFirstInstall(Boolean isFirst){
        DataLocalManager.getInstance().sessionManager.putBooleanValue(PREF_FIRST_INSTALL, isFirst);
    }

    public static boolean getFirstInstall(){
        return DataLocalManager.getInstance().sessionManager.getBooleanValue(PREF_FIRST_INSTALL);
    }

    public static void setPhoneInstall(String isPhone){
        DataLocalManager.getInstance().sessionManager.putStringPhone(PREF_FIRST_PHONE, isPhone);
    }

    public static String getPhoneInstall(){
        return DataLocalManager.getInstance().sessionManager.getStringPhone(PREF_FIRST_PHONE);
    }

    public static void setRuleInstall(Boolean isRule){
        DataLocalManager.getInstance().sessionManager.putBooleanRule(PREF_FIRST_RULE, isRule);
    }

    public static boolean getRuleInstall(){
        return DataLocalManager.getInstance().sessionManager.getBooleanRule(PREF_FIRST_RULE);
    }
}
