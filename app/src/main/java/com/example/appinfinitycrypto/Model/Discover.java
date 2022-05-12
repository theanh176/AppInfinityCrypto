package com.example.appinfinitycrypto.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Discover {
    private String Type;
    private String Message;
    private List<String> Promoted;
    private List<DataNews> Data;

    public Discover(String Type, String Message, List Promoted, List<DataNews> Data) {
        this.Type = Type;
        this.Message = Message;
        this.Promoted = Promoted;
        this.Data = Data;
    }

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

    public List getPromoted() {
        return Promoted;
    }

    public void setPromoted(List promoted) {
        Promoted = promoted;
    }

    public List<DataNews> getData() {
        return Data;
    }

    public void setData(List<DataNews> data) {
        Data = data;
    }
};



