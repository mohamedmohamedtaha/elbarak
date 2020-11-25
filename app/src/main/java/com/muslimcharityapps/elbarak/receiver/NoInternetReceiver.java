package com.muslimcharityapps.elbarak.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.muslimcharityapps.elbarak.R;

import static com.muslimcharityapps.elbarak.checkConnection.NoInternetConnection.isInternet;

public class NoInternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isInternet()) {
        } else {
            Toast.makeText(context, context.getString(R.string.no_internet_network), Toast.LENGTH_SHORT).show();
        }
    }
}
