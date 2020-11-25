package com.muslimcharityapps.elbarak.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.muslimcharityapps.elbarak.utils.RxBus;
import com.muslimcharityapps.elbarak.utils.Values;

/**
 * Created by ssb on 18/6/17.
 */

public class ConnectivityReceiver extends BroadcastReceiver implements Values {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null) {
                RxBus.get().post(MESSAGE_CONNECTIVITY, networkInfo.isConnected());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
