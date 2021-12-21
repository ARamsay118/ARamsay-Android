package com.Aramasy.Android_App.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.Aramasy.Android_App.MyApplication;


public class NetworkUtil {
    public static boolean isNetworkAvailable(){
        ConnectivityManager manager=(ConnectivityManager) MyApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager==null)
            return false;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo==null||!networkInfo.isConnected())
            return false;
        return true;
    }
}
