package com.example.appinfinitycrypto.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Discover {
    private String Type;
    private String Message;
    private List<String> Promoted;
    private List<DataNew> Data;

    public Discover(String type, String message, List<String> promoted, List<DataNew> data) {
        this.Type = type;
        this.Message = message;
        this.Promoted = promoted;
        this.Data = data;
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

    public List<String> getPromoted() {
        return Promoted;
    }

    public void setPromoted(ArrayList<String> promoted) {
        Promoted = promoted;
    }

    public List<DataNew> getData() {
        return Data;
    }

    public void setData(List<DataNew> data) {
        Data = data;
    }
}
