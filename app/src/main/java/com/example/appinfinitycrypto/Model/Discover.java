package com.example.appinfinitycrypto.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Discover {
    private String Type;
    private String Message;
    private ArrayList<String> Promoted;
    private ArrayList<DataNew> Data;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<String> getPromoted() {
        return Promoted;
    }

    public void setPromoted(ArrayList<String> promoted) {
        Promoted = promoted;
    }

    public ArrayList<DataNew> getData() {
        return Data;
    }

    public void setData(ArrayList<DataNew> data) {
        Data = data;
    }
}
