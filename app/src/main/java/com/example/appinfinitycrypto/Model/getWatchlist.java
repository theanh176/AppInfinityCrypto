package com.example.appinfinitycrypto.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class getWatchlist {

    public HashMap<String, Boolean> watchlist;

    public getWatchlist(DataSnapshot snapshot) {
        HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();

        this.watchlist = (HashMap<String, Boolean>) hashMap.get("watchlist");

    }

    public List<String> getWatchList() {
        if (watchlist == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(watchlist.keySet());
    }
}
