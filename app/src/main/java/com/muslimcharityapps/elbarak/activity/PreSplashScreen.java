package com.muslimcharityapps.elbarak.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.muslimcharityapps.elbarak.R;
import com.muslimcharityapps.elbarak.utils.Values;

import java.util.Locale;

public class PreSplashScreen extends Activity implements Values {

    // Splash screen timer
    public static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedpreferences = getSharedPreferences(SP_SONG_STATE, Context.MODE_PRIVATE);
        if (sharedpreferences.getString(getString(R.string.language), "").length() > 0) {
            setApplicationLocale(sharedpreferences.getString(getString(R.string.language), ""));
        }

        new Handler().postDelayed(() -> {
            Intent i = new Intent(PreSplashScreen.this, SplashScreen.class);
            startActivity(i);
            // close this activity
         //   finish();
        }, SPLASH_TIME_OUT);
    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(locale.toLowerCase());
        resources.updateConfiguration(configuration, displayMetrics);
    }
}