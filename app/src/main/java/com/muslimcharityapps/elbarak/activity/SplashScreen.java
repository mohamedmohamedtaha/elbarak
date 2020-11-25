package com.muslimcharityapps.elbarak.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.muslimcharityapps.elbarak.R;


public class SplashScreen extends Activity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileAds.initialize(this, initializationStatus -> {
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                Intent i = new Intent(SplashScreen.this, Dashboard.class);
                startActivity(i);
                Toast.makeText(getApplication(), getString(R.string.sorry_for_confdent),
                        Toast.LENGTH_LONG).show();

            }
        });

        requestNewInterstitial();
        new Handler().postDelayed(()->{
            Intent in = new Intent(SplashScreen.this, Dashboard.class);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                startActivity(in);
            }
            finish();

        },PreSplashScreen.SPLASH_TIME_OUT);}

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent in = new Intent(SplashScreen.this, Dashboard.class);
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                } else {
//                    startActivity(in);
//                }
//
//
//                // close this activity
//                finish();
//
//            }
//        },PreSplashScreen.SPLASH_TIME_OUT);
   // }

    private void requestNewInterstitial() {
        AdRequest adrequest = new AdRequest.Builder()
                //.addTestDevice("8A983E419D5EB0C2BB6B6E1339E88004")  // An example device ID
                .build();

        mInterstitialAd.loadAd(adrequest);
    }
}