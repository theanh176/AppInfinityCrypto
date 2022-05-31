package com.example.appinfinitycrypto.Model;

import java.util.HashMap;

public class DetailItem {
    private HashMap<String, DataItem> data;
    private Status status;

    public DetailItem(Status status, HashMap<String, DataItem> data) {
        this.data = data;
        this.status = status;
    }

    public HashMap<String, DataItem> getData() {
        return data;
    }

    public void setData(HashMap<String, DataItem> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Status{
        private String timestamp;
        private int error_code;
        private String error_message;
        private int elapsed;
        private int credit_count;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getError_message() {
            return error_message;
        }

        public void setError_message(String error_message) {
            this.error_message = error_message;
        }

        public int getElapsed() {
            return elapsed;
        }

        public void setElapsed(int elapsed) {
            this.elapsed = elapsed;
        }

        public int getCredit_count() {
            return credit_count;
        }

        public void setCredit_count(int credit_count) {
            this.credit_count = credit_count;
        }
    }
}
