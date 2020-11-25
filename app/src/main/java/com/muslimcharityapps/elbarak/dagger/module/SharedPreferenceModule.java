package com.muslimcharityapps.elbarak.dagger.module;

import android.app.Application;
import android.content.SharedPreferences;

import com.muslimcharityapps.elbarak.utils.Values;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ssb on 11/3/17.
 */

@Module
public class SharedPreferenceModule implements Values {

    private String TAG = SharedPreferenceModule.class.getSimpleName();
    private Application ssbApplication;

    public SharedPreferenceModule(Application ssbApplication) {
        this.ssbApplication = ssbApplication;
    }

    /*
    CONFIGURING APP STATE SHARED PREFERENCE
     */

    @Provides
    @Singleton
    SharedPreferences provideAppStateSharedPreference() {
        return ssbApplication.getSharedPreferences(SP_SONG_STATE, MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideAppStateEditor(SharedPreferences songStateSharedPreference) {
        return songStateSharedPreference.edit();
    }
}
