package com.example.appinfinitycrypto;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    private String someVariable = "";

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }

//    // set
//    ((MyApplication) this.getApplication()).setSomeVariable("foo");
//
//    // get
//    String s = ((MyApplication) this.getApplication()).getSomeVariable();

}
