package com.example.appinfinitycrypto.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class Account {
    public String name;
    public String phone;
    public String email;
    public String date;
    public String pass;
    public String country;
    public String sex;
    public String rule;
    public Boolean isOnline;
    public HashMap<String, Boolean> watchlist;

    public Account(DataSnapshot snapshot) {
        HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
        this.name = (String) hashMap.get("name");
        this.phone = (String) hashMap.get("phone");
        this.email = (String) hashMap.get("email");
        this.date = (String) hashMap.get("date");
        this.pass = (String) hashMap.get("pass");
        this.country = (String) hashMap.get("country");
        this.watchlist = (HashMap<String, Boolean>) hashMap.get("watchlist");
        this.sex = (String) hashMap.get("sex");
        this.rule = (String) hashMap.get("rule");
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public HashMap<String, Boolean> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(HashMap<String, Boolean> watchlist) {
        this.watchlist = watchlist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account(String name, String email, String date, String country, String sex) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.country = country;
        this.sex = sex;
    }

    public Account(String name, String phone, String email, String date, String pass, String country, String sex, String rule) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.date = date;
        this.pass = pass;
        this.country = country;
        this.sex = sex;
        this.rule = rule;
    }

    public Account(String name, String phone, String rule) {
        this.name = name;
        this.phone = phone;
        this.rule = rule;
    }

    public Account(){

    }
}
