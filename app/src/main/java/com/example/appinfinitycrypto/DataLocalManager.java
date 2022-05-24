package com.example.appinfinitycrypto;

import android.content.Context;

public class DataLocalManager {

    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_FIRST_PHONE = "PREF_FIRST_PHONE";
    private static final String PREF_FIRST_RULE_USER = "PREF_FIRST_RULE_USER";
    private static final String PREF_FIRST_RULE_ADMIN = "PREF_FIRST_RULE_ADMIN";
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

    public static void setRuleUserInstall(Boolean isRule){
        DataLocalManager.getInstance().sessionManager.putBooleanRuleUser(PREF_FIRST_RULE_USER, isRule);
    }

    public static boolean getRuleUserInstall(){
        return DataLocalManager.getInstance().sessionManager.getBooleanRuleUser(PREF_FIRST_RULE_USER);
    }

    public static void setRuleAdminInstall(Boolean isRule){
        DataLocalManager.getInstance().sessionManager.putBooleanRuleAdmin(PREF_FIRST_RULE_ADMIN, isRule);
    }

    public static boolean getRuleAdminInstall(){
        return DataLocalManager.getInstance().sessionManager.getBooleanRuleAdmin(PREF_FIRST_RULE_ADMIN);
    }
}
