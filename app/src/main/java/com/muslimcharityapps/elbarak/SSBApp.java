package com.muslimcharityapps.elbarak;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.muslimcharityapps.elbarak.dagger.component.DaggerRetroComponent;
import com.muslimcharityapps.elbarak.dagger.component.RetroComponent;
import com.muslimcharityapps.elbarak.dagger.module.RetroModule;
import com.muslimcharityapps.elbarak.dagger.module.SharedPreferenceModule;
import com.muslimcharityapps.elbarak.dagger.module.UtilityModule;

/**
 * Created by ssb on 11/3/17.
 */

public class SSBApp extends Application {

    private RetroComponent retroComponent;
    private static SSBApp ssbApp;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());

        /*
        INITIALIZING
         */

        ssbApp = this;

        /*
        CONFIGURING DAGGER
         */

        retroComponent = DaggerRetroComponent
                .builder()
                .retroModule(new RetroModule(this))
                .sharedPreferenceModule(new SharedPreferenceModule(this))
                .utilityModule(new UtilityModule(this))
                .build();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static SSBApp getApp() {
        return ssbApp;
    }

    public RetroComponent getRetroComponent() {
        return retroComponent;
    }

}
